package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Notifica;

import java.sql.*;

public class NotificaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Controller controller;

    public NotificaDAO(Controller controller) {
        this.controller = controller;
    }

    public void inserisciNotifica(Notifica nuovaNotifica) {
        String sql = "INSERT INTO notifica (id_notifica, contenuto, problema, tipo_messaggio, letta) VALUES (?, ?, ?, ?, 0)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, nuovaNotifica.getidNotifica());

            ps.setString(2, nuovaNotifica.getContenuto());

            String problema = nuovaNotifica.getProblema();
            if (problema != null) {
                ps.setString(3, problema);
            } else {
                ps.setNull(3, Types.VARCHAR);
            }

            if (nuovaNotifica.getTipoMessaggio() != null) {
                ps.setString(4, nuovaNotifica.getTipoMessaggio().name());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void contrassegnaComeLetta(int idNotifica) {
        String sql = "UPDATE notifica SET letta = 1 WHERE id_notifica = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idNotifica);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}