package server;

/**
 * Menyimpan data session pengguna yang sedang login.
 * Role: "admin" | "staff" | "boss"
 */
public class Session {
    private static int idUser = 0;
    private static String username = "";
    private static String role = "";

    public static int getIdUser() { return idUser; }
    public static void setIdUser(int id) { idUser = id; }

    public static String getUsername() { return username; }
    public static void setUsername(String u) { username = u; }

    // Alias agar kode lama yang pakai getNamaUser() / setNamaUser() tetap compile
    public static String getNamaUser() { return username; }
    public static void setNamaUser(String nama) { username = nama; }

    public static String getRole() { return role; }
    public static void setRole(String r) { role = r; }

    public static void clearSession() {
        idUser = 0;
        username = "";
        role = "";
    }
}
