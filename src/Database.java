import java.sql.*;

public class Database {

    private static final String URL = "jdbc:sqlite:users.db";

    public static void databaseCreation() {
        String url = URL;

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS users ("
                    + " username TEXT NOT NULL UNIQUE,"
                    + " password TEXT NOT NULL)";

            stmt.execute(sql);

            System.out.println("Database and table set up successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {

        String url = "jdbc:sqlite:users.db";
        String sql = "INSERT INTO users(username, password) VALUES(?,?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);  // Note: This should be a hashed password. DO NOT store plain text passwords.
            pstmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean isUsernameTaken(String username) {
        String query = "SELECT username FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();


            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }
            public boolean authenticateUser(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            // Check if the username exists in the database.
            if (rs.next()) {
                String storedPassword = rs.getString("password");

                // In a real-world scenario, you would compare hashed passwords.
                if (password.equals(storedPassword)) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
