package util;

import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "bodyhealth";
    private static final String FULL_URL = URL + DB_NAME + "?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(FULL_URL, USERNAME, PASSWORD);
    }

    public static void init() {
        // ensure database exists (requires a connection to server DB) — create DB if missing
        try (Connection c = DriverManager.getConnection(URL + "?useSSL=false&serverTimezone=UTC", USERNAME, PASSWORD);
             Statement st = c.createStatement()) {
            st.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // create tables in bodyhealth
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    username VARCHAR(50) UNIQUE NOT NULL,
                    password VARCHAR(100) NOT NULL,
                    age INT,
                    gender VARCHAR(10)
                );
            """);

            st.execute("""
                CREATE TABLE IF NOT EXISTS health_metrics (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    user_id INT,
                    metric_date DATE,
                    weight DOUBLE,
                    systolic INT,
                    diastolic INT,
                    heart_rate INT,
                    notes VARCHAR(255),
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                );
            """);

            // default admin user
            st.execute("""
                INSERT IGNORE INTO users (username, password, age, gender)
                VALUES ('admin', '1234', 30, 'other');
            """);

            System.out.println("DB initialized.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
