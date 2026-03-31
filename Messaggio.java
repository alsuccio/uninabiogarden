package org.uninabiogarden.oobd68.dao;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Coltura;

import java.sql.*;
import java.util.Date; 

public class ColturaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden";
    private static final String USER = "root";
    private static final String PASS = "root";

    private Controller controller;

    public ColturaDAO(Controller controller) {
        this.controller = controller;
    }

    public String getStagioneMigliore(int idColtura) {
        String stagioneIdeale = "Non specificata";

        String sql = "SELECT tipo_ortaggio, tempo_maturazione FROM coltura WHERE id_coltura = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idColtura);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String tipoOrtaggio = rs.getString("tipo_ortaggio");

                    java.sql.Date dataMaturazioneSql = rs.getDate("tempo_maturazione");
                    Date tempoMaturazione = (dataMaturazioneSql != null) ? new Date(dataMaturazioneSql.getTime()) : null;

                    Coltura c = new Coltura(idColtura, tempoMaturazione, tipoOrtaggio, 0.0);

                    stagioneIdeale = c.getStagioneMigliore();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Errore nel recupero dati";
        }

        return stagioneIdeale;
    }
}