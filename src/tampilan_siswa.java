
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Adhwa
 */
public class tampilan_siswa extends javax.swing.JFrame {

    /**
     * Creates new form tampilan_siswa
     */
    //    UserSession
    String id = userSession.get_id();
    String nama = userSession.get_nama_siswa();
    String level = userSession.get_level();
    String nisn = userSession.get_nisn();

    public JTextField tuse = new javax.swing.JTextField();
    private Connection con = new koneksi().connect();

    public tampilan_siswa() {
        initComponents();
//        tampiltabelpembayaran();
        tampiltabelhistoripembayaransiswa();
        showDate();
        showTime();
    }

    public void tampiltabelpembayaran() {
        String[] kolompembayaran2
                = {"ID PEMBAYARAN", "ID PETUGAS", "NISN", "ID KELAS", "TANGGAL BAYAR", "SEMESTER", "JUMLAH DI BAYAR", "ID SPP", "SISA TAGIHAN"};
        DefaultTableModel model = new DefaultTableModel(kolompembayaran2, 0);
        tabelhistory.setModel(model);

        try {
            String sql = "SELECT * FROM pembayaran where nisn='" + nisn + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String[] baris = {
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9)
                };
                model.addRow(baris);

            }
            System.out.println("" + nisn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    //    Tampil jam
    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MMMMMMMMMM-yyyy");
        String dat = s.format(d);
        tanggal.setText(dat);
    }
//    menampilkan jam

    public void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh-mm-ss");
                String jam = s.format(d);
                jjanam.setText(jam);
            }
        }).start();
    }

    public void tampiltabelhistoripembayaransiswa() {
        try {
            String[] headertabel = {"" + "ID PEMBAYARAN",
                "tanggal bayar", "NISN", "Nama", "NAMA KELAS", "SEMESTER",
                "JUMLAH YANG DIBAYARKAN", "SISA TAGIHAN", "ANGSURAN KE", "STATUS PEMBAYARAN"};

            String sql = "SELECT s.nisn, s.nama,k.nama_kelas, sp.semester,pembayaran.jumlah_bayar ,tagihan.sisa_tagihan,tagihan.angsuran_ke,tagihan.statusbayar,pembayaran.id_pembayaran,pembayaran.tgl_bayar FROM kelas as k inner join siswa as s on k.id_kelas=s.id_kelas inner join spp as sp on sp.id_spp=s.id_spp inner join "
                    + " tagihan on s.nisn=tagihan.nisn  inner join spp on spp.id_spp=tagihan.idspp  inner join kelas  on  kelas.id_kelas=tagihan.id_kelas inner join pembayaran  on pembayaran.id_pembayaran=tagihan.id_pembayaran  where s.nisn='" + tampilnisn.getText() + "'";

            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            ResultSetMetaData meta = (ResultSetMetaData) rs.getMetaData();
            int kolom = meta.getColumnCount();
            int baris = 0;
            while (rs.next()) {
                baris = rs.getRow();
            }
            Object[][] dataSPP = new Object[baris][kolom];
            int x = 0;
            rs.beforeFirst();
            while (rs.next()) {
                dataSPP[x][0] = rs.getString("id_pembayaran");
                dataSPP[x][1] = rs.getString("tgl_bayar");
                dataSPP[x][2] = rs.getString("nisn");
                dataSPP[x][3] = rs.getString("nama");
                dataSPP[x][4] = rs.getString("nama_kelas");
                dataSPP[x][5] = rs.getString("semester");
                dataSPP[x][6] = rs.getString("jumlah_bayar");
                dataSPP[x][7] = rs.getString("sisa_tagihan");
                dataSPP[x][8] = rs.getString("angsuran_ke");
                dataSPP[x][9] = rs.getString("statusbayar");
                x++;
            }
            tabelhistory.setModel(new DefaultTableModel(dataSPP, headertabel));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            System.out.println(e.getMessage()+"yang ini error");

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        namausersis = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelhistory = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tampilnisn = new javax.swing.JLabel();
        tampilnama1 = new javax.swing.JLabel();
        tampilid = new javax.swing.JLabel();
        tanggal = new javax.swing.JLabel();
        jjanam = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        namausersis.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jPanel1.add(namausersis, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 36, 156, 24));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(204, 0, 204));
        jLabel6.setText("Data Siswa");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 100, 121, 36));

        tabelhistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabelhistory);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 720, 180));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));

        jLabel8.setText("Nama Siswa");

        jLabel9.setText("KELAS");

        jLabel7.setText("Nisn");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(tampilnisn, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(96, 96, 96)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(tampilnama1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(79, 79, 79)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tampilid, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tampilid, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tampilnama1, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                            .addComponent(tampilnisn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 730, 60));

        tanggal.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        tanggal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel1.add(tanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 140, 40));

        jjanam.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jPanel1.add(jjanam, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 140, 40));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/bg siswa.png"))); // NOI18N
        jLabel4.setText("jLabel4");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 2, 1080, 760));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 770));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(tampilan_siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tampilan_siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tampilan_siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tampilan_siswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tampilan_siswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jjanam;
    public javax.swing.JLabel namausersis;
    private javax.swing.JTable tabelhistory;
    public javax.swing.JLabel tampilid;
    public javax.swing.JLabel tampilnama1;
    public javax.swing.JLabel tampilnisn;
    private javax.swing.JLabel tanggal;
    // End of variables declaration//GEN-END:variables
}
