/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */
import models.Users;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import util.PasswordUtils;


public class UsersDAO {

    // ✅ Create (Add User)
    public static boolean addUser(Users user) {
    String sql = "INSERT INTO users (first_name, last_name, email, password, role, created_at) VALUES (?, ?, ?, ?, ?, ?)";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, user.getFirstName());
        stmt.setString(2, user.getLastName());
        stmt.setString(3, user.getEmail());
        
        // Hash password before storing
        String hashedPassword = PasswordUtils.hashPassword(user.getPassword());
        stmt.setString(4, hashedPassword);

        //ensure role is  not null or empty
        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("user"); //default role
        }
        
        stmt.setString(5,user.getRole());
        
        stmt.setDate(6, Date.valueOf(user.getCreatedAt()));
        
        System.out.println("Insert role: " + user.getRole());

        return stmt.executeUpdate() > 0;
        
    } catch (SQLException e) {
        System.out.println("❌ Error adding user: " + e.getMessage());
        return false;
    }
}


    // ✅ Read (Get All Users)
    public static List<Users> getAllUsers() {
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Users user = new Users(
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDate("created_at").toLocalDate()
                );
                usersList.add(user);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error fetching users: " + e.getMessage());
        }
        return usersList;
    }

    // ✅ Update (Modify User)
    public static boolean updateUser(Users user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ?, role = ? WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getLastName());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            stmt.setInt(6, user.getUserId());

            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error updating user: " + e.getMessage());
            return false;
        }
    }

    // ✅ Delete (Remove User)
    public static boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("❌ Error deleting user: " + e.getMessage());
            return false;
        }
    }
    
    // ✅ Authenticate User (Login)
public static Users authenticateUser(String email, String password) {
    String sql = "SELECT * FROM users WHERE email = ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            String hashedPassword = rs.getString("password");

            // Verify password
            if (PasswordUtils.checkPassword(password, hashedPassword)) {
                return new Users(
                    rs.getInt("user_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getDate("created_at").toLocalDate()
                );
            }
        }
        
    } catch (SQLException e) {
        System.out.println("❌ Error during authentication: " + e.getMessage());
    }
    return null;
}


public static String hashPassword(String password) {
    try {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        throw new RuntimeException("❌ Error hashing password", e);
    }
}


}

