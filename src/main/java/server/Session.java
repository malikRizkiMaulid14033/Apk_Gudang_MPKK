package server;

public class Session {
    private static int idUser = 1; // Default hardcoded ke 1 agar Run File langsung tetap berhasil
    private static String namaUser = "admin";

    public static int getIdUser() {
        return idUser;
    }

    public static void setIdUser(int id) {
        idUser = id;
    }

    public static String getNamaUser() {
        return namaUser;
    }

    public static void setNamaUser(String nama) {
        namaUser = nama;
    }
    public static void clearSession() {
        idUser = 0;
        namaUser = null;
    }
}

//package server;
//
//public class Session {
//    private static int idUser = 0;
//    private static String role = null;
//    private static String username = "";
//
//    public static void setRole(String r) {
//        role = r;
//    }
//
//    public static String getRole() {
//        return role;
//    }
//
//    public static void setSession(int id, String user) {
//        idUser = id;
//        username = user;
//    }
//
//    public static int getIdUser() {
//        return idUser;
//    }
//
//    public static String getUsername() {
//        return username;
//    }
//
//    public static void clearSession() {
//        idUser = 0;
//        username = null;
//    }
//}

