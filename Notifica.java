package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Coltura;

import java.sql.*;

public class LottoDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "password";

    private Controller controller;

    public LottoDAO(Controller controller) {
        this.controller = controller;
    }

    public boolean isTerrenoAdatto(int idLotto, Coltura coltura) {

        String sql = "SELECT tipo_terreno FROM Lotto WHERE id_lotto = ?";
        String tipoTerrenoDb = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idLotto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    tipoTerrenoDb = rs.getString("tipo_terreno");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        if (tipoTerrenoDb == null) return false;

        String nomeOrtaggio = coltura.getTipoortaggio();
        if (nomeOrtaggio == null) return false;

        nomeOrtaggio = nomeOrtaggio.toLowerCase();
        tipoTerrenoDb = tipoTerrenoDb.toLowerCase();

        if (nomeOrtaggio.contains("patata") && tipoTerrenoDb.contains("sabbioso")) {
            return true;
        }
        if (nomeOrtaggio.contains("pomodoro") && tipoTerrenoDb.contains("argilloso")) {
            return true;
        }

        return false;
    }
}