package Class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import server.Koneksi; // Menggunakan koneksi DB yang sudah ada di project

public class Class_Laporan_Barang extends Koneksi {
    
    /**
     * Memuat daftar kategori yang unik dari tabel master_barang ke dalam ComboBox.
     */
    public void loadKategori(JComboBox<String> cbKategori) {
        cbKategori.removeAllItems();
        cbKategori.addItem("Semua Kategori"); // Pilihan default
        
        String sql = "SELECT DISTINCT kategori FROM master_barang WHERE kategori IS NOT NULL AND kategori != ''";
        
        try (
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cbKategori.addItem(rs.getString("kategori"));
            }
        } catch (Exception e) {
            System.err.println("Gagal memuat kategori: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Menampilkan data ke JTable berdasarkan filter kategori dan pengurutan (sorting).
     */
    public void tampilkanData(JTable tabelLaporan, String kategoriPilihan, String filterBerdasarkan) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Stok Awal");
        model.addColumn("Stok Masuk");
        model.addColumn("Stok Akhir");
        
        tabelLaporan.setModel(model);
        
        // Query perhitungan stok menggunakan subquery untuk menghindari Cartesian product
        StringBuilder sql = new StringBuilder(
            "SELECT mb.id_barang, mb.nama_barang, mb.kategori, " +
            "mb.stok_awal, " +
            "COALESCE(bm.total_masuk, 0) AS stok_masuk, " +
            "COALESCE(bk.total_keluar, 0) AS stok_keluar, " +
            "COALESCE(so.total_selisih, 0) AS selisih_opname, " +
            "(mb.stok_awal + COALESCE(bm.total_masuk, 0) - COALESCE(bk.total_keluar, 0) + COALESCE(so.total_selisih, 0)) AS stok_akhir " +
            "FROM master_barang mb " +
            "LEFT JOIN (SELECT id_barang, SUM(qty_Masuk) AS total_masuk FROM barang_masuk GROUP BY id_barang) bm ON mb.id_barang = bm.id_barang " +
            "LEFT JOIN (SELECT id_barang, SUM(qty_Keluar) AS total_keluar FROM barang_keluar GROUP BY id_barang) bk ON mb.id_barang = bk.id_barang " +
            "LEFT JOIN (SELECT id_barang, SUM(selisih) AS total_selisih FROM stock_opname GROUP BY id_barang) so ON mb.id_barang = so.id_barang " +
            "WHERE 1=1 "
        );
        
        // Filter Kategori
        boolean filterAdaKategori = kategoriPilihan != null && !kategoriPilihan.equals("Semua Kategori");
        if (filterAdaKategori) {
            sql.append("AND mb.kategori = ? ");
        }
        
        // Filter Urutan (Order By)
        if (filterBerdasarkan != null) {
            switch (filterBerdasarkan) {
                case "Nama Barang A-Z":
                    sql.append("ORDER BY mb.nama_barang ASC");
                    break;
                case "Kategori":
                    sql.append("ORDER BY mb.kategori ASC, mb.nama_barang ASC");
                    break;
                case "Stok Tertinggi":
                    sql.append("ORDER BY stok_akhir DESC");
                    break;
                case "Stok Terendah":
                    sql.append("ORDER BY stok_akhir ASC");
                    break;
                default:
                    sql.append("ORDER BY mb.id_barang ASC");
                    break;
            }
        } else {
            sql.append("ORDER BY mb.id_barang ASC"); // Default urutan
        }
        
        try (
             PreparedStatement pst = conn.prepareStatement(sql.toString())) {
            
            // Set parameter kategori jika difilter
            if (filterAdaKategori) {
                pst.setString(1, kategoriPilihan);
            }
            
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Update stok table if necessary (as per user request: "hasilnya nanti akan masuk ke tabel stok")
                    int idBarang = rs.getInt("id_barang");
                    int stokAkhir = rs.getInt("stok_akhir");
                    updateStokTable(idBarang, stokAkhir);
                    
                    model.addRow(new Object[]{
                        idBarang,
                        rs.getString("nama_barang"),
                        rs.getString("kategori"),
                        rs.getInt("stok_awal"),
                        rs.getInt("stok_masuk"),
                        stokAkhir
                    });
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal menampilkan data laporan: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Memperbarui tabel stok dengan stok akhir terbaru
     */
    private void updateStokTable(int idBarang, int stokAkhir) {
        String checkSql = "SELECT id_Stok FROM stok WHERE Id_Barang = ?";
        String updateSql = "UPDATE stok SET Qty = ? WHERE Id_Barang = ?";
        String insertSql = "INSERT INTO stok (Id_Barang, Qty) VALUES (?, ?)";
        
        try (PreparedStatement checkPst = conn.prepareStatement(checkSql)) {
            checkPst.setInt(1, idBarang);
            ResultSet rs = checkPst.executeQuery();
            if (rs.next()) {
                try (PreparedStatement updatePst = conn.prepareStatement(updateSql)) {
                    updatePst.setString(1, String.valueOf(stokAkhir));
                    updatePst.setInt(2, idBarang);
                    updatePst.executeUpdate();
                }
            } else {
                try (PreparedStatement insertPst = conn.prepareStatement(insertSql)) {
                    insertPst.setInt(1, idBarang);
                    insertPst.setString(2, String.valueOf(stokAkhir));
                    insertPst.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.err.println("Gagal update tabel stok: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
