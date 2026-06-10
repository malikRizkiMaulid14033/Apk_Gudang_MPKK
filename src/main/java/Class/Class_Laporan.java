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
public class Class_Laporan extends Koneksi {
    
    public Class_Laporan() {
        try {
            super.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Mengambil data laporan gabungan (Barang Masuk & Barang Keluar) dengan filter dinamis
     */
    public ResultSet getLaporanTransaksi(String kategori, String dariTanggal, String sampaiTanggal, String sortBy) {
        try {
            // Menggunakan UNION ALL untuk menggabungkan transaksi masuk dan keluar
            // Dibungkus dalam Subquery (AS laporan_transaksi) agar bisa difilter secara global
            StringBuilder sql = new StringBuilder(
                "SELECT * FROM (" +
                    "SELECT mb.kode_barang, mb.nama_barang, mb.kategori, 'Restok' AS tipe_transaksi, bm.tanggal, bm.qty_Masuk AS jumlah " +
                    "FROM barang_masuk bm " +
                    "JOIN master_barang mb ON bm.id_barang = mb.id_barang " +
                    "UNION ALL " +
                    "SELECT mb.kode_barang, mb.nama_barang, mb.kategori, 'Jual' AS tipe_transaksi, bk.tanggal, bk.qty_Keluar AS jumlah " +
                    "FROM barang_keluar bk " +
                    "JOIN master_barang mb ON bk.id_barang = mb.id_barang" +
                ") AS laporan_transaksi " +
                "WHERE 1=1"
            );

            // Filter Kategori
            boolean filterKategori = (kategori != null && !kategori.equalsIgnoreCase("Semua Kategori") && !kategori.trim().isEmpty());
            if (filterKategori) {
                sql.append(" AND kategori = ?");
            }

            // Filter Rentang Tanggal
            boolean filterTanggal = (dariTanggal != null && !dariTanggal.trim().isEmpty() && 
                                     sampaiTanggal != null && !sampaiTanggal.trim().isEmpty());
            if (filterTanggal) {
                sql.append(" AND DATE(tanggal) BETWEEN ? AND ?");
            }

            // Order By (Urutkan Berdasarkan) termasuk Tipe Transaksi
            if (sortBy != null) {
                if (sortBy.equalsIgnoreCase("Nama Barang (A-Z)")) {
                    sql.append(" ORDER BY nama_barang ASC");
                } else if (sortBy.equalsIgnoreCase("Nama Barang (Z-A)")) {
                    sql.append(" ORDER BY nama_barang DESC");
                } else if (sortBy.equalsIgnoreCase("Tipe Transaksi")) {
                    sql.append(" ORDER BY tipe_transaksi ASC");
                } else if (sortBy.equalsIgnoreCase("Kategori")) {
                    sql.append(" ORDER BY kategori ASC");
                } else {
                    sql.append(" ORDER BY tanggal DESC"); // Default: Transaksi terbaru di atas
                }
            } else {
                sql.append(" ORDER BY tanggal DESC");
            }

            PreparedStatement ps = conn.prepareStatement(sql.toString());

            // Set parameter dinamis untuk PreparedStatement
            int paramIndex = 1;
            if (filterKategori) {
                ps.setString(paramIndex++, kategori);
            }
            if (filterTanggal) {
                ps.setString(paramIndex++, dariTanggal);
                ps.setString(paramIndex++, sampaiTanggal);
            }

            return ps.executeQuery();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Mengambil data laporan transaksi tanpa filter (Data Default)
     */
    public ResultSet getAllLaporanTransaksi() {
        try {
            String sql = "SELECT * FROM (" +
                            "SELECT mb.kode_barang, mb.nama_barang, mb.kategori, 'Restok' AS tipe_transaksi, bm.tanggal, bm.qty_Masuk AS jumlah " +
                            "FROM barang_masuk bm " +
                            "JOIN master_barang mb ON bm.id_barang = mb.id_barang " +
                            "UNION ALL " +
                            "SELECT mb.kode_barang, mb.nama_barang, mb.kategori, 'Jual' AS tipe_transaksi, bk.tanggal, bk.qty_Keluar AS jumlah " +
                            "FROM barang_keluar bk " +
                            "JOIN master_barang mb ON bk.id_barang = mb.id_barang" +
                         ") AS laporan_transaksi " +
                         "ORDER BY tanggal DESC"; // Menampilkan riwayat transaksi terbaru paling atas
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Mengambil daftar kategori unik untuk ComboBox
     */
    public ResultSet getKategoriList() {
        try {
            String sql = "SELECT DISTINCT kategori FROM master_barang ORDER BY kategori ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}