package Class;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class class_stokOpname extends class_reStock {

    public int getStokSistem(int idBarang) {
        try {
            String sql = "SELECT Qty FROM stok WHERE Id_Barang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Integer.parseInt(rs.getString("Qty").trim());
            } else {
                String sqlM = "SELECT stok_awal FROM master_barang WHERE id_barang = ?";
                PreparedStatement psM = conn.prepareStatement(sqlM);
                psM.setInt(1, idBarang);
                ResultSet rsM = psM.executeQuery();
                if (rsM.next()) {
                    return rsM.getInt("stok_awal");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateStokToValue(int idBarang, int targetQty) {
        try {
            String sqlCheck = "SELECT Qty FROM stok WHERE Id_Barang = ?";
            PreparedStatement psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, idBarang);
            ResultSet rsCheck = psCheck.executeQuery();
            if (rsCheck.next()) {
                String sqlUpdate = "UPDATE stok SET Qty = ? WHERE Id_Barang = ?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, String.valueOf(targetQty));
                psUpdate.setInt(2, idBarang);
                psUpdate.executeUpdate();
            } else {
                String sqlInsert = "INSERT INTO stok (Id_Barang, Qty) VALUES (?, ?)";
                PreparedStatement psInsert = conn.prepareStatement(sqlInsert);
                psInsert.setInt(1, idBarang);
                psInsert.setString(2, String.valueOf(targetQty));
                psInsert.executeUpdate();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal update stok: " + e.getMessage());
        }
    }

    @Override
    public boolean simpanTransaksi(String noTransaksi, String namaBarang, int stokDiGudang, String keterangan, String tanggal) {
        try {
            int idBarang = getBarangIdByNama(namaBarang);
            if (idBarang == -1) {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
                return false;
            }
            int idUser = server.Session.getIdUser();
            int stokSistem = getStokSistem(idBarang);
            int selisih = stokDiGudang - stokSistem;
            int barangHilang = (selisih < 0) ? Math.abs(selisih) : 0;
            
            String sql = "INSERT INTO stock_opname (id_barang, id_user, barang_Hilang, keterangan, stok_di_gudang, selisih, tanggal, no_transaksi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);
            ps.setInt(2, idUser);
            ps.setInt(3, barangHilang);
            ps.setString(4, keterangan);
            ps.setInt(5, stokDiGudang);
            ps.setInt(6, selisih);
            ps.setString(7, tanggal);
            ps.setString(8, noTransaksi);
            ps.executeUpdate();
            
            // Set stock to actual physical count
            updateStokToValue(idBarang, stokDiGudang);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal simpan transaksi: " + e.getMessage());
            return false;
        }
    }
}
