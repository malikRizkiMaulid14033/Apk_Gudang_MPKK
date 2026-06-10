/*
 * Dashboard data class
 */
package Class;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import server.Koneksi;

/**
 * Menyediakan data untuk halaman Dashboard:
 *  - Total stok semua barang (SUM dari tabel stok)
 *  - Jumlah terjual hari ini (SUM qty_Keluar hari ini)
 *  - Jumlah restock hari ini (SUM qty_Masuk hari ini)
 *  - 5 transaksi terbaru (gabungan masuk, keluar, opname) dengan filter tipe
 */
public class Class_Dashboard extends Koneksi {

    public Class_Dashboard() {
        try {
            super.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Total stok semua barang = SUM(Qty) dari tabel stok
     */
    public int getTotalStok() {
        try {
            String sql = "SELECT COALESCE(SUM(CAST(Qty AS UNSIGNED)), 0) AS total FROM stok";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Total barang terjual hari ini (qty_Keluar dari barang_keluar WHERE DATE(tanggal) = CURDATE())
     */
    public int getTerjualHariIni() {
        try {
            String sql = "SELECT COALESCE(SUM(qty_Keluar), 0) AS total FROM barang_keluar WHERE DATE(tanggal) = CURDATE()";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Total barang restock hari ini (qty_Masuk dari barang_masuk WHERE DATE(tanggal) = CURDATE())
     */
    public int getRestokHariIni() {
        try {
            String sql = "SELECT COALESCE(SUM(qty_Masuk), 0) AS total FROM barang_masuk WHERE DATE(tanggal) = CURDATE()";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 5 transaksi terbaru dari semua tipe (Masuk, Keluar, Opname)
     * Dapat difilter berdasarkan tipe: "Masuk", "Keluar", "Opname", atau null/kosong = semua
     *
     * Kolom hasil: tanggal, nama_barang, tipe_transaksi, jumlah
     */
    public ResultSet getTransaksiTerbaru(String filterTipe) {
        try {
            StringBuilder sql = new StringBuilder(
                "SELECT * FROM (" +
                    "SELECT bm.tanggal, mb.nama_barang, 'Masuk' AS tipe_transaksi, bm.qty_Masuk AS jumlah " +
                    "FROM barang_masuk bm JOIN master_barang mb ON bm.id_barang = mb.id_barang " +
                    "UNION ALL " +
                    "SELECT bk.tanggal, mb.nama_barang, 'Keluar' AS tipe_transaksi, bk.qty_Keluar AS jumlah " +
                    "FROM barang_keluar bk JOIN master_barang mb ON bk.id_barang = mb.id_barang " +
                    "UNION ALL " +
                    "SELECT so.tanggal, mb.nama_barang, 'Opname' AS tipe_transaksi, so.stok_di_gudang AS jumlah " +
                    "FROM stock_opname so JOIN master_barang mb ON so.id_barang = mb.id_barang" +
                ") AS semua_transaksi"
            );

            boolean hasFilter = (filterTipe != null
                    && !filterTipe.trim().isEmpty()
                    && !filterTipe.equalsIgnoreCase("Semua")
                    && !filterTipe.equalsIgnoreCase("FILTER"));

            if (hasFilter) {
                sql.append(" WHERE tipe_transaksi = ?");
            }

            sql.append(" ORDER BY tanggal DESC LIMIT 5");

            PreparedStatement ps = conn.prepareStatement(sql.toString());
            if (hasFilter) {
                ps.setString(1, filterTipe);
            }
            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
