/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.awt.Color;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import server.Koneksi;

/**
 *
 * @author HP
 */
public class class_masterBarang extends Koneksi {

    public class_masterBarang() {
        try {
            super.createStatement();
        } catch (Exception e) {
            Logger.getLogger(class_masterBarang.class.getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
    }
    public DefaultTableModel modelMaster = new DefaultTableModel();

    public void initTabelMaster(JTable tabel) {
        String[] kolom = {"id", "kode barang", "nama barang", "kategori", "harga beli", "harga jual", "stok awal", "created at"};
        modelMaster.setColumnIdentifiers(kolom);
    }

    public void tampilData() {
        try {
            modelMaster.setRowCount(0);
            String sql = "SELECT * FROM master_barang ORDER BY id_barang DESC LIMIT 50";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_barang");
                String kode = rs.getString("kode_barang");
                String nama = rs.getString("nama_barang");
                String kategori = rs.getString("kategori");
                int harga_beli = rs.getInt("harga_beli");
                int harga_jual = rs.getInt("harga_jual");
                int stok_awal = rs.getInt("stok_awal");
                String tanggal = rs.getString("created_at");

                Object[] data = {id, kode, nama, kategori, harga_beli, harga_jual, stok_awal, tanggal};
                modelMaster.addRow(data);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    public void cariBarang(String nama) {
        modelMaster.setRowCount(0);

        try {
            String sql = "SELECT * FROM master_barang WHERE nama_barang LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nama + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                modelMaster.addRow(new Object[]{
                    rs.getInt("id_barang"),
                    rs.getString("kode_barang"),
                    rs.getString("nama_barang"),
                    rs.getString("kategori"),
                    rs.getInt("harga_beli"),
                    rs.getInt("harga_jual"),
                    rs.getInt("stok_awal"),
                    rs.getString("created_at")
                }
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void filterTable(JComboBox<String> cmb) {
        try {
            if (cmb.getSelectedItem() == null) {
                return;
            }
            String kategoriDipilih = cmb.getSelectedItem().toString();
            modelMaster.setRowCount(0);
            if (kategoriDipilih.equalsIgnoreCase("Filter")) {
                tampilData();
            } else {
                String sql = "SELECT * FROM master_barang WHERE kategori = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, kategoriDipilih);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    modelMaster.addRow((new Object[]{
                        rs.getInt("id_barang"),
                        rs.getString("kode_barang"),
                        rs.getString("nama_barang"),
                        rs.getString("kategori"),
                        rs.getInt("harga_beli"),
                        rs.getInt("harga_jual"),
                        rs.getInt("stok_awal"),
                        rs.getString("created_at")
                    }));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void tampilKategori(JComboBox<String> cmb) {

        try {
            String sql = "SELECT DISTINCT kategori FROM master_barang WHERE kategori IS NOT NULL AND kategori != '' ORDER BY kategori ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            cmb.removeAllItems();

            cmb.addItem("Filter");

            while (rs.next()) {
                cmb.addItem(rs.getString("kategori"));
            }

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Gagal menampilkan kategori : " + e.getMessage());
        }
        cmb.setForeground(Color.white);
    }

    public void simpanData(String kode, String nama, String kategori, int hargaBeli, int hargaJual, int stokAwal) {
        try {
            String sql = "INSERT INTO master_barang (kode_barang, nama_barang, kategori, harga_beli, harga_jual, stok_awal, id_user) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, kode);
            ps.setString(2, nama);
            ps.setString(3, kategori);
            ps.setInt(4, hargaBeli);
            ps.setInt(5, hargaJual);
            ps.setInt(6, stokAwal);
            ps.setInt(7, server.Session.getIdUser());

            ps.executeUpdate();

            // Simpan stok_awal ke tabel stok
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newId = generatedKeys.getInt(1);
                String sqlStok = "INSERT INTO stok (Id_Barang, Qty) VALUES (?, ?)";
                PreparedStatement psStok = conn.prepareStatement(sqlStok);
                psStok.setInt(1, newId);
                psStok.setString(2, String.valueOf(stokAwal));
                psStok.executeUpdate();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Simpan: " + e.getMessage());
        }
    }

    public void ubahData(int selectedId, String nama, String kategori, int hargaBeli, int hargaJual, int stokAwal) {
        try {
            // 1. Ambil stok_awal lama
            int stokAwalLama = 0;
            String sqlOld = "SELECT stok_awal FROM master_barang WHERE id_barang = ?";
            PreparedStatement psOld = conn.prepareStatement(sqlOld);
            psOld.setInt(1, selectedId);
            ResultSet rsOld = psOld.executeQuery();
            if (rsOld.next()) {
                stokAwalLama = rsOld.getInt("stok_awal");
            }

            // 2. Update master_barang
            String sql = "UPDATE master_barang SET nama_barang = ?, kategori = ?, harga_beli = ?, harga_jual = ?, stok_awal = ? WHERE id_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setString(2, kategori);
            ps.setInt(3, hargaBeli);
            ps.setInt(4, hargaJual);
            ps.setInt(5, stokAwal);
            ps.setInt(6, selectedId);
            ps.executeUpdate();

            // 3. Update stok berdasarkan selisih stok_awal
            int selisih = stokAwal - stokAwalLama;
            if (selisih != 0) {
                String sqlCheckStok = "SELECT Qty FROM stok WHERE Id_Barang = ?";
                PreparedStatement psCheck = conn.prepareStatement(sqlCheckStok);
                psCheck.setInt(1, selectedId);
                ResultSet rsCheck = psCheck.executeQuery();
                if (rsCheck.next()) {
                    int currentQty = Integer.parseInt(rsCheck.getString("Qty").trim());
                    int newQty = currentQty + selisih;
                    if (newQty < 0) newQty = 0;
                    String sqlUpdateStok = "UPDATE stok SET Qty = ? WHERE Id_Barang = ?";
                    PreparedStatement psUpdateStok = conn.prepareStatement(sqlUpdateStok);
                    psUpdateStok.setString(1, String.valueOf(newQty));
                    psUpdateStok.setInt(2, selectedId);
                    psUpdateStok.executeUpdate();
                } else {
                    // Belum ada record stok, buat baru
                    String sqlInsertStok = "INSERT INTO stok (Id_Barang, Qty) VALUES (?, ?)";
                    PreparedStatement psInsert = conn.prepareStatement(sqlInsertStok);
                    psInsert.setInt(1, selectedId);
                    psInsert.setString(2, String.valueOf(stokAwal));
                    psInsert.executeUpdate();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Update: " + e.getMessage());
        }
    }

    public void hapusData(int id) {
        try {
            // Hapus record stok terlebih dahulu
            String sqlStok = "DELETE FROM stok WHERE Id_Barang = ?";
            PreparedStatement psStok = conn.prepareStatement(sqlStok);
            psStok.setInt(1, id);
            psStok.executeUpdate();

            // Hapus master barang
            String sql = "DELETE FROM master_barang WHERE id_barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Delete: " + e.getMessage());
        }
    }
}
