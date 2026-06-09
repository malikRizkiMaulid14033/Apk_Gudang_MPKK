package server;

public class Session {
    private static int id_user = 1; // Default hardcoded ke 1 agar Run File langsung tetap berhasil
    private static String nama_user = "admin";

    public static int getIdUser() {
        return id_user;
    }

    public static void setIdUser(int id) {
        id_user = id;
    }

    public static String getNamaUser() {
        return nama_user;
    }

    public static void setNamaUser(String nama) {
        nama_user = nama;
    }
}
