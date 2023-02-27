/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adhwa
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class koneksi {
 private Connection Koneksii;
    public Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("sistem berhasil terconnect");
        } catch (ClassNotFoundException ex) {
            System.out.println("sistem tidak berhasil konek");
        }
        String url = "jdbc:mysql://localhost:3306/db_spp2_copy";
        try {
            Koneksii = DriverManager.getConnection(url, "root", "");
            System.out.println("Berhasil koneksi Database");
        } catch (SQLException ex) {
            System.out.println("Gagal koneksi Database");
            JOptionPane.showMessageDialog(null, "Tidak ada database!!");

        }
        return Koneksii;
    }
}
