/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

/**
 *
 * @author hp
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/pharmacy_db"; // Your DB name
    private static final String USER = "root"; // Your MySQL username
    private static final String PASSWORD = ""; // Your MySQL password (empty if none)
    
    private static Connection conn = null;

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC Driver
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database Connected Successfully!");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("❌ Database Connection Failed: " + e.getMessage());
        }
        return conn;
    }
    
    // **Fix: Add closeConnection() method**
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("✅ Database Connection Closed.");
            } catch (SQLException e) {
                System.out.println("❌ Error Closing Connection: " + e.getMessage());
            }
        }
    }

}
