package server;
import java.sql.*;

public class CheckHash {
    public static void main(String[] args) {
        try {
            String url = "jdbc:mysql://localhost:3306/inventory_db";
            String user = "root";
            String pass = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, pass);
            
            String sql = "SELECT sandi FROM user WHERE nama_user = 'admin'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("sandi");
                System.out.println("Hash in DB: " + hash);
                System.out.println("Length: " + hash.length());
            } else {
                System.out.println("User admin not found in DB.");
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
