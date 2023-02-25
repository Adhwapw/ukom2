/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adhwa
 */
public class userSession {
    private static String id;
    private static String username;
    private static String nama;
    private static String level;
    private static String nisn;
    private static String nama_siswa;
    
    //user session : id_________________________________________________________
    public static String get_id() {
        return id;
    } 
    public static void set_id(String id) {
        userSession.id = id;
    }
    
    //user session : username___________________________________________________
    public static String get_username() {
        return username;
    } 
    public static void set_username(String username) {
        userSession.username = username;
    }
    
    //user session : nama_______________________________________________________
    public static String get_nama() {
        return nama;
    } 
    public static void set_nama(String nama) {
        userSession.nama = nama;
    }
    
    //user session : level______________________________________________________
    public static String get_level() {
        return level;
    } 
    public static void set_level(String level) {
        userSession.level = level;
    }
    
    //user session : nisn_______________________________________________________
    public static String get_nisn() {
        return nisn;
    } 
    public static void set_nisn(String nisn) {
        userSession.nisn = nisn;
    }
    
    //user session : nama siswa_________________________________________________
    public static String get_nama_siswa() {
        return nama_siswa;
    } 
    public static void set_nama_siswa(String nama_siswa) {
        userSession.nama_siswa = nama_siswa;
    }
}
