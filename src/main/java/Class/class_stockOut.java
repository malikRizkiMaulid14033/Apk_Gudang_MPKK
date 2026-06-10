package Class;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class class_stockOut extends class_reStock {

    @Override
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

            int idBarang = getBarangIdByNama(namaBarang);
            if (idBarang == -1) {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan! Pastikan memilih barang dari daftar pencarian.");
                return false;
            }

            // Validasi stok cukup
            int currentStok = getCurrentStok(idBarang);
            if (qty > currentStok) {
                JOptionPane.showMessageDialog(null, 
                    "Stok tidak cukup!\nStok saat ini: " + currentStok + "\nQty yang diminta: " + qty,
                    "Stok Tidak Cukup", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            int idUser = server.Session.getIdUser();
            
            String sql = "INSERT INTO barang_keluar (id_barang, id_user, qty_Keluar, keterangan, tanggal, no_transaksi) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setInt(2, idUser);
            ps.setInt(3, qty);
            ps.setString(4, keterangan);
            ps.setString(5, tanggal);
            ps.setString(6, noTransaksi);
            ps.executeUpdate();
            
            // subtract from stock
            updateStok(idBarang, -qty);
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
