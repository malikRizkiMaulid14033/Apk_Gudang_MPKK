/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.Koneksi;

/**
 *
 * @author HP
 */
public class Class_akun extends Koneksi {
    
    public Class_akun() {
        try {
            super.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean CekUsername(String username) {
        try {
            String sql = "SELECT * FROM user WHERE nama_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean tambahAkun(String username, String password, String role) {
        try {
            String sql = "INSERT INTO user (nama_user, sandi, role) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean updateAkun(int idUser, String username, String password, String role) {
        try {
            String sql = "UPDATE user SET nama_user = ?, sandi = ?, role = ? WHERE id_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.setInt(4, idUser);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean hapusAkun(int idUser) {
        try {
            String sql = "DELETE FROM user WHERE id_user = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUser);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public ResultSet getAllAkun() {
        try {
            String sql = "SELECT * FROM user";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


