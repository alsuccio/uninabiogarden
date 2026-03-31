package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Attivita;

import java.sql.*;

public class ColtivazioneStagionaleDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Controller controller;

    public ColtivazioneStagionaleDAO(Controller controller) {
        this.controller = controller;
    }

    public void aggiungiAttivita(int idColtivazione, Attivita nuovaAttivita) {

        String sql = "INSERT INTO attivita (" +
                "id_attivita, inizio_sem, fine_sem, inizio_racc, fine_raccol, " +
                "inizio_irrig, fine_irrig, id_semina, id_raccolta, id_irrigazione, " +
                "soluzione, tipo_stato, id_curacoltura, id_coltivazione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuovaAttivita.getidAttivita());

            if (nuovaAttivita.getinizioSemina() != null) {
                ps.setTimestamp(2, Timestamp.valueOf(nuovaAttivita.getinizioSemina()));
            } else {
                ps.setNull(2, Types.TIMESTAMP);
            }

            if (nuovaAttivita.getfineSemina() != null) {
                ps.setTimestamp(3, Timestamp.valueOf(nuovaAttivita.getfineSemina()));
            } else {
                ps.setNull(3, Types.TIMESTAMP);
            }

            if (nuovaAttivita.getinizioRaccolta() != null) {
                ps.setTimestamp(4, Timestamp.valueOf(nuovaAttivita.getinizioRaccolta()));
            } else {
                ps.setNull(4, Types.TIMESTAMP);
            }

            if (nuovaAttivita.getfineRaccolta() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(nuovaAttivita.getfineRaccolta()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }

            if (nuovaAttivita.getinizioIrrigazione() != null) {
                ps.setTimestamp(6, Timestamp.valueOf(nuovaAttivita.getinizioIrrigazione()));
            } else {
                ps.setNull(6, Types.TIMESTAMP);
            }

            if (nuovaAttivita.getfineIrrigazione() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(nuovaAttivita.getfineIrrigazione()));
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }

            ps.setNull(8, Types.INTEGER);
            ps.setNull(9, Types.INTEGER);
            ps.setNull(10, Types.INTEGER);
            ps.setNull(11, Types.INTEGER);

            if (nuovaAttivita.getTipoStato() != null) {
                ps.setString(12, nuovaAttivita.getTipoStato().name());
            } else {
                ps.setNull(12, Types.VARCHAR);
            }

            ps.setNull(13, Types.INTEGER);

            ps.setInt(14, idColtivazione);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}