
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.interfaces.PBEKey;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Adhwa
 */
public class tampilan extends javax.swing.JFrame {

    /**
     * Creates new form tampilan
     */
    public JTable tabekelas2 = new javax.swing.JTable();
    public JTable tabelsppsen = new javax.swing.JTable();
    private Connection con = new koneksi().connect();
    Calendar kalender = new GregorianCalendar();

//    UserSession
    String id = userSession.get_id();
    String nama = userSession.get_nama();
    String level = userSession.get_level();

    public tampilan() {
        initComponents();
        tablesiswa();
        tablekelas();
        tablepetugas();
        tablespp();
        showDate();
        showTime();
        tampil_comboidkelas();
        tampil_comboidspp();
        tampiltotalsiswa();
        tampiltotalkelas();
        tampiltotalpetugas();
        id_otomatis();
        id_otomatisspp();
        tabelSiswapem();
        ID_AUTO();
    }
    String kelas;
    String spp;

//    Id otomatis untuk kelas....
    public void id_otomatis() {
        try {
            String sql = "select * from kelas order by Id_kelas desc";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            tidkelas.setEnabled(false);
            if (rs.next()) {
                String nourut = rs.getString("Id_kelas").substring(1);
                String an = "" + (Integer.parseInt(nourut) + 1);
                String nol = "";
                if (an.length() == 1) {
                    nol = "00";
                } else if (an.length() == 2) {
                    nol = "0";
                } else if (an.length() == 3) {
                    nol = "";
                }
                tidkelas.setText("K" + nol + an);

            } else {
                tidkelas.setText("K001");
            }

        } catch (Exception e) {
        }
    }

//    Id otomatis untuk Pembayaran
    private void ID_AUTO() {
        try {
            String sql = "select max(right(id_pembayaran,3)) as no_trans from pembayaran";
            PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();

            if (rs.first()) {
                if (rs.getString("no_trans") == null) {
                    tNo_Transaki.setText("TRS001");
                } else {
                    int autoid = Integer.parseInt(rs.getString("no_trans")) + 1;
                    String nomor = String.format("%03d", autoid);
                    tNo_Transaki.setText("TRS" + nomor);
                }
            }
        } catch (Exception e) {
            System.out.println("error pembayaran | " + e);
        }

    }

//    Id otomatis untuk SPP...
    public void id_otomatisspp() {
        try {
            String sql = "select * from spp order by Id_spp desc";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            tidspp.setEnabled(false);
            if (rs.next()) {
                String nourut = rs.getString("Id_spp").substring(1);
                String an = "" + (Integer.parseInt(nourut) + 1);
                String nol = "";
                if (an.length() == 1) {
                    nol = "00";
                } else if (an.length() == 2) {
                    nol = "0";
                } else if (an.length() == 3) {
                    nol = "";
                }
                tidspp.setText("K" + nol + an);

            } else {
                tidspp.setText("K001");
            }

        } catch (Exception e) {
        }
    }

    //menampilkan foreign id kelas pada combobox
    public void tampil_comboidkelas() {
        try {
            String sql = "SELECT * FROM kelas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comboidkelas.addItem(rs.getString("nama_kelas"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "error gakbisa tampil" + e.getMessage());
        }
    }

    //menampilkan item id spp pada combo box
    public void tampil_comboidspp() {
        try {
            String sql = "SELECT * FROM spp";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                comboidspp.addItem(rs.getString("tahun"));

            }

        } catch (Exception e) {
        }
    }

    public void tampiltotalsiswa() {
        try {
            String sql = "SELECT COUNT(*) FROM siswa";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int count = rs.getInt(1);
                tampilsiswa.setText(Integer.toString(count));
            }
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
        } catch (Exception e) {
        }
    }

    public void tampiltotalpetugas() {
        try {
            String sql = "SELECT COUNT(*) FROM petugas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int count = rs.getInt(1);
                distopetugas.setText(Integer.toString(count));
            }
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
        } catch (Exception e) {
        }
    }

    public void tampiltotalkelas() {
        try {
            String sql = "SELECT COUNT(*) FROM kelas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int count = rs.getInt(1);
                distokelas.setText(Integer.toString(count));
            }
            rs.last();
            int jumlahdata = rs.getRow();
            rs.first();
        } catch (Exception e) {
        }
    }
//    Tampil jam

    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MMMMMMMMMM-yyyy");
        String dat = s.format(d);
        ltgl.setText(dat);
    }

    public void showTime() {
        new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date d = new Date();
                SimpleDateFormat s = new SimpleDateFormat("hh-mm-ss");
                String jam = s.format(d);
                ljam.setText(jam);
            }
        }).start();
    }

    private void tabelSiswapem() {
        String[] judul = {"NISN", "NIS", "Nama", "Kelas", "Alamat", "Telepon", "Tahun"};
        DefaultTableModel model = new DefaultTableModel(judul, 0);
        tabelsiswapembayaran.setModel(model);
//        String sql = "SELECT siswa.*, kelas.nama_kelas, spp.tahun from siswa INNER JOIN kelas USING(id_kelas) INNER JOIN spp Using(id_spp) where nisn like '%"+cari.getText()+"%' or nama like '%"+cari.getText()+"%'";

        try {
            String sql = "SELECT siswa.*, kelas.nama_kelas, spp.tahun from siswa INNER JOIN kelas USING(Id_kelas) INNER JOIN spp Using(Id_spp) where nisn like '%" + cari.getText() + "%' or nama like '%" + cari.getText() + "%'";;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String nisn = rs.getString("nisn");
                String nis = rs.getString("nis");
                String nama = rs.getString("nama");
                String kelas = rs.getString("nama_kelas");
                String alamat = rs.getString("alamat");
                String telp = rs.getString("no_telp");
                String tahun = rs.getString("tahun");

                String[] data = {nisn, nis, nama, kelas, alamat, telp, tahun};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void tablesiswa() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("NISN");
        tbl.addColumn("NIS");
        tbl.addColumn("NAMA");
        tbl.addColumn("ID KELAS");
        tbl.addColumn("ALAMAT");
        tbl.addColumn("NO TELP");
        tbl.addColumn("ID SPP");

        try {
            String sql = "SELECT * FROM siswa";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tbl.addRow(new Object[]{
                    rs.getString("nisn"),
                    rs.getString("nis"),
                    rs.getString("nama"),
                    rs.getString("Id_kelas"),
                    rs.getString("alamat"),
                    rs.getString("no_telp"),
                    rs.getString("id_spp")});
                tabelsiswamaster.setModel(tbl);
            }
//            JOptionPane.showMessageDialog(null, "Koneksi berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal" + e.getMessage());
        }

    }

//    untuk panel kelas>>|
    public void tablekelas() {
        DefaultTableModel tbkelas = new DefaultTableModel();
        tbkelas.addColumn("Id Kelas");
        tbkelas.addColumn("Nama Kelas");
        tbkelas.addColumn("Kompetensi Keahlian");

        try {
            String sql = "SELECT * FROM kelas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tbkelas.addRow(new Object[]{
                    rs.getString("Id_kelas"),
                    rs.getString("nama_kelas"),
                    rs.getString("kompetensi_keahlian")});
                tabelkelas.setModel(tbkelas);
            }
//            JOptionPane.showMessageDialog(null, "Koneksi berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal" + e.getMessage());
        }

    }

    public void refreshkelas() {
        try {
            String sql = "SELECT * FROM kelas WHERE Id_kelas ='" + tidkelas.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablekelas();
    }
//untuk panel Petugas>>|

    public void tablepetugas() {
        DefaultTableModel tbpetu = new DefaultTableModel();
        tbpetu.addColumn("Id Petugas");
        tbpetu.addColumn("Username");
        tbpetu.addColumn("Password");
        tbpetu.addColumn("Nama Petugas");
        tbpetu.addColumn("No Telepon");
        tbpetu.addColumn("level");

        try {
            String sql = "SELECT * FROM petugas";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tbpetu.addRow(new Object[]{
                    rs.getString("Id_petugas"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nama_petugas"),
                    rs.getString("no_tlppet"),
                    rs.getString("level"),});
                tabelpetugas.setModel(tbpetu);
            }
//            JOptionPane.showMessageDialog(null, "Koneksi berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal" + e.getMessage());
        }

    }

    public void refreshpetugas() {
        try {
            String sql = "SELECT * FROM petugas WHERE Id_petugas ='" + tidpetugas.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablepetugas();
    }

    public void refreshsiswa() {
        try {
            String sql = "SELECT * FROM siswa WHERE nisn ='" + tnisnsiswa.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablesiswa();
    }

//    SPP
    public void tablespp() {
        DefaultTableModel tbspp = new DefaultTableModel();
        tbspp.addColumn("Id SPP");
        tbspp.addColumn("Tahun Ajaran");
        tbspp.addColumn("Nominal Perbulan");
        tbspp.addColumn("Semester");
        tbspp.addColumn("Total Nominal");

        try {
            String sql = "SELECT * FROM spp";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                tbspp.addRow(new Object[]{
                    rs.getString("Id_spp"),
                    rs.getString("tahun_ajaran"),
                    rs.getString("nominal_perbulan"),
                    rs.getString("semester"),
                    rs.getString("total_nominal_semester")
                });
                
                tabelspp.setModel(tbspp);
            }
//            JOptionPane.showMessageDialog(null, "Koneksi berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi gagal" + e.getMessage());
        }

    }

    public void refreshspp() {
        try {
            String sql = "SELECT * FROM spp WHERE Id_spp ='" + tidspp.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablespp();
        bsimpanspp.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel22 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        panel2 = new java.awt.Panel();
        siswa1 = new javax.swing.JLabel();
        bpetugas1 = new javax.swing.JLabel();
        bspp1 = new javax.swing.JLabel();
        bpembayaran1 = new javax.swing.JLabel();
        bkelas1 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        namauser1 = new javax.swing.JLabel();
        bkeluar1 = new javax.swing.JLabel();
        mainpanel1 = new javax.swing.JPanel();
        Phome1 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        greating1 = new javax.swing.JLabel();
        ltgl1 = new javax.swing.JLabel();
        ljam1 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        Psiswa1 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabelsiswamaster1 = new javax.swing.JTable();
        jPanel30 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        tnisnsiswa1 = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        tnissiswa1 = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        tnamasiswas1 = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        talamatsiswa1 = new javax.swing.JTextArea();
        jLabel51 = new javax.swing.JLabel();
        ttelesiswa1 = new javax.swing.JTextField();
        jLabel52 = new javax.swing.JLabel();
        bsimpansiswa1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        comboidkelas1 = new javax.swing.JComboBox<>();
        comboidspp1 = new javax.swing.JComboBox<>();
        tampilidkelas1 = new javax.swing.JTextField();
        tampidspp1 = new javax.swing.JTextField();
        Ppetugas1 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        tidpetugas1 = new javax.swing.JTextField();
        tuserpetugas1 = new javax.swing.JTextField();
        tpasspetu1 = new javax.swing.JPasswordField();
        tnamapetugas1 = new javax.swing.JTextField();
        ttelp1 = new javax.swing.JTextField();
        boxlevel1 = new javax.swing.JComboBox<>();
        bsimpanpetu2 = new javax.swing.JButton();
        bsimpanpetu3 = new javax.swing.JButton();
        beditpetugas1 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tabelpetugas1 = new javax.swing.JTable();
        Psppp1 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tabelspp1 = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        tidspp1 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        ttahun1 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        tnominal1 = new javax.swing.JTextField();
        bsimpanspp1 = new javax.swing.JButton();
        beditspp1 = new javax.swing.JButton();
        bhapusspp1 = new javax.swing.JButton();
        Ppembayaran1 = new javax.swing.JPanel();
        Pkelas1 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel63 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        tidkelas1 = new javax.swing.JTextField();
        tnamakelas1 = new javax.swing.JTextField();
        tkom1 = new javax.swing.JTextField();
        bsimpankelas1 = new javax.swing.JButton();
        bhapuskelas1 = new javax.swing.JButton();
        beditkelas1 = new javax.swing.JButton();
        btampilkelas1 = new javax.swing.JButton();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        tabelkelas1 = new javax.swing.JTable();
        bcarikelas1 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jPanel42 = new javax.swing.JPanel();
        tnis4 = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        tnis5 = new javax.swing.JTextField();
        tnis6 = new javax.swing.JTextField();
        jLabel69 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        kButton4 = new com.k33ptoo.components.KButton();
        kButton5 = new com.k33ptoo.components.KButton();
        jLabel71 = new javax.swing.JLabel();
        kButton6 = new com.k33ptoo.components.KButton();
        jLabel72 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        tnis7 = new javax.swing.JTextField();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        asli = new javax.swing.JLabel();
        hover = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        panel1 = new java.awt.Panel();
        siswa = new javax.swing.JLabel();
        bpetugas = new javax.swing.JLabel();
        bspp = new javax.swing.JLabel();
        bpembayaran = new javax.swing.JLabel();
        bkelas = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namauser = new javax.swing.JLabel();
        bkeluar = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        Phome = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        greating = new javax.swing.JLabel();
        ltgl = new javax.swing.JLabel();
        ljam = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tampilsiswa = new javax.swing.JLabel();
        distokelas = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        distopetugas = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();
        Psiswa = new javax.swing.JPanel();
        tampilidkelas = new javax.swing.JTextField();
        tampidspp = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelsiswamaster = new javax.swing.JTable();
        txtcarisis = new javax.swing.JTextField();
        comboidspp = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        ttelesiswa = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comboidkelas = new javax.swing.JComboBox<>();
        tnamasiswas = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        tnissiswa = new javax.swing.JTextField();
        tnisnsiswa = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        bsimpansiswa = new javax.swing.JButton();
        btneditsis = new javax.swing.JButton();
        btnhapussis = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        talamatsiswa = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        backssiwa = new javax.swing.JLabel();
        Ppetugas = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        tidpetugas = new javax.swing.JTextField();
        tuserpetugas = new javax.swing.JTextField();
        tpasspetu = new javax.swing.JPasswordField();
        tnamapetugas = new javax.swing.JTextField();
        ttelp = new javax.swing.JTextField();
        boxlevel = new javax.swing.JComboBox<>();
        bsimpanpetu = new javax.swing.JButton();
        btampil = new javax.swing.JButton();
        beditpetugas = new javax.swing.JButton();
        bhapuspetugas = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabelpetugas = new javax.swing.JTable();
        caripetu = new javax.swing.JTextField();
        Psppp = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabelspp = new javax.swing.JTable();
        bhapusspp = new javax.swing.JButton();
        beditspp = new javax.swing.JButton();
        bsimpanspp = new javax.swing.JButton();
        tnominal = new javax.swing.JTextField();
        ttahun = new javax.swing.JTextField();
        tidspp = new javax.swing.JTextField();
        totalnom = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        cgannep = new javax.swing.JComboBox<>();
        caridataspp = new javax.swing.JTextField();
        backspp = new javax.swing.JLabel();
        Ppembayaran = new javax.swing.JPanel();
        cari = new javax.swing.JTextField();
        jScrollPane15 = new javax.swing.JScrollPane();
        tabelsiswapembayaran = new javax.swing.JTable();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        namasiswa = new javax.swing.JLabel();
        tNo_Transaki = new javax.swing.JTextField();
        tNisn = new javax.swing.JTextField();
        tNama_siswa = new javax.swing.JTextField();
        tKelas = new javax.swing.JTextField();
        tId_Angkatan = new javax.swing.JTextField();
        tId_petugas = new javax.swing.JTextField();
        tTanggal = new javax.swing.JTextField();
        tAngkatan = new javax.swing.JLabel();
        tPetugas = new javax.swing.JLabel();
        tnama_siswastatus = new javax.swing.JLabel();
        tJumlahbayar = new javax.swing.JTextField();
        namasiswa1 = new javax.swing.JLabel();
        namasiswa2 = new javax.swing.JLabel();
        namasiswa3 = new javax.swing.JLabel();
        namasiswa4 = new javax.swing.JLabel();
        namasiswa5 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        backpembayaran = new javax.swing.JLabel();
        Pkelas = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelkelas = new javax.swing.JTable();
        btampilkelas = new javax.swing.JButton();
        beditkelas = new javax.swing.JButton();
        bhapuskelas = new javax.swing.JButton();
        bsimpankelas = new javax.swing.JButton();
        tidkelas = new javax.swing.JTextField();
        tnamakelas = new javax.swing.JTextField();
        tkom = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        textcarikelas = new javax.swing.JTextField();
        bcarikelas = new javax.swing.JButton();
        jLabel78 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        tnis = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tnis1 = new javax.swing.JTextField();
        tnis2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        kButton1 = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        jLabel8 = new javax.swing.JLabel();
        kButton3 = new com.k33ptoo.components.KButton();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        tnis3 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jFrame1.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jFrame1.setUndecorated(true);
        jFrame1.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel22.setBackground(new java.awt.Color(45, 205, 223));

        jLabel37.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("APLIKASI PEMBAYARAN SPP");

        jLabel38.setText("jLabel21");
        jLabel38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel38MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 720, Short.MAX_VALUE)
                .addComponent(jLabel38)
                .addGap(16, 16, 16))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jFrame1.getContentPane().add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1080, 60));

        panel2.setBackground(new java.awt.Color(204, 204, 204));

        siswa1.setBackground(new java.awt.Color(153, 153, 153));
        siswa1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        siswa1.setForeground(new java.awt.Color(255, 255, 255));
        siswa1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        siswa1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-student-25.png"))); // NOI18N
        siswa1.setText("MASTER SISWA");
        siswa1.setOpaque(true);
        siswa1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                siswa1MouseClicked(evt);
            }
        });

        bpetugas1.setBackground(new java.awt.Color(153, 153, 153));
        bpetugas1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bpetugas1.setForeground(new java.awt.Color(255, 255, 255));
        bpetugas1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bpetugas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-worker-20.png"))); // NOI18N
        bpetugas1.setText("PETUGAS");
        bpetugas1.setOpaque(true);
        bpetugas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bpetugas1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bpetugas1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bpetugas1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bpetugas1MousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bpetugas1MouseReleased(evt);
            }
        });

        bspp1.setBackground(new java.awt.Color(153, 153, 153));
        bspp1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bspp1.setForeground(new java.awt.Color(255, 255, 255));
        bspp1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bspp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-insert-money-25.png"))); // NOI18N
        bspp1.setText("SPP");
        bspp1.setOpaque(true);
        bspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bspp1MouseClicked(evt);
            }
        });

        bpembayaran1.setBackground(new java.awt.Color(153, 153, 153));
        bpembayaran1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bpembayaran1.setForeground(new java.awt.Color(255, 255, 255));
        bpembayaran1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bpembayaran1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-pay-25.png"))); // NOI18N
        bpembayaran1.setText("PEMBAYARAN");
        bpembayaran1.setOpaque(true);

        bkelas1.setBackground(new java.awt.Color(153, 153, 153));
        bkelas1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bkelas1.setForeground(new java.awt.Color(255, 255, 255));
        bkelas1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bkelas1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-classroom-25.png"))); // NOI18N
        bkelas1.setText("MASTER KELAS");
        bkelas1.setOpaque(true);
        bkelas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bkelas1MouseClicked(evt);
            }
        });

        jLabel39.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(51, 51, 51));
        jLabel39.setText("Selamat Datang");

        namauser1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        namauser1.setForeground(new java.awt.Color(255, 255, 255));

        bkeluar1.setBackground(new java.awt.Color(153, 153, 153));
        bkeluar1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bkeluar1.setForeground(new java.awt.Color(255, 255, 255));
        bkeluar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bkeluar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-classroom-25.png"))); // NOI18N
        bkeluar1.setText("LOG OUT");
        bkeluar1.setOpaque(true);
        bkeluar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bkeluar1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(siswa1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bpetugas1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bspp1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bpembayaran1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(bkelas1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(namauser1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(bkeluar1, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(namauser1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(siswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(bpetugas1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(bspp1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(bpembayaran1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(bkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(bkeluar1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jFrame1.getContentPane().add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 160, 530));

        mainpanel1.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel1.setLayout(new java.awt.CardLayout());

        Phome1.setBackground(new java.awt.Color(235, 235, 235));
        Phome1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));

        jLabel40.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("SISWA");

        jLabel41.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setText("KELAS");

        jLabel42.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("PETUGAS");

        jLabel43.setFont(new java.awt.Font("Montserrat", 1, 14)); // NOI18N
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("PAYMENT");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(82, 82, 82)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(97, 97, 97))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)))
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(99, 99, 99))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 31, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );

        Phome1.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 880, 240));

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-back-arrow-35.png"))); // NOI18N
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel44MouseClicked(evt);
            }
        });
        Phome1.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 10, 40, 40));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));

        greating1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N

        ltgl1.setFont(new java.awt.Font("Ravie", 1, 14)); // NOI18N
        ltgl1.setText("sd");

        ljam1.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        ljam1.setText("sd");

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(greating1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ltgl1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ljam1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(ltgl1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ljam1))
                    .addComponent(greating1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        Phome1.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 790, -1));

        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/pexels-miguel-á-padriñán-1111318.jpg"))); // NOI18N
        jLabel45.setText("jLabel22");
        Phome1.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 710));

        mainpanel1.add(Phome1, "card2");

        Psiswa1.setBackground(new java.awt.Color(248, 248, 248));
        Psiswa1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        tabelsiswamaster1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tabelsiswamaster1);

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel29Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        Psiswa1.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 110, 420, 280));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        jLabel46.setText("Nisn");

        tnisnsiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnisnsiswa1ActionPerformed(evt);
            }
        });

        jLabel47.setText("Nis");

        tnissiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnissiswa1ActionPerformed(evt);
            }
        });

        jLabel48.setText("Nama");

        tnamasiswas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamasiswas1ActionPerformed(evt);
            }
        });

        jLabel49.setText("Id Kelas");

        jLabel50.setText("Alamat");

        talamatsiswa1.setColumns(20);
        talamatsiswa1.setRows(5);
        jScrollPane9.setViewportView(talamatsiswa1);

        jLabel51.setText("No Telpon");

        ttelesiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttelesiswa1ActionPerformed(evt);
            }
        });

        jLabel52.setText("Id Spp");

        bsimpansiswa1.setText("Simpan");
        bsimpansiswa1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpansiswa1ActionPerformed(evt);
            }
        });

        jButton4.setText("Edit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Hapus");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        comboidkelas1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));

        comboidspp1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel30Layout.createSequentialGroup()
                            .addComponent(bsimpansiswa1)
                            .addGap(18, 18, 18)
                            .addComponent(jButton4)
                            .addGap(26, 26, 26)
                            .addComponent(jButton5))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                            .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(27, 27, 27)
                                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ttelesiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboidspp1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel30Layout.createSequentialGroup()
                                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(44, 44, 44)
                                    .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(tnamasiswas1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                        .addComponent(comboidkelas1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGap(12, 12, 12)))
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel30Layout.createSequentialGroup()
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(53, 53, 53)
                                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tnissiswa1, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                                    .addComponent(tnisnsiswa1))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(tnisnsiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tnissiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(tnamasiswas1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel49)
                    .addComponent(comboidkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel50)))
                .addGap(13, 13, 13)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(ttelesiswa1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel52)
                    .addComponent(comboidspp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(bsimpansiswa1))
                .addGap(18, 18, 18))
        );

        Psiswa1.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 390, 480));

        lihatdata1.setText("Lihat Data ");
        lihatdata1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatdata1ActionPerformed(evt);
            }
        });
        Psiswa1.add(lihatdata1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, -1, -1));

        tampilidkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilidkelas1ActionPerformed(evt);
            }
        });
        Psiswa1.add(tampilidkelas1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 154, -1));

        tampidspp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampidspp1ActionPerformed(evt);
            }
        });
        Psiswa1.add(tampidspp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 420, 154, -1));

        lihatdata3.setText("Lihat Data ");
        lihatdata3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatdata3ActionPerformed(evt);
            }
        });
        Psiswa1.add(lihatdata3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 420, -1, -1));

        mainpanel1.add(Psiswa1, "card3");

        Ppetugas1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel31.setBackground(new java.awt.Color(153, 153, 255));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        jLabel53.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel53.setText("Data Petugas");

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel53)
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel53)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel54.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel54.setText("Id Petugas");

        jLabel55.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel55.setText("Username");

        jLabel56.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel56.setText("Password");

        jLabel57.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel57.setText("Nama Petugas");

        jLabel58.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel58.setText("No telepon");

        jLabel59.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel59.setText("Level");

        tidpetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpetugas1ActionPerformed(evt);
            }
        });

        tuserpetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tuserpetugas1ActionPerformed(evt);
            }
        });

        tpasspetu1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tpasspetu1ActionPerformed(evt);
            }
        });

        tnamapetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapetugas1ActionPerformed(evt);
            }
        });

        ttelp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttelp1ActionPerformed(evt);
            }
        });
        ttelp1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttelp1KeyPressed(evt);
            }
        });

        boxlevel1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pilih", "admin", "petugas" }));

        bsimpanpetu2.setText("Simpan");
        bsimpanpetu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanpetu2ActionPerformed(evt);
            }
        });

        bsimpanpetu3.setText("Tampil");
        bsimpanpetu3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanpetu3ActionPerformed(evt);
            }
        });

        beditpetugas1.setText("Edit");
        beditpetugas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditpetugas1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel57)
                            .addComponent(jLabel56)
                            .addComponent(jLabel55)
                            .addComponent(jLabel54)
                            .addComponent(jLabel58))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tidpetugas1)
                                .addComponent(tuserpetugas1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                                .addComponent(tpasspetu1)
                                .addComponent(tnamapetugas1))
                            .addComponent(ttelp1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(151, 151, 151)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bsimpanpetu2)
                            .addComponent(bsimpanpetu3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel59)
                        .addGap(97, 97, 97)))
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boxlevel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(beditpetugas1)))
                .addGap(155, 155, 155))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel54)
                                    .addComponent(tidpetugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel55)
                                    .addComponent(tuserpetugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel59)
                                    .addComponent(boxlevel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(tpasspetu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(bsimpanpetu2)
                            .addComponent(beditpetugas1))))
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel57)
                            .addComponent(tnamapetugas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(ttelp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(bsimpanpetu3)
                        .addGap(82, 82, 82))))
        );

        tabelpetugas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id Petugas", "Username", "Password", "Nama Petugas", "No Telepon", "level"
            }
        ));
        tabelpetugas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpetugas1MouseClicked(evt);
            }
        });
        jScrollPane10.setViewportView(tabelpetugas1);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel31Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        Ppetugas1.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 710));

        mainpanel1.add(Ppetugas1, "card4");

        Psppp1.setPreferredSize(new java.awt.Dimension(920, 710));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 490, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 60, Short.MAX_VALUE)
        );

        tabelspp1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID SPP", "Tahun", "Nominal"
            }
        ));
        tabelspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelspp1MouseClicked(evt);
            }
        });
        jScrollPane11.setViewportView(tabelspp1);

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );

        jLabel60.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel60.setText("ID SPP");

        tidspp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidspp1ActionPerformed(evt);
            }
        });

        jLabel61.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel61.setText("Tahun");

        ttahun1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttahun1ActionPerformed(evt);
            }
        });

        jLabel62.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel62.setText("Nominal");

        tnominal1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnominal1ActionPerformed(evt);
            }
        });

        bsimpanspp1.setText("Simpan");
        bsimpanspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bsimpanspp1MouseClicked(evt);
            }
        });

        beditspp1.setText("Edit");
        beditspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beditspp1MouseClicked(evt);
            }
        });

        bhapusspp1.setText("Hapus");
        bhapusspp1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bhapusspp1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(bsimpanspp1))
                .addGap(38, 38, 38)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(beditspp1)
                        .addGap(33, 33, 33)
                        .addComponent(bhapusspp1))
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(tidspp1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ttahun1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tnominal1, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel60)
                    .addComponent(tidspp1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ttahun1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tnominal1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpanspp1)
                    .addComponent(beditspp1)
                    .addComponent(bhapusspp1))
                .addContainerGap(120, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(278, 278, 278))
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel35Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(239, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout Psppp1Layout = new javax.swing.GroupLayout(Psppp1);
        Psppp1.setLayout(Psppp1Layout);
        Psppp1Layout.setHorizontalGroup(
            Psppp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Psppp1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Psppp1Layout.setVerticalGroup(
            Psppp1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainpanel1.add(Psppp1, "card5");

        javax.swing.GroupLayout Ppembayaran1Layout = new javax.swing.GroupLayout(Ppembayaran1);
        Ppembayaran1.setLayout(Ppembayaran1Layout);
        Ppembayaran1Layout.setHorizontalGroup(
            Ppembayaran1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 920, Short.MAX_VALUE)
        );
        Ppembayaran1Layout.setVerticalGroup(
            Ppembayaran1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 710, Short.MAX_VALUE)
        );

        mainpanel1.add(Ppembayaran1, "card6");

        Pkelas1.setBackground(new java.awt.Color(255, 255, 255));
        Pkelas1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel63.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel63.setText("Data Kelas");

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(382, 382, 382)
                .addComponent(jLabel63)
                .addContainerGap(405, Short.MAX_VALUE))
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel39Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel63)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        Pkelas1.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 6, 880, 60));

        jLabel64.setText("Id Kelas");

        jLabel65.setText("Nama Kelas");

        jLabel66.setText("Kompetensi Keahlian");

        tidkelas1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tidkelas1FocusLost(evt);
            }
        });
        tidkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkelas1ActionPerformed(evt);
            }
        });

        tnamakelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamakelas1ActionPerformed(evt);
            }
        });

        tkom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkom1ActionPerformed(evt);
            }
        });

        bsimpankelas1.setText("Simpan");
        bsimpankelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpankelas1ActionPerformed(evt);
            }
        });

        bhapuskelas1.setText("Hapus");
        bhapuskelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapuskelas1ActionPerformed(evt);
            }
        });

        beditkelas1.setText("Edit");
        beditkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditkelas1ActionPerformed(evt);
            }
        });

        btampilkelas1.setText("Tampil");
        btampilkelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btampilkelas1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tidkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tnamakelas1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tkom1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel40Layout.createSequentialGroup()
                        .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btampilkelas1)
                            .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(bsimpankelas1)
                                .addGap(18, 18, 18)
                                .addComponent(bhapuskelas1)))
                        .addGap(18, 18, 18)
                        .addComponent(beditkelas1)))
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel64)
                    .addComponent(tidkelas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel65)
                    .addComponent(tnamakelas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(tkom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(65, 65, 65)
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bsimpankelas1)
                    .addComponent(bhapuskelas1)
                    .addComponent(beditkelas1))
                .addGap(18, 18, 18)
                .addComponent(btampilkelas1)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        Pkelas1.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 310, 320));

        tabelkelas1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id Kelas", "Nama Kelas", "Kompetensi Keahlian"
            }
        ));
        tabelkelas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkelas1MouseClicked(evt);
            }
        });
        jScrollPane12.setViewportView(tabelkelas1);

        bcarikelas1.setText("Cari");
        bcarikelas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarikelas1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addComponent(bcarikelas1)
                .addGap(37, 37, 37)
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel41Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bcarikelas1)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54))
        );

        Pkelas1.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 130, 470, 230));

        mainpanel1.add(Pkelas1, "card3");

        jFrame1.getContentPane().add(mainpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 920, 710));

        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tnis4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis4.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis4ActionPerformed(evt);
            }
        });
        jPanel42.add(tnis4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel67.setText("NISN");
        jPanel42.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel68.setText("NIS");
        jPanel42.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis5.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis5ActionPerformed(evt);
            }
        });
        jPanel42.add(tnis5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis6.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis6ActionPerformed(evt);
            }
        });
        jPanel42.add(tnis6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel69.setText("NAMA");
        jPanel42.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });
        jPanel42.add(jComboBox3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel70.setText("ALAMAT");
        jPanel42.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextArea2.setRows(3);
        jScrollPane13.setViewportView(jTextArea2);

        jPanel42.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton4.setText("Simpan");
        kButton4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jPanel42.add(kButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton5.setText("Edit");
        kButton5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        kButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton5ActionPerformed(evt);
            }
        });
        jPanel42.add(kButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel71.setText("NO TELP");
        jPanel42.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton6.setText("Hapus");
        kButton6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        kButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton6ActionPerformed(evt);
            }
        });
        jPanel42.add(kButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel72.setText("ID SPP");
        jPanel42.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox4ActionPerformed(evt);
            }
        });
        jPanel42.add(jComboBox4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis7.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis7ActionPerformed(evt);
            }
        });
        jPanel42.add(tnis7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NISN", "NIS", "NAMA", "ID KELAS", "ALAMAT", "NO TELP", "ID SPP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane14.setViewportView(jTable2);

        jPanel42.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jFrame1.getContentPane().add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 58, -1, 600));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aplikasi pembayaran spp");
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(45, 205, 223));
        jPanel1.setLayout(null);

        asli.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        asli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-close-window-30 (1).png"))); // NOI18N
        asli.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        asli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                asliMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                asliMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                asliMouseExited(evt);
            }
        });
        jPanel1.add(asli);
        asli.setBounds(1310, 0, 60, 60);

        hover.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-close-window-30 (2).png"))); // NOI18N
        hover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hoverMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                hoverMouseEntered(evt);
            }
        });
        jPanel1.add(hover);
        hover.setBounds(1310, -2, 60, 60);

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/backhead.png"))); // NOI18N
        jPanel1.add(jLabel5);
        jLabel5.setBounds(0, 0, 1370, 60);

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1370, 60));

        panel1.setBackground(new java.awt.Color(239, 243, 245));
        panel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        siswa.setBackground(new java.awt.Color(153, 153, 153));
        siswa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        siswa.setForeground(new java.awt.Color(255, 255, 255));
        siswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        siswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-student-25.png"))); // NOI18N
        siswa.setText("MASTER SISWA");
        siswa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        siswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                siswaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                siswaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                siswaMouseExited(evt);
            }
        });
        panel1.add(siswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 140, 30));

        bpetugas.setBackground(new java.awt.Color(153, 153, 153));
        bpetugas.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bpetugas.setForeground(new java.awt.Color(255, 255, 255));
        bpetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bpetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-worker-20.png"))); // NOI18N
        bpetugas.setText("PETUGAS");
        bpetugas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bpetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bpetugasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bpetugasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bpetugasMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bpetugasMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bpetugasMouseReleased(evt);
            }
        });
        panel1.add(bpetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 110, 30));

        bspp.setBackground(new java.awt.Color(153, 153, 153));
        bspp.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bspp.setForeground(new java.awt.Color(255, 255, 255));
        bspp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bspp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-insert-money-25.png"))); // NOI18N
        bspp.setText("SPP");
        bspp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bsppMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bsppMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bsppMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bsppMouseReleased(evt);
            }
        });
        panel1.add(bspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 120, 40));

        bpembayaran.setBackground(new java.awt.Color(153, 153, 153));
        bpembayaran.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bpembayaran.setForeground(new java.awt.Color(255, 255, 255));
        bpembayaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bpembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-pay-25.png"))); // NOI18N
        bpembayaran.setText("PEMBAYARAN");
        bpembayaran.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bpembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bpembayaranMouseClicked(evt);
            }
        });
        panel1.add(bpembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 490, 140, 38));

        bkelas.setBackground(new java.awt.Color(153, 153, 153));
        bkelas.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bkelas.setForeground(new java.awt.Color(255, 255, 255));
        bkelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bkelas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-classroom-25.png"))); // NOI18N
        bkelas.setText("MASTER KELAS");
        bkelas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bkelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bkelasMouseClicked(evt);
            }
        });
        panel1.add(bkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(-10, 448, 150, 30));

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Selamat Datang");
        panel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 20, 110, 50));

        namauser.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        namauser.setForeground(new java.awt.Color(255, 255, 255));
        panel1.add(namauser, new org.netbeans.lib.awtextra.AbsoluteConstraints(146, 711, 93, 13));

        bkeluar.setBackground(new java.awt.Color(153, 153, 153));
        bkeluar.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        bkeluar.setForeground(new java.awt.Color(255, 255, 255));
        bkeluar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bkeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-classroom-25.png"))); // NOI18N
        bkeluar.setText("LOG OUT");
        bkeluar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bkeluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bkeluarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bkeluarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bkeluarMouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bkeluarMouseReleased(evt);
            }
        });
        panel1.add(bkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, -1, 30));

        jLabel23.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-home-page-25.png"))); // NOI18N
        jLabel23.setText("HOME");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel23MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel23MouseExited(evt);
            }
        });
        panel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 90, 30));

        jLabel75.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/sidebar.png"))); // NOI18N
        panel1.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 160, -1));

        getContentPane().add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 160, 710));

        mainpanel.setBackground(new java.awt.Color(255, 255, 255));
        mainpanel.setLayout(new java.awt.CardLayout());

        Phome.setBackground(new java.awt.Color(235, 235, 235));
        Phome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/icons8-back-arrow-35.png"))); // NOI18N
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        Phome.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 20, 40, 40));

        greating.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Phome.add(greating, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 478, 80));

        ltgl.setFont(new java.awt.Font("Ravie", 1, 14)); // NOI18N
        ltgl.setText("sd");
        Phome.add(ltgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 100, 218, -1));

        ljam.setFont(new java.awt.Font("Rockwell", 1, 14)); // NOI18N
        ljam.setText("sd");
        Phome.add(ljam, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 150, 218, -1));

        jLabel7.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("SISWA");
        Phome.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 520, 109, 31));

        tampilsiswa.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        tampilsiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tampilsiswa.setText("no");
        Phome.add(tampilsiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 340, 100, 100));

        distokelas.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        distokelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        distokelas.setText("no");
        Phome.add(distokelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 340, -1, 100));

        jLabel9.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("KELAS");
        Phome.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 109, 31));

        distopetugas.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        distopetugas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        distopetugas.setText("no");
        Phome.add(distopetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 340, 50, 100));

        jLabel74.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("no");
        Phome.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 340, -1, 100));

        jLabel11.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PETUGAS");
        Phome.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 520, 130, 31));

        jLabel12.setFont(new java.awt.Font("Montserrat", 1, 24)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("PAYMENT");
        Phome.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 520, 140, 31));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/halut.png"))); // NOI18N
        Phome.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 710));

        mainpanel.add(Phome, "card2");

        Psiswa.setBackground(new java.awt.Color(248, 248, 248));
        Psiswa.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lihatdata.setText("Lihat Data ");
        lihatdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatdataActionPerformed(evt);
            }
        });
        Psiswa.add(lihatdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, -1, -1));

        tampilidkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampilidkelasActionPerformed(evt);
            }
        });
        Psiswa.add(tampilidkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 154, -1));

        tampidspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tampidsppActionPerformed(evt);
            }
        });
        Psiswa.add(tampidspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, 154, -1));

        lihatdata2.setText("Lihat Data ");
        lihatdata2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lihatdata2ActionPerformed(evt);
            }
        });
        Psiswa.add(lihatdata2, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 550, -1, -1));

        tabelsiswamaster.setBorder(new javax.swing.border.MatteBorder(null));
        tabelsiswamaster.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsiswamaster.setGridColor(new java.awt.Color(178, 214, 250));
        tabelsiswamaster.setSelectionBackground(new java.awt.Color(204, 204, 204));
        tabelsiswamaster.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswamasterMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelsiswamaster);

        Psiswa.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 220, 660, 250));

        txtcarisis.setBackground(new java.awt.Color(255, 255, 255, 0));
        txtcarisis.setBorder(null);
        txtcarisis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcarisisActionPerformed(evt);
            }
        });
        txtcarisis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtcarisisKeyReleased(evt);
            }
        });
        Psiswa.add(txtcarisis, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 210, 40));

        comboidspp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));
        comboidspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboidsppActionPerformed(evt);
            }
        });
        Psiswa.add(comboidspp, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, 181, -1));

        jLabel20.setText("Tahun SPP");
        Psiswa.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 66, -1));

        jLabel19.setText("No Telpon");
        Psiswa.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, 66, -1));

        ttelesiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttelesiswaActionPerformed(evt);
            }
        });
        ttelesiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ttelesiswaKeyReleased(evt);
            }
        });
        Psiswa.add(ttelesiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 193, 30));

        jLabel18.setText("Alamat");
        Psiswa.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, 49, -1));

        jLabel17.setText("Kelas");
        Psiswa.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 49, -1));

        comboidkelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--Pilih--" }));
        comboidkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboidkelasActionPerformed(evt);
            }
        });
        Psiswa.add(comboidkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 290, 193, -1));

        tnamasiswas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamasiswasActionPerformed(evt);
            }
        });
        tnamasiswas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnamasiswasKeyReleased(evt);
            }
        });
        Psiswa.add(tnamasiswas, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 250, 193, 30));

        jLabel16.setText("Nama");
        Psiswa.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 250, 40, -1));

        jLabel15.setText("Nis");
        Psiswa.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 40, -1));

        tnissiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnissiswaActionPerformed(evt);
            }
        });
        tnissiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnissiswaKeyReleased(evt);
            }
        });
        Psiswa.add(tnissiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 193, 30));

        tnisnsiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnisnsiswaActionPerformed(evt);
            }
        });
        tnisnsiswa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnisnsiswaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tnisnsiswaKeyTyped(evt);
            }
        });
        Psiswa.add(tnisnsiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 193, 30));

        jLabel14.setText("Nisn");
        Psiswa.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 40, -1));

        bsimpansiswa.setBackground(new java.awt.Color(51, 255, 51));
        bsimpansiswa.setText("Simpan");
        bsimpansiswa.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(51, 255, 51), new java.awt.Color(204, 255, 204), new java.awt.Color(102, 255, 102)));
        bsimpansiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpansiswaActionPerformed(evt);
            }
        });
        Psiswa.add(bsimpansiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 540, 106, 32));

        btneditsis.setBackground(new java.awt.Color(255, 255, 102));
        btneditsis.setText("Edit");
        btneditsis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditsisActionPerformed(evt);
            }
        });
        Psiswa.add(btneditsis, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 540, 104, 32));

        btnhapussis.setBackground(new java.awt.Color(255, 51, 51));
        btnhapussis.setText("Hapus");
        btnhapussis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapussisActionPerformed(evt);
            }
        });
        Psiswa.add(btnhapussis, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 580, 86, 36));

        talamatsiswa.setColumns(20);
        talamatsiswa.setRows(2);
        talamatsiswa.setTabSize(5);
        jScrollPane4.setViewportView(talamatsiswa);

        Psiswa.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 330, 184, 70));
        Psiswa.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, 40));

        backssiwa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/baacksis.png"))); // NOI18N
        backssiwa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backssiwaMouseClicked(evt);
            }
        });
        Psiswa.add(backssiwa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 710));

        mainpanel.add(Psiswa, "card3");

        Ppetugas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel14.setBackground(new java.awt.Color(153, 153, 255));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jLabel27.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel27.setText("Data Petugas");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(307, 307, 307)
                .addComponent(jLabel27)
                .addContainerGap(311, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel27)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel28.setText("Id Petugas");

        jLabel29.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel29.setText("Username");

        jLabel30.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel30.setText("Password");

        jLabel31.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel31.setText("Nama Petugas");

        jLabel32.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel32.setText("No telepon");

        jLabel33.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel33.setText("Level");

        tidpetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tidpetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidpetugasActionPerformed(evt);
            }
        });

        tuserpetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tuserpetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tuserpetugasActionPerformed(evt);
            }
        });
        tuserpetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tuserpetugasKeyPressed(evt);
            }
        });

        tpasspetu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tpasspetu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tpasspetuActionPerformed(evt);
            }
        });

        tnamapetugas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tnamapetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamapetugasActionPerformed(evt);
            }
        });

        ttelp.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        ttelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttelpActionPerformed(evt);
            }
        });
        ttelp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttelpKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ttelpKeyReleased(evt);
            }
        });

        boxlevel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        boxlevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "pilih", "admin", "petugas" }));

        bsimpanpetu.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bsimpanpetu.setText("Simpan");
        bsimpanpetu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpanpetuActionPerformed(evt);
            }
        });

        btampil.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btampil.setText("Tampil");
        btampil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btampilActionPerformed(evt);
            }
        });

        beditpetugas.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        beditpetugas.setText("Edit");
        beditpetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditpetugasActionPerformed(evt);
            }
        });

        bhapuspetugas.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        bhapuspetugas.setText("Hapus");
        bhapuspetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapuspetugasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tidpetugas)
                    .addComponent(tuserpetugas, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addComponent(tpasspetu)
                    .addComponent(tnamapetugas))
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jLabel32)
                        .addGap(18, 18, 18)
                        .addComponent(ttelp, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(bsimpanpetu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(beditpetugas))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(54, 54, 54)
                                .addComponent(boxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(bhapuspetugas)
                            .addComponent(btampil))
                        .addGap(101, 101, 101))))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(tidpetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel28)))
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(boxlevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel33)
                                    .addComponent(btampil)))
                            .addGroup(jPanel16Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel29)
                                    .addComponent(tuserpetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tpasspetu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)))
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel32)
                            .addComponent(ttelp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tnamapetugas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(bsimpanpetu)
                    .addComponent(beditpetugas)
                    .addComponent(bhapuspetugas))
                .addContainerGap(49, Short.MAX_VALUE))
        );

        tabelpetugas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id Petugas", "Username", "Password", "Nama Petugas", "No Telepon", "level"
            }
        ));
        tabelpetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelpetugasMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(tabelpetugas);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(34, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        caripetu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caripetuKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(81, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel14Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(caripetu, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(277, 277, 277))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(caripetu, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
        );

        Ppetugas.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 920, 710));

        mainpanel.add(Ppetugas, "card4");

        Psppp.setPreferredSize(new java.awt.Dimension(920, 710));
        Psppp.setLayout(null);

        tabelspp.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        tabelspp.setForeground(new java.awt.Color(126, 107, 46));
        tabelspp.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID SPP", "Tahun", "Nominal"
            }
        ));
        tabelspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsppMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(tabelspp);

        Psppp.add(jScrollPane7);
        jScrollPane7.setBounds(226, 150, 730, 200);

        bhapusspp.setText("Hapus");
        bhapusspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bhapussppMouseClicked(evt);
            }
        });
        bhapusspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapussppActionPerformed(evt);
            }
        });
        Psppp.add(bhapusspp);
        bhapusspp.setBounds(710, 620, 90, 40);

        beditspp.setText("Edit");
        beditspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                beditsppMouseClicked(evt);
            }
        });
        beditspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditsppActionPerformed(evt);
            }
        });
        Psppp.add(beditspp);
        beditspp.setBounds(570, 620, 90, 40);

        bsimpanspp.setText("Simpan");
        bsimpanspp.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bsimpanspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bsimpansppMouseClicked(evt);
            }
        });
        bsimpanspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpansppActionPerformed(evt);
            }
        });
        Psppp.add(bsimpanspp);
        bsimpanspp.setBounds(420, 620, 90, 40);

        tnominal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnominalActionPerformed(evt);
            }
        });
        tnominal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnominalKeyReleased(evt);
            }
        });
        Psppp.add(tnominal);
        tnominal.setBounds(610, 560, 170, 40);

        ttahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ttahunActionPerformed(evt);
            }
        });
        ttahun.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ttahunKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ttahunKeyReleased(evt);
            }
        });
        Psppp.add(ttahun);
        ttahun.setBounds(610, 510, 170, 40);

        tidspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidsppActionPerformed(evt);
            }
        });
        Psppp.add(tidspp);
        tidspp.setBounds(610, 460, 170, 40);

        totalnom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalnomActionPerformed(evt);
            }
        });
        totalnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                totalnomKeyReleased(evt);
            }
        });
        Psppp.add(totalnom);
        totalnom.setBounds(850, 520, 170, 40);

        jLabel34.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel34.setText("ID SPP");
        Psppp.add(jLabel34);
        jLabel34.setBounds(430, 470, 56, 19);

        jLabel35.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel35.setText("Tahun");
        Psppp.add(jLabel35);
        jLabel35.setBounds(430, 520, 56, 24);

        jLabel36.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel36.setText("Nominal");
        Psppp.add(jLabel36);
        jLabel36.setBounds(430, 570, 56, 20);

        cgannep.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--pilih--", "ganjil", "genap" }));
        Psppp.add(cgannep);
        cgannep.setBounds(850, 470, 110, 22);

        caridataspp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caridatasppActionPerformed(evt);
            }
        });
        caridataspp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                caridatasppKeyReleased(evt);
            }
        });
        Psppp.add(caridataspp);
        caridataspp.setBounds(530, 120, 140, 22);

        backspp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/backspp.png"))); // NOI18N
        backspp.setText("jLabel73");
        backspp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backsppMouseClicked(evt);
            }
        });
        Psppp.add(backspp);
        backspp.setBounds(0, 0, 1380, 710);

        mainpanel.add(Psppp, "card5");

        Ppembayaran.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cariKeyReleased(evt);
            }
        });
        Ppembayaran.add(cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 52, 350, 30));

        tabelsiswapembayaran.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelsiswapembayaran.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelsiswapembayaranMouseClicked(evt);
            }
        });
        jScrollPane15.setViewportView(tabelsiswapembayaran);

        Ppembayaran.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 100, -1, 140));

        jLabel76.setText("NO. Transaksi");
        Ppembayaran.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 90, 20));

        jLabel77.setText("NISN");
        Ppembayaran.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 90, 20));

        namasiswa.setText("Nama Siswa");
        Ppembayaran.add(namasiswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 90, 20));
        Ppembayaran.add(tNo_Transaki, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, 100, -1));
        Ppembayaran.add(tNisn, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, 100, -1));
        Ppembayaran.add(tNama_siswa, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, 100, -1));
        Ppembayaran.add(tKelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 100, -1));
        Ppembayaran.add(tId_Angkatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 280, 100, -1));
        Ppembayaran.add(tId_petugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 100, -1));
        Ppembayaran.add(tTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 130, -1));
        Ppembayaran.add(tAngkatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 280, 100, 20));
        Ppembayaran.add(tPetugas, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 100, 20));
        Ppembayaran.add(tnama_siswastatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 550, 110, -1));

        tJumlahbayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tJumlahbayarActionPerformed(evt);
            }
        });
        Ppembayaran.add(tJumlahbayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 580, 100, -1));

        namasiswa1.setText("Kelas");
        Ppembayaran.add(namasiswa1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 90, 20));

        namasiswa2.setText("Angkatan");
        Ppembayaran.add(namasiswa2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 90, 20));

        namasiswa3.setText("Petugas");
        Ppembayaran.add(namasiswa3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 90, 20));

        namasiswa4.setText("Tanggal");
        Ppembayaran.add(namasiswa4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 90, 20));

        namasiswa5.setText("Jumlah Bayar");
        Ppembayaran.add(namasiswa5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 580, 90, 20));

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane16.setViewportView(jTable3);

        Ppembayaran.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, -1, 240));

        backpembayaran.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/backbuatformpembayaran.png"))); // NOI18N
        Ppembayaran.add(backpembayaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, -1));

        mainpanel.add(Ppembayaran, "card6");

        Pkelas.setBackground(new java.awt.Color(255, 255, 255));
        Pkelas.setPreferredSize(new java.awt.Dimension(1210, 710));
        Pkelas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane5.setBackground(new java.awt.Color(255, 255, 255, 0));

        tabelkelas.setFont(new java.awt.Font("Segoe UI Black", 0, 13)); // NOI18N
        tabelkelas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID KELAS", "NAMA KELAS", "Kompetensi Keahlian"
            }
        ));
        tabelkelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelkelasMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tabelkelas);

        Pkelas.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 790, 170));

        btampilkelas.setText("Tampil");
        btampilkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btampilkelasActionPerformed(evt);
            }
        });
        Pkelas.add(btampilkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 620, -1, -1));

        beditkelas.setText("Edit");
        beditkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beditkelasActionPerformed(evt);
            }
        });
        Pkelas.add(beditkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 610, 90, 30));

        bhapuskelas.setText("Hapus");
        bhapuskelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapuskelasActionPerformed(evt);
            }
        });
        Pkelas.add(bhapuskelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 610, 80, 30));

        bsimpankelas.setText("Simpan");
        bsimpankelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsimpankelasActionPerformed(evt);
            }
        });
        Pkelas.add(bsimpankelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 610, 80, 30));

        tidkelas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tidkelasFocusLost(evt);
            }
        });
        tidkelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tidkelasActionPerformed(evt);
            }
        });
        Pkelas.add(tidkelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 470, 132, -1));

        tnamakelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnamakelasActionPerformed(evt);
            }
        });
        tnamakelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tnamakelasKeyReleased(evt);
            }
        });
        Pkelas.add(tnamakelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 510, 132, -1));

        tkom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tkomActionPerformed(evt);
            }
        });
        Pkelas.add(tkom, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 550, 132, -1));

        jLabel24.setText("Id Kelas");
        Pkelas.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 470, 111, -1));

        jLabel25.setText("Nama Kelas");
        Pkelas.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 510, 111, -1));

        jLabel26.setText("Kompetensi Keahlian");
        Pkelas.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 550, -1, -1));

        textcarikelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textcarikelasActionPerformed(evt);
            }
        });
        textcarikelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                textcarikelasKeyReleased(evt);
            }
        });
        Pkelas.add(textcarikelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, 200, 30));

        bcarikelas.setText("Cari");
        bcarikelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bcarikelasActionPerformed(evt);
            }
        });
        Pkelas.add(bcarikelas, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 110, -1, -1));

        jLabel78.setBackground(new java.awt.Color(255, 255, 255, 0));
        jLabel78.setIcon(new javax.swing.ImageIcon(getClass().getResource("/foto/backkelas.png"))); // NOI18N
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel78MouseClicked(evt);
            }
        });
        Pkelas.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1210, 710));

        mainpanel.add(Pkelas, "card3");

        getContentPane().add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 60, 1210, 710));

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tnis.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnisActionPerformed(evt);
            }
        });
        jPanel3.add(tnis, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel1.setText("NISN");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel2.setText("NIS");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis1ActionPerformed(evt);
            }
        });
        jPanel3.add(tnis1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis2.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis2ActionPerformed(evt);
            }
        });
        jPanel3.add(tnis2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel3.setText("NAMA");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel4.setText("ALAMAT");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jTextArea1.setRows(3);
        jScrollPane2.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton1.setText("Simpan");
        kButton1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jPanel3.add(kButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton2.setText("Edit");
        kButton2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(kButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel8.setText("NO TELP");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        kButton3.setText("Hapus");
        kButton3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        kButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(kButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jLabel10.setText("ID SPP");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });
        jPanel3.add(jComboBox2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        tnis3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tnis3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 2, 0, new java.awt.Color(0, 0, 0)));
        tnis3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tnis3ActionPerformed(evt);
            }
        });
        jPanel3.add(tnis3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "NISN", "NIS", "NAMA", "ID KELAS", "ALAMAT", "NO TELP", "ID SPP"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 0, 0));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 58, -1, 600));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tnisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnisActionPerformed
        String text = tnis.getText();
        if (text.length() > 10) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 10.");
        }
        try {
            int value = Integer.parseInt(tnis.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }//GEN-LAST:event_tnisActionPerformed

    private void tnis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis1ActionPerformed

    private void tnis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton2ActionPerformed

    private void tnis3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis3ActionPerformed

    private void kButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void bsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsppMouseClicked
//        new spp().setVisible(true);
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Psppp);
        mainpanel.repaint();
        mainpanel.revalidate();

    }//GEN-LAST:event_bsppMouseClicked

    private void bpetugasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugasMouseEntered
        bpetugas.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_bpetugasMouseEntered

    private void bpetugasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugasMouseExited
        bpetugas.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_bpetugasMouseExited

    private void bpetugasMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugasMousePressed
        bpetugas.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_bpetugasMousePressed

    private void bpetugasMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugasMouseReleased
        bpetugas.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_bpetugasMouseReleased

    private void bpetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugasMouseClicked
        //mengarahkan ke panel petugas
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Ppetugas);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_bpetugasMouseClicked

    private void siswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siswaMouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Psiswa);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_siswaMouseClicked

    private void bkelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkelasMouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Pkelas);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_bkelasMouseClicked

    private void bkeluarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkeluarMouseClicked
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin Keluar ?", "LogOut", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            new tampilan_awal().setVisible(true);
            this.dispose();
        }

    }//GEN-LAST:event_bkeluarMouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel13MouseClicked

    private void tnisnsiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnisnsiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnisnsiswaActionPerformed

    private void tnissiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnissiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnissiswaActionPerformed

    private void tnamasiswasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamasiswasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamasiswasActionPerformed

    private void ttelesiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttelesiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttelesiswaActionPerformed

    private void bsimpansiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpansiswaActionPerformed
        String idkelas = comboidkelas.getSelectedItem().toString();
        String idspp = comboidspp.getSelectedItem().toString();
//        String idspp = comboidkelas.getSelectedItem(rs.getString("id_spp")).toString();
        try {
            String sql = "INSERT INTO siswa VALUES ('" + tnisnsiswa.getText()
                    + "','" + tnissiswa.getText()
                    + "','" + tnamasiswas.getText()
                    + "','" + kelas
                    + "','" + talamatsiswa.getText()
                    + "','" + ttelesiswa.getText()
                    + "','" + spp
                    + "')";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Sudah Tersimpan");
            tnisnsiswa.setText("");
            tnissiswa.setText("");
            tnamasiswas.setText("");
            tampilidkelas.setText("");
            talamatsiswa.setText("");
            ttelesiswa.setText("");
            tampidspp.setText("");

            DefaultTableModel model = (DefaultTableModel) tabelkelas.getModel();
            model.setRowCount(0);
//            tampil_comboidkelas();
            refreshsiswa();

        } catch (Exception e) {
            System.out.println("gagal memasukkan data" + e.getMessage());
            JOptionPane.showMessageDialog(rootPane, "Data masih kosong");
        }
    }//GEN-LAST:event_bsimpansiswaActionPerformed

    private void btneditsisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditsisActionPerformed

    }//GEN-LAST:event_btneditsisActionPerformed

    private void btnhapussisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapussisActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin menghapus data ini ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "delete from siswa where nisn ='" + tnisnsiswa.getText() + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "berhasil");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "gagal");
            }
            resetFormsiswa();
        }

    }//GEN-LAST:event_btnhapussisActionPerformed

    private void asliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_asliMouseClicked
        System.exit(0);
        asli.setVisible(false);
        asli.setEnabled(false);
        hover.setEnabled(false);
        hover.setEnabled(false);
    }//GEN-LAST:event_asliMouseClicked

    private void tidkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkelasActionPerformed
//        tidkelas.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
//                    tnamakelas.requestFocus();
//                }
//            }
//        });
        tnamakelas.requestFocus();
    }//GEN-LAST:event_tidkelasActionPerformed

    private void tnamakelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamakelasActionPerformed
//        tnamakelas.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    tkom.requestFocus();
//                }
//            }
//        });

        tkom.requestFocus();
    }//GEN-LAST:event_tnamakelasActionPerformed

    private void tkomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkomActionPerformed
        tkom.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    bsimpankelas.doClick();
                }
            }
        });
    }//GEN-LAST:event_tkomActionPerformed

    private void bsimpankelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpankelasActionPerformed
        try {
            String sql = "INSERT INTO kelas VALUES ('" + tidkelas.getText()
                    + "','" + tnamakelas.getText()
                    + "','" + tkom.getText() + "')";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Sudah Tersimpan");
            tidkelas.setText("");
            tnamakelas.setText("");
            tkom.setText("");

            DefaultTableModel model = (DefaultTableModel) tabelkelas.getModel();
            model.setRowCount(0);
            refreshkelas();
            id_otomatis();

        } catch (Exception e) {
            System.out.println("gagal memasukkan data" + e.getMessage());
        }

    }//GEN-LAST:event_bsimpankelasActionPerformed

    private void bhapuskelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapuskelasActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin menghapus data ini ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "DELETE FROM kelas WHERE Id_kelas ='" + tidkelas.getText() + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data");
//            refresh();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus" + e.getMessage());
            }
        }
        resetFormkelas();
    }//GEN-LAST:event_bhapuskelasActionPerformed

    private void beditkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditkelasActionPerformed
        try {
            String sql = "UPDATE kelas SET Id_kelas ='" + tidkelas.getText()
                    + "',nama_kelas ='" + tnamakelas.getText()
                    + "',kompetensi_keahlian ='" + tkom.getText() + "' WHERE Id_kelas ='" + tidkelas.getText() + "'";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            DefaultTableModel model = (DefaultTableModel) tabelkelas.getModel();
            model.setRowCount(0);
            refreshkelas();
            JOptionPane.showMessageDialog(null, "Data Sudah di Update");
            refreshkelas();
            resetForm();
        } catch (Exception e) {
            System.out.println("gagal mengupdate data " + e.getMessage());
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }
    }//GEN-LAST:event_beditkelasActionPerformed

    private void bcarikelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarikelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bcarikelasActionPerformed

    private void btampilkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btampilkelasActionPerformed
        try {
            String sql = "SELECT * FROM kelas WHERE Id_kelas ='" + tidkelas.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablekelas();
//        hapus();
    }//GEN-LAST:event_btampilkelasActionPerformed

    private void tabelkelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkelasMouseClicked
        int baris = tabelkelas.rowAtPoint(evt.getPoint());
        String nim = tabelkelas.getValueAt(baris, 0).toString();
        tidkelas.setText(nim);
        String nama = tabelkelas.getValueAt(baris, 1).toString();
        tnamakelas.setText(nama);
        String alamat = tabelkelas.getValueAt(baris, 2).toString();
        tkom.setText(alamat);
        bsimpankelas.setEnabled(false);
    }//GEN-LAST:event_tabelkelasMouseClicked

    private void tidpetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpetugasActionPerformed

    private void tuserpetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tuserpetugasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tuserpetugasActionPerformed

    private void tnamapetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapetugasActionPerformed
        String text = tnamapetugas.getText();
        if (text.length() > 13) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 12.");
        }
        try {
            int value = Integer.parseInt(tnis.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }//GEN-LAST:event_tnamapetugasActionPerformed

    private void ttelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttelpActionPerformed
        String text = ttelp.getText();
        if (text.length() > 13) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 13.");
        }
        try {
            int value = Integer.parseInt(ttelp.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number.");
        }
    }//GEN-LAST:event_ttelpActionPerformed
//petugas
    private void bsimpanpetuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanpetuActionPerformed
        String pilihanlevel = (String) boxlevel.getSelectedItem();
        try {
            String sql = "INSERT INTO petugas VALUES ('" + tidpetugas.getText()
                    + "','" + tuserpetugas.getText()
                    + "','" + tpasspetu.getText()
                    + "','" + tnamapetugas.getText()
                    + "','" + ttelp.getText()
                    + "','" + pilihanlevel
                    + "')";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Sudah Tersimpan");
            tidpetugas.setText("");
            tuserpetugas.setText("");
            tpasspetu.setText("");
            tnamapetugas.setText("");
            ttelp.setText("");
//            boxlevel.set

            DefaultTableModel model = (DefaultTableModel) tabelpetugas.getModel();
            model.setRowCount(0);
            refreshpetugas();

        } catch (Exception e) {
            System.out.println("gagal memasukkan data" + e.getMessage());
        }
    }//GEN-LAST:event_bsimpanpetuActionPerformed

    private void btampilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btampilActionPerformed
        try {
            String sql = "SELECT * FROM petugas WHERE Id_petugas ='" + tidpetugas.getText() + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        tablepetugas();
//        hapus();
    }//GEN-LAST:event_btampilActionPerformed

    private void tpasspetuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tpasspetuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpasspetuActionPerformed

    private void ttelpKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttelpKeyPressed

    }//GEN-LAST:event_ttelpKeyPressed

    private void tidsppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidsppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidsppActionPerformed

    private void ttahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttahunActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttahunActionPerformed

    private void tnominalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnominalActionPerformed
        
    }//GEN-LAST:event_tnominalActionPerformed

    private void tabelsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsppMouseClicked
        int baris = tabelspp.rowAtPoint(evt.getPoint());
        String nim = tabelspp.getValueAt(baris, 0).toString();
        tidspp.setText(nim);
        String nama = tabelspp.getValueAt(baris, 1).toString();
        ttahun.setText(nama);
        String alamat = tabelspp.getValueAt(baris, 2).toString();
        tnominal.setText(alamat);
        String semester = tabelspp.getValueAt(baris, 3).toString();
        cgannep.setSelectedItem(semester);
        String total = tabelspp.getValueAt(baris, 4).toString();
        totalnom.setText(total);

        bsimpanspp.setVisible(false);

    }//GEN-LAST:event_tabelsppMouseClicked

    private void bhapussppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bhapussppMouseClicked
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin menghapus data ini ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "DELETE FROM spp WHERE Id_spp ='" + tidspp.getText() + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data");
//            refresh();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus" + e.getMessage());
            }
        }
        refreshspp();
    }//GEN-LAST:event_bhapussppMouseClicked

    private void beditsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beditsppMouseClicked
        try {
            String sql = "UPDATE spp SET Id_spp ='" + tidspp.getText() + "',tahun ='" + ttahun.getText()
                    + "',nominal ='" + tnominal.getText() + "' WHERE Id_kelas ='" + tidkelas.getText() + "'";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            DefaultTableModel model = (DefaultTableModel) tabelspp.getModel();
            model.setRowCount(0);
            refreshkelas();
            JOptionPane.showMessageDialog(null, "Data Sudah diUpdate");
            refreshspp();
        } catch (Exception e) {
            System.out.println("gagal mengupdate data" + e.getMessage());
        }
    }//GEN-LAST:event_beditsppMouseClicked

    private void bsimpansppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsimpansppMouseClicked
        try {
            String sql = "INSERT INTO spp VALUES ('" + tidspp.getText()
                    + "','" + ttahun.getText()
                    + "','" + tnominal.getText() 
                    + "','" + cgannep.getSelectedItem()
                    + "','" + totalnom.getText()
                    + "')";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            JOptionPane.showMessageDialog(null, "Data Sudah Tersimpan");
            tidspp.setText("");
            ttahun.setText("");
            tnominal.setText("");
            cgannep.setSelectedItem("--pilih--");
            totalnom.setText("");

            DefaultTableModel model = (DefaultTableModel) tabelspp.getModel();
            model.setRowCount(0);
            refreshspp();
            id_otomatisspp();

        } catch (Exception e) {
            System.out.println("gagal memasukkan data" + e.getMessage());
        }
    }//GEN-LAST:event_bsimpansppMouseClicked

    private void tabelpetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpetugasMouseClicked
        int baris = tabelpetugas.rowAtPoint(evt.getPoint());
        String id = tabelpetugas.getValueAt(baris, 0).toString();
        tidpetugas.setText(id);
        String user = tabelpetugas.getValueAt(baris, 1).toString();
        tuserpetugas.setText(user);
        String pass = tabelpetugas.getValueAt(baris, 2).toString();
        tpasspetu.setText(pass);
        String nama = tabelpetugas.getValueAt(baris, 3).toString();
        tnamapetugas.setText(nama);
        String notelp = tabelpetugas.getValueAt(baris, 4).toString();
        ttelp.setText(notelp);
        String level = tabelpetugas.getValueAt(baris, 5).toString();
        boxlevel.setSelectedItem(level);

        bsimpanpetu.setEnabled(false);
    }//GEN-LAST:event_tabelpetugasMouseClicked

    private void tampilidkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilidkelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tampilidkelasActionPerformed

    private void lihatdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatdataActionPerformed
        Formkelas fkelas = new Formkelas();
        fkelas.setVisible(true);
        fkelas.tabelkelas2.setEnabled(true);
    }//GEN-LAST:event_lihatdataActionPerformed

    private void tampidsppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampidsppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tampidsppActionPerformed

    private void lihatdata2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatdata2ActionPerformed
        spp fspp = new spp();
        fspp.setVisible(true);
        fspp.tabelsppsen.setEnabled(true);
    }//GEN-LAST:event_lihatdata2ActionPerformed

    private void tidkelasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tidkelasFocusLost
        String input = tidkelas.getText();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            JOptionPane.showMessageDialog(this, "Input harus angka!", "Kesalahan", JOptionPane.ERROR_MESSAGE);
            tidkelas.requestFocus();
        }
    }//GEN-LAST:event_tidkelasFocusLost

    private void beditpetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditpetugasActionPerformed
        String pilihanlevel = (String) boxlevel.getSelectedItem();
        try {
            String sql = "UPDATE petugas SET Id_petugas ='" + tidpetugas.getText()
                    + "',username ='" + tuserpetugas.getText()
                    + "',password ='" + tpasspetu.getText()
                    + "',nama_petugas ='" + tnamapetugas.getText()
                    + "',no_tlppet ='" + ttelp.getText()
                    + "',level ='" + pilihanlevel
                    + "' WHERE Id_petugas ='" + tidpetugas.getText() + "'";
//            Connection con = (Connection) koneksi.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.execute();
            DefaultTableModel model = (DefaultTableModel) tabelpetugas.getModel();
            model.setRowCount(0);
            refreshpetugas();
            JOptionPane.showMessageDialog(null, "Data Sudah diUpdate");
            refreshpetugas();
            resetFormpetu();

        } catch (Exception e) {
            System.out.println("gagal mengupdate data" + e.getMessage());
        }
    }//GEN-LAST:event_beditpetugasActionPerformed

    private void jLabel38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel38MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel38MouseClicked

    private void siswa1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siswa1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_siswa1MouseClicked

    private void bpetugas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bpetugas1MouseClicked

    private void bpetugas1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugas1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_bpetugas1MouseEntered

    private void bpetugas1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugas1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_bpetugas1MouseExited

    private void bpetugas1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugas1MousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_bpetugas1MousePressed

    private void bpetugas1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpetugas1MouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_bpetugas1MouseReleased

    private void bspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bspp1MouseClicked

    private void bkelas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkelas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bkelas1MouseClicked

    private void bkeluar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkeluar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bkeluar1MouseClicked

    private void jLabel44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel44MouseClicked

    private void tnisnsiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnisnsiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnisnsiswa1ActionPerformed

    private void tnissiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnissiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnissiswa1ActionPerformed

    private void tnamasiswas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamasiswas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamasiswas1ActionPerformed

    private void ttelesiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttelesiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttelesiswa1ActionPerformed

    private void bsimpansiswa1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpansiswa1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpansiswa1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void lihatdata1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatdata1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lihatdata1ActionPerformed

    private void tampilidkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampilidkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tampilidkelas1ActionPerformed

    private void tampidspp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tampidspp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tampidspp1ActionPerformed

    private void lihatdata3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lihatdata3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lihatdata3ActionPerformed

    private void tidpetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidpetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidpetugas1ActionPerformed

    private void tuserpetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tuserpetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tuserpetugas1ActionPerformed

    private void tpasspetu1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tpasspetu1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tpasspetu1ActionPerformed

    private void tnamapetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamapetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamapetugas1ActionPerformed

    private void ttelp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttelp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttelp1ActionPerformed

    private void ttelp1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttelp1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttelp1KeyPressed

    private void bsimpanpetu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanpetu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpanpetu2ActionPerformed

    private void bsimpanpetu3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpanpetu3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpanpetu3ActionPerformed

    private void beditpetugas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditpetugas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_beditpetugas1ActionPerformed

    private void tabelpetugas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelpetugas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelpetugas1MouseClicked

    private void tabelspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelspp1MouseClicked

    private void tidspp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidspp1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidspp1ActionPerformed

    private void ttahun1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ttahun1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ttahun1ActionPerformed

    private void tnominal1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnominal1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnominal1ActionPerformed

    private void bsimpanspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsimpanspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpanspp1MouseClicked

    private void beditspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beditspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_beditspp1MouseClicked

    private void bhapusspp1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bhapusspp1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_bhapusspp1MouseClicked

    private void tidkelas1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tidkelas1FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkelas1FocusLost

    private void tidkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tidkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tidkelas1ActionPerformed

    private void tnamakelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnamakelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnamakelas1ActionPerformed

    private void tkom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tkom1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tkom1ActionPerformed

    private void bsimpankelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpankelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpankelas1ActionPerformed

    private void bhapuskelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapuskelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bhapuskelas1ActionPerformed

    private void beditkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_beditkelas1ActionPerformed

    private void btampilkelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btampilkelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btampilkelas1ActionPerformed

    private void tabelkelas1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelkelas1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelkelas1MouseClicked

    private void bcarikelas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bcarikelas1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bcarikelas1ActionPerformed

    private void tnis4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis4ActionPerformed

    private void tnis5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis5ActionPerformed

    private void tnis6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis6ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void kButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton5ActionPerformed

    private void kButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton6ActionPerformed

    private void jComboBox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox4ActionPerformed

    private void tnis7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tnis7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tnis7ActionPerformed

    private void bpembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpembayaranMouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Ppembayaran);
        mainpanel.repaint();
        mainpanel.revalidate();
    }//GEN-LAST:event_bpembayaranMouseClicked

    private void comboidkelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboidkelasActionPerformed
        try {
            String comboboxidkelas = comboidkelas.getSelectedItem().toString();
            String sql = "select * from kelas where nama_kelas='" + comboboxidkelas + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                kelas = rs.getString("Id_kelas");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_comboidkelasActionPerformed

    private void comboidsppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboidsppActionPerformed
        try {
            String comboboxidspp = comboidspp.getSelectedItem().toString();
            String sql = "select * from spp where tahun='" + comboboxidspp + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                spp = rs.getString("Id_spp");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }//GEN-LAST:event_comboidsppActionPerformed

    private void cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cariKeyReleased
        tabelSiswapem();
    }//GEN-LAST:event_cariKeyReleased

    private void tabelsiswapembayaranMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswapembayaranMouseClicked
        ID_AUTO();
        //setting date
        int tahun = kalender.get(Calendar.YEAR);
        int bulan = kalender.get(Calendar.MONTH) + 1;
        int hari = kalender.get(Calendar.DAY_OF_MONTH);
        DefaultTableModel model = new DefaultTableModel();
        String tanggal = tahun + "/" + bulan + "/" + hari;
        tTanggal.setText(tanggal);

        int i = tabelsiswapembayaran.rowAtPoint(evt.getPoint());
        tNisn.setText(tabelsiswapembayaran.getValueAt(i, 0).toString());
        tNama_siswa.setText(tabelsiswapembayaran.getValueAt(i, 2).toString());
        tnama_siswastatus.setText(tabelsiswapembayaran.getValueAt(i, 2).toString());
        tKelas.setText(tabelsiswapembayaran.getValueAt(i, 3).toString());
        tAngkatan.setText(tabelsiswapembayaran.getValueAt(i, 6).toString());
        tId_petugas.setText(id);
        tPetugas.setText(nama);

        try {
            String sql = "select siswa.*, spp.* from siswa INNER JOIN spp Using(id_spp) where nisn like '%" + tNisn.getText() + "%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                tId_Angkatan.setText(rs.getString("id_spp"));
                tJumlahbayar.setText(rs.getString("nominal"));
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(rootPane, e.getMessage());
        }

//        btnSimpan.setEnabled(true);
//        btnHapus.setEnabled(false);
    }//GEN-LAST:event_tabelsiswapembayaranMouseClicked

    private void tJumlahbayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tJumlahbayarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tJumlahbayarActionPerformed

    private void txtcarisisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcarisisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcarisisActionPerformed

    private void txtcarisisKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcarisisKeyReleased
        carisiswa();
    }//GEN-LAST:event_txtcarisisKeyReleased

    private void tabelsiswamasterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelsiswamasterMouseClicked
        int baris = tabelsiswamaster.rowAtPoint(evt.getPoint());
        String nisnsis = tabelsiswamaster.getValueAt(baris, 0).toString();
        tnisnsiswa.setText(nisnsis);
        String nissis = tabelsiswamaster.getValueAt(baris, 1).toString();
        tnissiswa.setText(nisnsis);
        String nama = tabelsiswamaster.getValueAt(baris, 2).toString();
        tnamasiswas.setText(nama);
        String kelas = tabelsiswamaster.getValueAt(baris, 3).toString();
        comboidkelas.setSelectedItem(kelas);
        String alamat = tabelsiswamaster.getValueAt(baris, 4).toString();
        talamatsiswa.setText(alamat);
        String notelp = tabelsiswamaster.getValueAt(baris, 5).toString();
        ttelesiswa.setText(notelp);
        String spp = tabelsiswamaster.getValueAt(baris, 3).toString();
        comboidspp.setSelectedItem(spp);
        bsimpansiswa.setEnabled(false);
    }//GEN-LAST:event_tabelsiswamasterMouseClicked

    private void beditsppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beditsppActionPerformed
        try {
            String tahun = ttahun.getText();
            String nominal = tnominal.getText();

            String sql = "update spp set tahun_ajaran='" + tahun + "', nominal_perbulan='" + nominal +"', semester ='" + cgannep.getSelectedItem()+"', total_nominal_semester='" + totalnom.getText()  +"' where id_spp='" + tidspp.getText() + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil Di Edit");
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Data Gagal Di Edit");
        }
        resetForm();
        id_otomatisspp();
    }//GEN-LAST:event_beditsppActionPerformed

    private void bsimpansppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsimpansppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bsimpansppActionPerformed

    private void bhapussppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapussppActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin menghapus data ini ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "delete from spp where id_spp ='" + tabelspp.getValueAt(tabelspp.getSelectedRow(), 0) + "'";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.execute();
                JOptionPane.showMessageDialog(null, "berhasil");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "gagal");
            }
            resetForm();
            id_otomatisspp();
        }

    }//GEN-LAST:event_bhapussppActionPerformed

    private void bhapuspetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapuspetugasActionPerformed
        int p = JOptionPane.showConfirmDialog(null, "Apaakah Anda ingin menghapus data ini ?", "Hapus", JOptionPane.YES_NO_OPTION);
        if (p == 0) {
            try {
                String sql = "DELETE FROM petugas WHERE Id_petugas ='" + tidpetugas.getText() + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Berhasil menghapus data");
//            refresh();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal menghapus" + e.getMessage());
            }
            resetFormpetu();
        }
    }//GEN-LAST:event_bhapuspetugasActionPerformed

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();

        mainpanel.add(Phome);
        mainpanel.repaint();
        mainpanel.revalidate();    }//GEN-LAST:event_jLabel23MouseClicked

    private void textcarikelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textcarikelasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textcarikelasActionPerformed

    private void textcarikelasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textcarikelasKeyReleased
        carikelas();
    }//GEN-LAST:event_textcarikelasKeyReleased

    private void caridatasppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caridatasppActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caridatasppActionPerformed

    private void caridatasppKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caridatasppKeyReleased
        carispp();
    }//GEN-LAST:event_caridatasppKeyReleased

    private void caripetuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_caripetuKeyReleased
        caripetugas();
    }//GEN-LAST:event_caripetuKeyReleased

    private void tuserpetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tuserpetugasKeyPressed
        String text = tuserpetugas.getText();
        if (text.length() > 35) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 35.");
            String potongkarakter = text.substring(0, 13);
            tuserpetugas.setText(potongkarakter);
        }
    }//GEN-LAST:event_tuserpetugasKeyPressed

    private void ttahunKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttahunKeyPressed

    }//GEN-LAST:event_ttahunKeyPressed

    private void ttahunKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttahunKeyReleased
        String text = ttahun.getText();
        if (text.length() > 9) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 9.");
            String potongkarakter = text.substring(0, 9);
            ttahun.setText(potongkarakter);
        }

        String regex = "^[0-9/]+$";
        String input = ttahun.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            ttahun.setText("");
        }
    }//GEN-LAST:event_ttahunKeyReleased

    private void tnominalKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnominalKeyReleased
        String text = tnominal.getText();
        try {
        // mengambil nilai dari textfield pertama
        int angka = Integer.parseInt(tnominal.getText());
        
        // melakukan perhitungan
        int hasil = angka * 6;
        
        // menampilkan hasil perhitungan di textfield kedua
        totalnom.setText(Integer.toString(hasil));
        
    } catch (NumberFormatException e) {
        // jika input bukan angka, tampilkan pesan error
        totalnom.setText("Error: Masukkan angka");
    }
        if (text.length() > 7) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 7.");
            String potongkarakter = text.substring(0, 7);
            tnominal.setText(potongkarakter);
        }
        String regex = "^[0-9]+$";
        String input = tnominal.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            tnominal.setText("");
        }
    }//GEN-LAST:event_tnominalKeyReleased

    private void tnamakelasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnamakelasKeyReleased
        String text = tnamakelas.getText();
        if (text.length() > 10) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 10.");
            String potongkarakter = text.substring(0, 10);
            tnamakelas.setText(potongkarakter);
        }
    }//GEN-LAST:event_tnamakelasKeyReleased

    private void backsppMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backsppMouseClicked
        resetForm();
    }//GEN-LAST:event_backsppMouseClicked

    private void jLabel78MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseClicked
        resetFormkelas();
    }//GEN-LAST:event_jLabel78MouseClicked

    private void backssiwaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backssiwaMouseClicked
        resetFormsiswa();
    }//GEN-LAST:event_backssiwaMouseClicked

    private void tnisnsiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnisnsiswaKeyReleased
        String text = tnisnsiswa.getText();
        if (text.length() > 10) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 10.");
            String potongkarakter = text.substring(0, 10);
            tnisnsiswa.setText(potongkarakter);
        }

        String regex = "^[0-9]+$";
        String input = tnisnsiswa.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            tnisnsiswa.setText("");
        }
    }//GEN-LAST:event_tnisnsiswaKeyReleased

    private void tnisnsiswaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnisnsiswaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_tnisnsiswaKeyTyped

    private void tnissiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnissiswaKeyReleased
        String text = tnissiswa.getText();
        if (text.length() > 8) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 8.");
            String potongkarakter = text.substring(0, 8);
            tnissiswa.setText(potongkarakter);
        }

        String regex = "^[0-9]+$";
        String input = tnissiswa.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            tnissiswa.setText("");
        }
    }//GEN-LAST:event_tnissiswaKeyReleased

    private void ttelesiswaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttelesiswaKeyReleased
        String text = ttelesiswa.getText();
        if (text.length() > 13) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 13.");
            String potongkarakter = text.substring(0, 13);
            ttelesiswa.setText(potongkarakter);
        }

        String regex = "^[0-9]+$";
        String input = ttelesiswa.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            ttelesiswa.setText("");
        }
    }//GEN-LAST:event_ttelesiswaKeyReleased

    private void tnamasiswasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tnamasiswasKeyReleased
        String text = tnamasiswas.getText();
        if (text.length() > 35) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 35.");
            String potongkarakter = text.substring(0, 35);
            tnamasiswas.setText(potongkarakter);
        }
    }//GEN-LAST:event_tnamasiswasKeyReleased

    private void ttelpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ttelpKeyReleased
        String text = ttelp.getText();
        if (text.length() > 13) {
            JOptionPane.showMessageDialog(null, "Panjang karakter tidak boleh lebih dari 13.");
            String potongkarakter = text.substring(0, 13);
            ttelp.setText(potongkarakter);
        }

        String regex = "^[0-9]+$";
        String input = ttelp.getText();
        if (!input.matches(regex)) {
            JOptionPane.showMessageDialog(null, "Harus berupa Angka.");
            ttelp.setText("");
        }
    }//GEN-LAST:event_ttelpKeyReleased

    private void hoverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoverMouseClicked
        System.exit(0);
    }//GEN-LAST:event_hoverMouseClicked

    private void asliMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_asliMouseEntered
        asli.setVisible(false);
        asli.setEnabled(false);
        hover.setEnabled(true);
//        hover.setEnabled(false);
    }//GEN-LAST:event_asliMouseEntered

    private void asliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_asliMouseExited
        asli.setVisible(true);
        asli.setEnabled(true);
        hover.setEnabled(false);
        hover.setEnabled(false);
    }//GEN-LAST:event_asliMouseExited

    private void bsppMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsppMouseReleased
        bspp.setForeground(Color.LIGHT_GRAY);
    }//GEN-LAST:event_bsppMouseReleased

    private void bsppMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsppMouseEntered
        bspp.setForeground(new Color(204, 255, 204)); // mengubah warna foreground label menjadi merah
    }//GEN-LAST:event_bsppMouseEntered

    private void bsppMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bsppMouseExited
        bspp.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_bsppMouseExited

    private void bkeluarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkeluarMouseEntered
        bkeluar.setForeground(new Color(255, 77, 77));
    }//GEN-LAST:event_bkeluarMouseEntered

    private void bkeluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkeluarMouseExited
        bkeluar.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_bkeluarMouseExited

    private void bkeluarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkeluarMouseReleased
        bkeluar.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_bkeluarMouseReleased

    private void hoverMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hoverMouseEntered
        hover.setBackground(new Color(255, 255, 255, 128));
    }//GEN-LAST:event_hoverMouseEntered

    private void siswaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siswaMouseEntered
        siswa.setForeground(new Color(204, 204, 204));
    }//GEN-LAST:event_siswaMouseEntered

    private void jLabel23MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseEntered
        jLabel23.setForeground(new Color(204, 204, 204));
    }//GEN-LAST:event_jLabel23MouseEntered

    private void jLabel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseExited
        jLabel23.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_jLabel23MouseExited

    private void siswaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siswaMouseExited
        siswa.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_siswaMouseExited

    private void totalnomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalnomActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalnomActionPerformed

    private void totalnomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_totalnomKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_totalnomKeyReleased

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
            java.util.logging.Logger.getLogger(tampilan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tampilan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tampilan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tampilan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tampilan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Phome;
    private javax.swing.JPanel Phome1;
    private javax.swing.JPanel Pkelas;
    private javax.swing.JPanel Pkelas1;
    private javax.swing.JPanel Ppembayaran;
    private javax.swing.JPanel Ppembayaran1;
    private javax.swing.JPanel Ppetugas;
    private javax.swing.JPanel Ppetugas1;
    public javax.swing.JPanel Psiswa;
    public javax.swing.JPanel Psiswa1;
    private javax.swing.JPanel Psppp;
    private javax.swing.JPanel Psppp1;
    private javax.swing.JLabel asli;
    private javax.swing.JLabel background;
    private javax.swing.JLabel backpembayaran;
    private javax.swing.JLabel backspp;
    private javax.swing.JLabel backssiwa;
    private javax.swing.JButton bcarikelas;
    private javax.swing.JButton bcarikelas1;
    private javax.swing.JButton beditkelas;
    private javax.swing.JButton beditkelas1;
    private javax.swing.JButton beditpetugas;
    private javax.swing.JButton beditpetugas1;
    public javax.swing.JButton beditspp;
    public javax.swing.JButton beditspp1;
    private javax.swing.JButton bhapuskelas;
    private javax.swing.JButton bhapuskelas1;
    private javax.swing.JButton bhapuspetugas;
    public javax.swing.JButton bhapusspp;
    public javax.swing.JButton bhapusspp1;
    public javax.swing.JLabel bkelas;
    private javax.swing.JLabel bkelas1;
    private javax.swing.JLabel bkeluar;
    private javax.swing.JLabel bkeluar1;
    private javax.swing.JComboBox<String> boxlevel;
    private javax.swing.JComboBox<String> boxlevel1;
    private javax.swing.JLabel bpembayaran;
    private javax.swing.JLabel bpembayaran1;
    public javax.swing.JLabel bpetugas;
    public javax.swing.JLabel bpetugas1;
    private javax.swing.JButton bsimpankelas;
    private javax.swing.JButton bsimpankelas1;
    private javax.swing.JButton bsimpanpetu;
    private javax.swing.JButton bsimpanpetu2;
    private javax.swing.JButton bsimpanpetu3;
    private javax.swing.JButton bsimpansiswa;
    private javax.swing.JButton bsimpansiswa1;
    public javax.swing.JButton bsimpanspp;
    public javax.swing.JButton bsimpanspp1;
    public javax.swing.JLabel bspp;
    private javax.swing.JLabel bspp1;
    private javax.swing.JButton btampil;
    private javax.swing.JButton btampilkelas;
    private javax.swing.JButton btampilkelas1;
    private javax.swing.JButton btneditsis;
    private javax.swing.JButton btnhapussis;
    private javax.swing.JTextField cari;
    private javax.swing.JTextField caridataspp;
    private javax.swing.JTextField caripetu;
    private javax.swing.JComboBox<String> cgannep;
    private javax.swing.JComboBox<String> comboidkelas;
    private javax.swing.JComboBox<String> comboidkelas1;
    private javax.swing.JComboBox<String> comboidspp;
    private javax.swing.JComboBox<String> comboidspp1;
    private javax.swing.JLabel distokelas;
    private javax.swing.JLabel distopetugas;
    public javax.swing.JLabel greating;
    public javax.swing.JLabel greating1;
    private javax.swing.JLabel hover;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField6;
    private com.k33ptoo.components.KButton kButton1;
    private com.k33ptoo.components.KButton kButton2;
    private com.k33ptoo.components.KButton kButton3;
    private com.k33ptoo.components.KButton kButton4;
    private com.k33ptoo.components.KButton kButton5;
    private com.k33ptoo.components.KButton kButton6;
    public final javax.swing.JButton lihatdata = new javax.swing.JButton();
    public final javax.swing.JButton lihatdata1 = new javax.swing.JButton();
    public final javax.swing.JButton lihatdata2 = new javax.swing.JButton();
    public final javax.swing.JButton lihatdata3 = new javax.swing.JButton();
    private javax.swing.JLabel ljam;
    private javax.swing.JLabel ljam1;
    private javax.swing.JLabel ltgl;
    private javax.swing.JLabel ltgl1;
    public javax.swing.JPanel mainpanel;
    public javax.swing.JPanel mainpanel1;
    private javax.swing.JLabel namasiswa;
    private javax.swing.JLabel namasiswa1;
    private javax.swing.JLabel namasiswa2;
    private javax.swing.JLabel namasiswa3;
    private javax.swing.JLabel namasiswa4;
    private javax.swing.JLabel namasiswa5;
    public javax.swing.JLabel namauser;
    public javax.swing.JLabel namauser1;
    private java.awt.Panel panel1;
    private java.awt.Panel panel2;
    public javax.swing.JLabel siswa;
    public javax.swing.JLabel siswa1;
    private javax.swing.JLabel tAngkatan;
    private javax.swing.JTextField tId_Angkatan;
    private javax.swing.JTextField tId_petugas;
    private javax.swing.JTextField tJumlahbayar;
    private javax.swing.JTextField tKelas;
    private javax.swing.JTextField tNama_siswa;
    private javax.swing.JTextField tNisn;
    private javax.swing.JTextField tNo_Transaki;
    private javax.swing.JLabel tPetugas;
    private javax.swing.JTextField tTanggal;
    private javax.swing.JTable tabelkelas;
    private javax.swing.JTable tabelkelas1;
    private javax.swing.JTable tabelpetugas;
    private javax.swing.JTable tabelpetugas1;
    private javax.swing.JTable tabelsiswamaster;
    private javax.swing.JTable tabelsiswamaster1;
    private javax.swing.JTable tabelsiswapembayaran;
    private javax.swing.JTable tabelspp;
    private javax.swing.JTable tabelspp1;
    public javax.swing.JTextArea talamatsiswa;
    public javax.swing.JTextArea talamatsiswa1;
    public javax.swing.JTextField tampidspp;
    public javax.swing.JTextField tampidspp1;
    public javax.swing.JTextField tampilidkelas;
    public javax.swing.JTextField tampilidkelas1;
    private javax.swing.JLabel tampilsiswa;
    private javax.swing.JTextField textcarikelas;
    private javax.swing.JTextField tidkelas;
    private javax.swing.JTextField tidkelas1;
    private javax.swing.JTextField tidpetugas;
    private javax.swing.JTextField tidpetugas1;
    private javax.swing.JTextField tidspp;
    private javax.swing.JTextField tidspp1;
    private javax.swing.JTextField tkom;
    private javax.swing.JTextField tkom1;
    private javax.swing.JLabel tnama_siswastatus;
    private javax.swing.JTextField tnamakelas;
    private javax.swing.JTextField tnamakelas1;
    private javax.swing.JTextField tnamapetugas;
    private javax.swing.JTextField tnamapetugas1;
    public javax.swing.JTextField tnamasiswas;
    public javax.swing.JTextField tnamasiswas1;
    private javax.swing.JTextField tnis;
    private javax.swing.JTextField tnis1;
    private javax.swing.JTextField tnis2;
    private javax.swing.JTextField tnis3;
    private javax.swing.JTextField tnis4;
    private javax.swing.JTextField tnis5;
    private javax.swing.JTextField tnis6;
    private javax.swing.JTextField tnis7;
    public javax.swing.JTextField tnisnsiswa;
    public javax.swing.JTextField tnisnsiswa1;
    public javax.swing.JTextField tnissiswa;
    public javax.swing.JTextField tnissiswa1;
    private javax.swing.JTextField tnominal;
    private javax.swing.JTextField tnominal1;
    private javax.swing.JTextField totalnom;
    private javax.swing.JPasswordField tpasspetu;
    private javax.swing.JPasswordField tpasspetu1;
    private javax.swing.JTextField ttahun;
    private javax.swing.JTextField ttahun1;
    public javax.swing.JTextField ttelesiswa;
    public javax.swing.JTextField ttelesiswa1;
    private javax.swing.JTextField ttelp;
    private javax.swing.JTextField ttelp1;
    private javax.swing.JTextField tuserpetugas;
    private javax.swing.JTextField tuserpetugas1;
    private javax.swing.JTextField txtcarisis;
    // End of variables declaration//GEN-END:variables
 public void resetFormsiswa() {
        tnisnsiswa.setText("");
        tnissiswa.setText("");
        tnamasiswas.setText("");
        comboidkelas.setSelectedItem("~pilih kelas");
        talamatsiswa.setText("");
        ttelesiswa.setText("");
        comboidspp.setSelectedItem("~angkatan tahun");

        tablesiswa();
        bsimpansiswa.setEnabled(true);
        tnisnsiswa.setEnabled(true);
    }

    public void resetForm() {
        tidspp.setText("");
        ttahun.setText("");
        tnominal.setText("");
        tablespp();

        bsimpanspp.setEnabled(true);
        tidspp.setEnabled(true);
        tidspp.setText("");
        bsimpanspp.setVisible(true);
    }
// reset formkelas

    public void resetFormkelas() {
        tidkelas.setText("");
        tnamakelas.setText("");
        tkom.setText("");
        tablekelas();

        bsimpankelas.setEnabled(true);
        tidkelas.setText("");
    }

    public void resetFormpetu() {
        tidpetugas.setText("");
        tuserpetugas.setText("");
        tpasspetu.setText("");
        tnamakelas.setText("");
        ttelp.setText("");
        boxlevel.setSelectedItem("pilih");

        tablepetugas();

        bsimpanpetu.setEnabled(true);
        tidpetugas.setText("");
    }

    private void carispp() {
        String[] judul = {"ID_SPP", "ANGAKTAN-TAHUN", "NOMINAL"};
        DefaultTableModel model = new DefaultTableModel(judul, 0);
        tabelspp.setModel(model);
        try {
            String sql = "SELECT * FROM spp WHERE Id_spp LIKE '%" + caridataspp.getText() + "%' OR tahun LIKE '%" + caridataspp.getText() + "%' OR nominal LIKE '%" + caridataspp.getText() + "%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String idspp = rs.getString("Id_spp");
                String tahun = rs.getString("tahun");
                String nom = rs.getString("nominal");
                String[] data = {idspp, tahun, nom};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void caripetugas() {
        String[] judul = {"ID PETUGAS", "USERNAME", "PASSWORD", "NAMA PETUGAS", "NO TELPON", "LEVEL"};
        DefaultTableModel model = new DefaultTableModel(judul, 0);
        tabelpetugas.setModel(model);
        try {
            String sql = "SELECT * FROM petugas WHERE Id_petugas LIKE '%" + caripetu.getText() + "%' OR username LIKE '%"
                    + caripetu.getText() + "%' OR password LIKE '%"
                    + caripetu.getText() + "%' OR nama_petugas LIKE '%"
                    + caripetu.getText() + "%' OR no_tlppet LIKE '%"
                    + caripetu.getText() + "%' OR level LIKE '%"
                    + caripetu.getText() + "%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String idpetugas = rs.getString("Id_petugas");
                String user = rs.getString("username");
                String password = rs.getString("password");
                String nama = rs.getString("nama_petugas");
                String notele = rs.getString("no_tlppet");
                String levelpet = rs.getString("level");

                String[] data = {idpetugas, user, password, nama, notele, levelpet};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void carisiswa() {
        String[] judul = {"NISN", "NIS", "NAMA", "KELAS", "ALAMAT", "NO TELPON", "TAHUN SPP"};
        DefaultTableModel model = new DefaultTableModel(judul, 0);
        tabelsiswamaster.setModel(model);
        try {
            String sql = "SELECT * FROM siswa WHERE nisn LIKE '%" + txtcarisis.getText() + "%' OR nis LIKE '%"
                    + txtcarisis.getText() + "%' OR nama LIKE '%"
                    + txtcarisis.getText() + "%' OR Id_kelas LIKE '%"
                    + txtcarisis.getText() + "%' OR alamat LIKE '%"
                    + txtcarisis.getText() + "%' OR no_telp LIKE '%"
                    + txtcarisis.getText() + "%' OR id_spp LIKE '%"
                    + txtcarisis.getText() + "%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String nisn = rs.getString("nisn");
                String nis = rs.getString("nis");
                String namasis = rs.getString("nama");
                String kelassis = rs.getString("Id_kelas");
                String alamatsis = rs.getString("alamat");
                String notelpsis = rs.getString("no_telp");
                String idsppsis = rs.getString("id_spp");

                String[] data = {nisn, nis, namasis, kelassis, alamatsis, notelpsis, idsppsis};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void carikelas() {
        String[] judul = {"ID_Kelas", "NAMA KELAS", "KOMPETENSI KEAHLIAN"};
        DefaultTableModel model = new DefaultTableModel(judul, 0);
        tabelkelas.setModel(model);
        try {
            String sql = "SELECT * FROM kelas WHERE Id_kelas LIKE '%" + textcarikelas.getText() + "%' OR nama_kelas LIKE '%" + textcarikelas.getText() + "%' OR kompetensi_keahlian LIKE '%" + textcarikelas.getText() + "%'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs = con.createStatement().executeQuery(sql);

            while (rs.next()) {
                String idkelas = rs.getString("Id_kelas");
                String namkel = rs.getString("nama_kelas");
                String kompetensi = rs.getString("kompetensi_keahlian");
                String[] data = {idkelas, namkel, kompetensi};
                model.addRow(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
