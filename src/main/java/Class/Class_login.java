package Class;

import server.Koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Class_login extends Koneksi {

    public Class_login() {
        try {
            super.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Class_login.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    public boolean cekLogin(String username, String password) {
        try {
            // Cek langsung dengan plain text (tanpa hash)
            String sql = "SELECT * FROM user WHERE nama_user=? AND sandi=?";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setString(1, username);
            pstat.setString(2, password);
            ResultSet rs = pstat.executeQuery();
            
            if (rs.next()) {
                // Simpan data session
                server.Session.setIdUser(rs.getInt("id_user"));
                server.Session.setUsername(rs.getString("nama_user"));
                server.Session.setRole(rs.getString("role"));
                return true; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal cek login: " + e.getMessage());
        }
        return false;
    }
}
