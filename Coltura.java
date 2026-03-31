package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Attivita;
import org.uninabiogarden.oobd68.entity.Categoria;
import org.uninabiogarden.oobd68.entity.ReportDati;
import org.uninabiogarden.oobd68.entity.Stato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttivitaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Controller controller;

    public AttivitaDAO(Controller controller) {
        this.controller = controller;
    }
    public AttivitaDAO(){
    }

    public boolean impostaDettagliRaccolta(int idAttivita, double quantitaPrevista) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement("UPDATE Attivita SET quantita_prevista = ? WHERE id_attivita = ?")) {

            ps.setDouble(1, quantitaPrevista);
            ps.setInt(2, idAttivita);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { return false; }
    }

    public boolean registraRaccolta(int idAttivita, double quantitaEffettiva) {
        String sql = "UPDATE Attivita SET quantita_effettiva = ?, data_fine_effettiva = CURRENT_DATE, tipo_stato = 'COMPLETATO' WHERE id_attivita = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, quantitaEffettiva);
            ps.setInt(2, idAttivita);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) { return false; }
    }

    public List<Attivita> recuperaAttivitaPerProgetto(int idColtivazione) {
        List<Attivita> lista = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Attivita WHERE id_coltivazione = ?")) {

            ps.setInt(1, idColtivazione);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Attivita a = new Attivita();

                a.setidAttivita(rs.getInt("id_attivita"));
                a.setQuantitaPrevista(rs.getDouble("quantita_prevista"));
                a.setQuantitaRaccolta(rs.getDouble("quantita_effettiva"));

                String cat = rs.getString("tipo_categoria");
                if (cat != null) a.setTipoCategoria(Categoria.valueOf(cat.toUpperCase()));

                String st = rs.getString("tipo_stato");
                if (st != null) a.setTipoStato(Stato.valueOf(st.toUpperCase()));

                Timestamp ts = rs.getTimestamp("data_inizio");
                if (ts != null) a.setinizioSemina(ts.toLocalDateTime());

                lista.add(a);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return lista;
    }

    public List<ReportDati> generaReport(int idLotto) {
        List<ReportDati> report = new ArrayList<>();
        String sql = "SELECT c.tipo_ortaggio, COUNT(*) as Tot, AVG(a.quantita_effettiva) as Media, " +
                "MIN(a.quantita_effettiva) as Min, MAX(a.quantita_effettiva) as Max " +
                "FROM Attivita a " +
                "JOIN ColtivazioneStagionale cs ON a.id_coltivazione = cs.id_coltivazione " +
                "JOIN Coltura c ON cs.id_coltura = c.id_coltura " +
                "WHERE cs.id_lotto = ? AND a.tipo_categoria = 'RACCOLTA' AND a.tipo_stato = 'COMPLETATO' " +
                "GROUP BY c.tipo_ortaggio";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLotto);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("tipo_ortaggio");
                int tot = rs.getInt("Tot");
                double media = rs.getDouble("Media");
                double min = rs.getDouble("Min");
                double max = rs.getDouble("Max");

                report.add(new ReportDati(nome, tot, media, min, max));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }


}