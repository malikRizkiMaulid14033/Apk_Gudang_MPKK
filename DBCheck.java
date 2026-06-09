import java.sql.*;
public class DBCheck {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_db", "root", "");
            Statement stmt = conn.createStatement();
            String[] tables = {"master_barang", "barang_masuk", "barang_keluar", "stock_opname", "stok"};
            for (String table : tables) {
                System.out.println("--- " + table + " ---");
                try {
                    ResultSet rs = stmt.executeQuery("DESCRIBE " + table);
                    while (rs.next()) {
                        System.out.println(rs.getString("Field") + " - " + rs.getString("Type"));
                    }
                } catch (Exception e) {
                    System.out.println("Table " + table + " error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
