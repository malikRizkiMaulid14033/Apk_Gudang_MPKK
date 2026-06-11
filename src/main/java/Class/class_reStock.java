/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import server.Koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author HP
 */
public class class_reStock extends Koneksi {

    public DefaultTableModel modelSearch = new DefaultTableModel(new String[]{"Nama Barang"}, 0);

    public void cariBarang(String nama) {
        modelSearch.setRowCount(0);

        try {
            String sql = "SELECT nama_barang FROM master_barang WHERE nama_barang LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nama + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelSearch.addRow(new Object[]{
                    rs.getString("nama_barang")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void setKode(String nama, JLabel kode) {
        try {
            String sql = "SELECT kode_barang FROM master_barang WHERE nama_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kode.setText("Kode : " + rs.getString("kode_barang"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public int getBarangIdByNama(String nama) {
        try {
            String sql = "SELECT id_barang FROM master_barang WHERE nama_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_barang");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void updateStok(int idBarang, int qtyChange) {
        try {
            String sqlCheck = "SELECT Qty FROM stok WHERE Id_Barang = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idBarang);
            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next()) {
                int currentQty = 0;
                try {
                    currentQty = Integer.parseInt(rsCheck.getString("Qty"));
                } catch (NumberFormatException e) {
                }
                int newQty = currentQty + qtyChange;
                String sqlUpdate = "UPDATE stok SET Qty = ? WHERE Id_Barang = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, String.valueOf(newQty));
                psUpdate.setInt(2, idBarang);
                psUpdate.executeUpdate();
            } else {
                String sqlInsert = "INSERT INTO stok (Id_Barang, Qty) VALUES (?, ?)";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setInt(1, idBarang);
                psInsert.setString(2, String.valueOf(qtyChange));
                psInsert.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal update stok: " + e.getMessage());
        }
    }

    public int getCurrentStok(int idBarang) {
        try {
            String sql = "SELECT Qty FROM stok WHERE Id_Barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("Qty").trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean simpanTransaksi(String noTransaksi, String namaBarang, int qty, String keterangan, String tanggal) {
        try {
            // Validasi input
            if (namaBarang == null || namaBarang.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama barang tidak boleh kosong!");
                return false;
            }
            if (qty <= 0) {
                JOptionPane.showMessageDialog(null, "Qty harus lebih dari 0!");
                return false;
            }
            if (noTransaksi == null || noTransaksi.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "No. Transaksi tidak boleh kosong!");
                return false;
            }

            int idBarang = getBarangIdByNama(namaBarang);
            if (idBarang == -1) {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan! Pastikan memilih barang dari daftar pencarian.");
                return false;
            }
            int idUser = server.Session.getIdUser();

            String sql = "INSERT INTO barang_masuk (id_barang, id_user, qty_Masuk, keterangan, tanggal, no_transaksi) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setInt(2, idUser);
            ps.setInt(3, qty);
            ps.setString(4, keterangan);
            ps.setString(5, tanggal);
            ps.setString(6, noTransaksi);
            ps.executeUpdate();

            updateStok(idBarang, qty);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan transaksi: " + e.getMessage());
            return false;
        }
    }

    public int getStokSistem(int idBarang) {
        try {
            String sql = "SELECT Qty FROM stok WHERE Id_Barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("Qty").trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
