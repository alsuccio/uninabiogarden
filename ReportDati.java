package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Coltura;
import org.uninabiogarden.oobd68.entity.Lotto;
import org.uninabiogarden.oobd68.entity.Persona;
import org.uninabiogarden.oobd68.entity.Utente;

import java.sql.*;
import java.util.List;

public class UtenteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Controller controller;

    public UtenteDAO(Controller controller) {
        this.controller = controller;
    }

    public UtenteDAO() {}

    public Utente login(String username, String password) {
        System.out.println("[DEBUG] Sto cercando User: '" + username + "' con Password: '" + password + "'");

        String sql = "SELECT * FROM utente WHERE username = ? AND password = ?";
        Utente utente = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("[DEBUG] Driver caricato correttamente.");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                System.out.println("[DEBUG] Connessione al Database APERTA con successo!");

                ps.setString(1, username);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("[DEBUG] Trovato! L'utente esiste nel DB.");
                        int id = rs.getInt("id_utente");
                        String nome = rs.getString("nome");
                        String cognome = rs.getString("cognome");
                        String usernameDb = rs.getString("username");
                        String ruoloString = rs.getString("tipo_persona");

                        Persona tipoRuolo = Persona.valueOf(ruoloString.trim().toUpperCase());

                        utente = new Utente(nome, cognome, usernameDb, password, tipoRuolo);
                        utente.setIdUtente(id);
                    } else {
                        System.out.println("[DEBUG] ERRORE DATI: La connessione funziona, ma User/Pass non corrispondono a nessun utente nel DB.");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[DEBUG] ERRORE: Driver NON trovato.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("[DEBUG] ERRORE SQL: Problema di connessione o query.");
            e.printStackTrace();
        }
        return utente;
    }

    public boolean creaProgettoStagionale(Lotto lotto, List<Coltura> listaColture) {
        if (lotto == null || listaColture == null || listaColture.isEmpty()) {
            return false;
        }

        String sqlGetIdColtura = "SELECT id_coltura FROM coltura WHERE tipo_ortaggio = ?";

        String sqlInsert = "INSERT INTO ColtivazioneStagionale (id_lotto, id_coltura, stato, data_inizio) " +
                "VALUES (?, ?, 'PIANIFICATO', CURRENT_DATE)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
                conn.setAutoCommit(false);

                try (PreparedStatement psGetId = conn.prepareStatement(sqlGetIdColtura);
                     PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {

                    for (Coltura c : listaColture) {
                        psGetId.setString(1, c.getTipoortaggio());
                        ResultSet rs = psGetId.executeQuery();

                        if (rs.next()) {
                            int idColturaReale = rs.getInt("id_coltura");

                            psInsert.setInt(1, lotto.getIdLotto());
                            psInsert.setInt(2, idColturaReale);
                            psInsert.addBatch();
                        } else {
                            System.out.println("Errore: Coltura '" + c.getTipoortaggio() + "' non trovata nel database.");
                            conn.rollback();
                            return false;
                        }
                    }

                    int[] risultati = psInsert.executeBatch();
                    conn.commit();
                    return risultati.length > 0;

                } catch (SQLException e) {
                    conn.rollback();
                    e.printStackTrace();
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}