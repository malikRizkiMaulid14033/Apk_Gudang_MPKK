package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Koneksi {
    public Connection conn;
    public Statement statement;

    public Koneksi() {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Koneksi Gagal: " + e.getMessage());
        }
    }

    public void createStatement() throws SQLException {
        statement = conn.createStatement();
    }
}
