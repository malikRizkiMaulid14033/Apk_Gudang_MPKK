/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Component;

import frame.mainFrame;
import Class.Class_sidebar;
import Class.menuItem;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import server.Session;
import main.Login;

/**
 *
 * @author HP
 */
public class sidebarPanel extends javax.swing.JPanel {

    private mainFrame main;
    private Class_sidebar sideBar;
    private menuItem menuDashboard;
    private menuItem menumasterBarang;
    private menuItem menuRestock;
    private menuItem menuStockOut;
    private menuItem menustockOpname;
    private menuItem menuLaporan;
    private menuItem menuManajemenAkun;
    private menuItem menuLogOut;

    /**
     * Creates new form sideBar
     */
    public sidebarPanel(mainFrame main) {
        initComponents();
        this.main = main;
        menuDashboard = new menuItem(panelDashboard, lblDashboard, jLabel8, "/images/navUnactive/dashboard.png", "/images/navActive/dashboard.png");
        menumasterBarang = new menuItem(panelMasterBarang, lbLMasterBarang, jLabel6, "/images/navUnactive/package.png", "/images/navActive/package.png");
        menuRestock = new menuItem(panelMasuk, lblMasuk, jLabel9, "/images/navUnactive/move_to.png", "/images/navActive/move_to.png");
        menuStockOut = new menuItem(panelKeluar, lblKeluar, jLabel10, "/images/navUnactive/outbox.png", "/images/navActive/outbox.png");
        menustockOpname = new menuItem(panelOpname, lblOpname, jLabel11, "/images/navUnactive/checkbook.png", "/images/navActive/checkbook.png");
        menuLaporan = new menuItem(panelLaporan, lblLaporan, jLabel7, "/images/navUnactive/analytics.png", "/images/navActive/analytics.png");
        menuManajemenAkun = new menuItem(panelManajemen, labelManajemen, jLabel12, "/images/navUnactive/manage_accounts.png", "/images/navActive/manage_accounts.png");
        menuLogOut = new menuItem(panelLogOut, labelLogOut, jLabel4, "/images/navActive/logOut.png", "/images/navActive/logOut.png");

        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);
        menuDashboard.setActive();
        panelDashboard.repaint();

        // Terapkan privilege sesuai role
        applyPrivilege();
    }

    /**
     * Sembunyikan/tampilkan menu berdasarkan role: - admin : semua menu tampil
     * - staff : tidak bisa akses masterBarang & manajemenAkun - boss : hanya
     * bisa akses laporan (+ dashboard & logout)
     */
    private void applyPrivilege() {
        String role = server.Session.getRole().toLowerCase();
        switch (role) {
            case "admin":
                // Semua menu tampil
                setMenuVisible(true, true, true, true, true, true, true);
                break;

            case "staff":
                // Tidak bisa masterBarang & manajemenAkun
                setMenuVisible(
                        /* dashboard */true,
                        /* masterBarang */ false,
                        /* restock */ true,
                        /* stockOut */ true,
                        /* opname */ true,
                        /* laporan */ true,
                        /* manajemenAkun */ false
                );
                // Redirect default ke dashboard (sudah ada)
                break;

            case "bos":
            case "boss":
                // Hanya laporan
                setMenuVisible(
                        /* dashboard */false,
                        /* masterBarang */ false,
                        /* restock */ false,
                        /* stockOut */ false,
                        /* opname */ false,
                        /* laporan */ true,
                        /* manajemenAkun */ false
                );
                // Langsung aktifkan visual laporan sebagai default
                menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);
                menuLaporan.setActive();
                break;

            default:
                // Role tidak dikenal → akses minimal (hanya logout)
                setMenuVisible(false, false, false, false, false, false, false);
                break;
        }
    }

    /**
     * Helper untuk set visibility semua panel menu sekaligus
     */
    private void setMenuVisible(boolean dashboard, boolean masterBarang,
            boolean restock, boolean stockOut,
            boolean opname, boolean laporan, boolean manajemenAkun) {
        panelDashboard.setVisible(dashboard);
        panelMasterBarang.setVisible(masterBarang);
        panelMasuk.setVisible(restock);
        panelKeluar.setVisible(stockOut);
        panelOpname.setVisible(opname);
        panelLaporan.setVisible(laporan);
        panelManajemen.setVisible(manajemenAkun);
        // panelLogOut selalu tampil untuk semua role
        panelLogOut.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        panelMasterBarang = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lbLMasterBarang = new javax.swing.JLabel();
        panelLaporan = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblLaporan = new javax.swing.JLabel();
        panelDashboard = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblDashboard = new javax.swing.JLabel();
        panelMasuk = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lblMasuk = new javax.swing.JLabel();
        panelKeluar = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblKeluar = new javax.swing.JLabel();
        panelOpname = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblOpname = new javax.swing.JLabel();
        panelLogOut = new javax.swing.JPanel();
        labelLogOut = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panelManajemen = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        labelManajemen = new javax.swing.JLabel();

        setBackground(new java.awt.Color(242, 244, 247));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 77, 153));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("APLIKASI ");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 77, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("GUDANG");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        jLabel1.setBackground(new java.awt.Color(0, 77, 153));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 77, 153));
        jLabel1.setText("AG");
        jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        panelMasterBarang.setBackground(new java.awt.Color(0, 77, 153));
        panelMasterBarang.setForeground(new java.awt.Color(71, 85, 105));
        panelMasterBarang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelMasterBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMasterBarangMousePressed(evt);
            }
        });
        panelMasterBarang.setLayout(null);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelMasterBarang.add(jLabel6);
        jLabel6.setBounds(10, 10, 20, 20);

        lbLMasterBarang.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lbLMasterBarang.setForeground(new java.awt.Color(255, 255, 255));
        lbLMasterBarang.setText("MASTER BARANG");
        panelMasterBarang.add(lbLMasterBarang);
        lbLMasterBarang.setBounds(40, 10, 100, 16);

        panelLaporan.setBackground(new java.awt.Color(0, 77, 153));
        panelLaporan.setForeground(new java.awt.Color(71, 85, 105));
        panelLaporan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelLaporanMousePressed(evt);
            }
        });
        panelLaporan.setLayout(null);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelLaporan.add(jLabel7);
        jLabel7.setBounds(10, 10, 20, 20);

        lblLaporan.setBackground(new java.awt.Color(71, 85, 105));
        lblLaporan.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblLaporan.setForeground(new java.awt.Color(255, 255, 255));
        lblLaporan.setText("LAPORAN");
        lblLaporan.setFocusable(false);
        panelLaporan.add(lblLaporan);
        lblLaporan.setBounds(40, 10, 90, 20);

        panelDashboard.setBackground(new java.awt.Color(0, 77, 153));
        panelDashboard.setForeground(new java.awt.Color(71, 85, 105));
        panelDashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelDashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelDashboardMousePressed(evt);
            }
        });
        panelDashboard.setLayout(null);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelDashboard.add(jLabel8);
        jLabel8.setBounds(10, 10, 20, 20);

        lblDashboard.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lblDashboard.setText("DASHBOARD");
        panelDashboard.add(lblDashboard);
        lblDashboard.setBounds(40, 10, 80, 16);

        panelMasuk.setBackground(new java.awt.Color(0, 77, 153));
        panelMasuk.setForeground(new java.awt.Color(71, 85, 105));
        panelMasuk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelMasuk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelMasukMousePressed(evt);
            }
        });
        panelMasuk.setLayout(null);

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelMasuk.add(jLabel9);
        jLabel9.setBounds(10, 10, 20, 20);

        lblMasuk.setBackground(new java.awt.Color(71, 85, 105));
        lblMasuk.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblMasuk.setForeground(new java.awt.Color(255, 255, 255));
        lblMasuk.setText("RE-STOCK");
        lblMasuk.setFocusable(false);
        panelMasuk.add(lblMasuk);
        lblMasuk.setBounds(40, 10, 90, 20);

        panelKeluar.setBackground(new java.awt.Color(0, 77, 153));
        panelKeluar.setForeground(new java.awt.Color(71, 85, 105));
        panelKeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelKeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelKeluarMousePressed(evt);
            }
        });
        panelKeluar.setLayout(null);

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelKeluar.add(jLabel10);
        jLabel10.setBounds(10, 10, 20, 20);

        lblKeluar.setBackground(new java.awt.Color(71, 85, 105));
        lblKeluar.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblKeluar.setForeground(new java.awt.Color(255, 255, 255));
        lblKeluar.setText("STOCK OUT");
        lblKeluar.setFocusable(false);
        panelKeluar.add(lblKeluar);
        lblKeluar.setBounds(40, 10, 90, 20);

        panelOpname.setBackground(new java.awt.Color(0, 77, 153));
        panelOpname.setForeground(new java.awt.Color(71, 85, 105));
        panelOpname.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelOpname.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelOpnameMousePressed(evt);
            }
        });
        panelOpname.setLayout(null);

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add.png"))); // NOI18N
        panelOpname.add(jLabel11);
        jLabel11.setBounds(10, 10, 20, 20);

        lblOpname.setBackground(new java.awt.Color(71, 85, 105));
        lblOpname.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        lblOpname.setForeground(new java.awt.Color(255, 255, 255));
        lblOpname.setText("STOCK OPNAME");
        lblOpname.setFocusable(false);
        panelOpname.add(lblOpname);
        lblOpname.setBounds(40, 10, 90, 20);

        panelLogOut.setBackground(new java.awt.Color(255, 255, 255));
        panelLogOut.setForeground(new java.awt.Color(71, 85, 105));
        panelLogOut.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelLogOut.setOpaque(false);
        panelLogOut.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelLogOutMousePressed(evt);
            }
        });
        panelLogOut.setLayout(null);

        labelLogOut.setBackground(new java.awt.Color(255, 255, 255));
        labelLogOut.setFont(new java.awt.Font("Inter", 1, 11)); // NOI18N
        labelLogOut.setForeground(new java.awt.Color(186, 26, 26));
        labelLogOut.setText("LOGOUT");
        labelLogOut.setFocusable(false);
        panelLogOut.add(labelLogOut);
        labelLogOut.setBounds(40, 10, 90, 20);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/navActive/logOut.png"))); // NOI18N
        panelLogOut.add(jLabel4);
        jLabel4.setBounds(10, 10, 22, 22);

        panelManajemen.setBackground(new java.awt.Color(0, 77, 153));
        panelManajemen.setForeground(new java.awt.Color(71, 85, 105));
        panelManajemen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        panelManajemen.setOpaque(false);
        panelManajemen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelManajemenMousePressed(evt);
            }
        });
        panelManajemen.setLayout(null);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/navActive/manage_accounts.png"))); // NOI18N
        panelManajemen.add(jLabel12);
        jLabel12.setBounds(10, 10, 20, 20);

        labelManajemen.setBackground(new java.awt.Color(71, 85, 105));
        labelManajemen.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        labelManajemen.setForeground(new java.awt.Color(51, 51, 51));
        labelManajemen.setText("MANAJEMEN AKUN");
        labelManajemen.setFocusable(false);
        panelManajemen.add(labelManajemen);
        labelManajemen.setBounds(40, 10, 120, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(panelDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMasuk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelMasterBarang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelKeluar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelOpname, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addComponent(panelLogOut, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                    .addComponent(panelManajemen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(52, 52, 52)
                .addComponent(panelDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMasterBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelOpname, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(panelManajemen, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelLogOut, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(75, 75, 75))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void panelMasterBarangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMasterBarangMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menumasterBarang.setActive();
        main.showPage("masterBarang", "MASTER BARANG");
        panelMasterBarang.repaint();

    }//GEN-LAST:event_panelMasterBarangMousePressed

    private void panelLaporanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLaporanMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menuLaporan.setActive();
        main.showPage("laporan", "LAPORAN");
        panelLaporan.repaint();
    }//GEN-LAST:event_panelLaporanMousePressed

    private void panelDashboardMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelDashboardMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menuDashboard.setActive();
        main.showPage("dashboard", "DASHBOARD");
        panelDashboard.repaint();


    }//GEN-LAST:event_panelDashboardMousePressed

    private void panelMasukMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelMasukMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menuRestock.setActive();
        main.showPage("reStock", "RE-STOCK");
        panelMasuk.repaint();
    }//GEN-LAST:event_panelMasukMousePressed

    private void panelKeluarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelKeluarMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menuStockOut.setActive();
        main.showPage("stockOut", "STOCK OUT");
        panelKeluar.repaint();
    }//GEN-LAST:event_panelKeluarMousePressed

    private void panelOpnameMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelOpnameMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);

        menustockOpname.setActive();
        main.showPage("stokOpname", "STOK OPNAME");
        panelOpname.repaint();
    }//GEN-LAST:event_panelOpnameMousePressed

    private void panelLogOutMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelLogOutMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.clearSession();
//            this.dispose
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.dispose();

            Login loginForm = new Login();
            loginForm.setLocationRelativeTo(null);
            loginForm.setVisible(true);
        }

    }//GEN-LAST:event_panelLogOutMousePressed

    private void panelManajemenMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelManajemenMousePressed
        // TODO add your handling code here:
        menuItem.resetMenu(menumasterBarang, menuDashboard, menuRestock, menuStockOut, menustockOpname, menuLaporan, menuManajemenAkun, menuLogOut);
        menuManajemenAkun.setActive();
        main.showPage("CrudAkun", "MANAJEMEN AKUN");
        panelManajemen.repaint();
    }//GEN-LAST:event_panelManajemenMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel labelLogOut;
    private javax.swing.JLabel labelManajemen;
    private javax.swing.JLabel lbLMasterBarang;
    private javax.swing.JLabel lblDashboard;
    private javax.swing.JLabel lblKeluar;
    private javax.swing.JLabel lblLaporan;
    private javax.swing.JLabel lblMasuk;
    private javax.swing.JLabel lblOpname;
    private javax.swing.JPanel panelDashboard;
    private javax.swing.JPanel panelKeluar;
    private javax.swing.JPanel panelLaporan;
    private javax.swing.JPanel panelLogOut;
    private javax.swing.JPanel panelManajemen;
    private javax.swing.JPanel panelMasterBarang;
    private javax.swing.JPanel panelMasuk;
    private javax.swing.JPanel panelOpname;
    // End of variables declaration//GEN-END:variables
}
