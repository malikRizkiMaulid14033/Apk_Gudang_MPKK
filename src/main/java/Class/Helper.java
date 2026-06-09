package Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    
    public static String getSystemDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String generateAutoKode(String prefix, String table, String column, String idColumn, Connection conn) {
        String newKode = prefix + "-0001";
        try {
            String sql = "SELECT " + column + " FROM " + table + " ORDER BY " + idColumn + " DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String lastKode = rs.getString(column);
                if (lastKode != null && lastKode.startsWith(prefix + "-")) {
                    String numStr = lastKode.substring((prefix + "-").length());
                    try {
                        int num = Integer.parseInt(numStr.trim());
                        newKode = prefix + "-" + String.format("%04d", num + 1);
                    } catch (NumberFormatException e) {
                        // ignore and fall back to default
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newKode;
    }
}
