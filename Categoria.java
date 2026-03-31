package org.uninabiogarden.oobd68.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/unina_biogarden?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL non trovato nel progetto!", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("DEBUG: Connessione a MySQL riuscita!");
            }
        } catch (SQLException e) {
            System.err.println("ERRORE: Impossibile connettersi a MySQL.");
            System.err.println("Messaggio: " + e.getMessage());
            e.printStackTrace();
        }
    }
}