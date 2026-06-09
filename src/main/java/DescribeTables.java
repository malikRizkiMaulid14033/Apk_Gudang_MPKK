import java.sql.*;

public class DescribeTables {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_db", "root", "");
            
            String[] tables = {"master_barang", "barang_masuk", "barang_keluar", "stock_opname"};
            for (String table : tables) {
                System.out.println("--- " + table + " ---");
                ResultSet rs = conn.createStatement().executeQuery("DESCRIBE " + table);
                while (rs.next()) {
                    System.out.println(rs.getString("Field") + " - " + rs.getString("Type"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
