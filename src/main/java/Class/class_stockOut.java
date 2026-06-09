package Class;

import javax.swing.JOptionPane;
import java.sql.PreparedStatement;

public class class_stockOut extends class_reStock {

    @Override
    public boolean simpanTransaksi(String noTransaksi, String namaBarang, int qty, String keterangan, String tanggal) {
        try {
            int idBarang = getBarangIdByNama(namaBarang);
            if (idBarang == -1) {
                JOptionPane.showMessageDialog(null, "Barang tidak ditemukan!");
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
}
