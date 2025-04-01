/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */

import models.Staff;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {

    // CREATE: Add a new staff member
    public void addStaff(Staff staff) {
        String sql = "INSERT INTO staff (first_name, last_name, role, phone_number, email, hire_date, salary, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            
            stmt.setString(1, staff.getFirstName());
            stmt.setString(2, staff.getLastName());
            stmt.setString(3, staff.getRole());
            stmt.setString(4, staff.getPhoneNumber());
            stmt.setString(5, staff.getEmail());
            stmt.setDate(6, Date.valueOf(staff.getHireDate()));
            stmt.setDouble(7, staff.getSalary());
            stmt.setInt(8, staff.getUserId());
            
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Staff added successfully!");
            } else {
                System.out.println("Staff not added.");
            }
            
        } catch (SQLException e) {
            System.out.println(" Error adding staff: " + e.getMessage());
        }
    }

    // READ: Get all staff members
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff";
        
        try (Connection conn = DBConnection.getConnection(); 
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Staff staff = new Staff(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("role"),
                    rs.getString("phone_number"),
                    rs.getString("email"),
                    rs.getDate("hire_date").toLocalDate(),
                    rs.getDouble("salary"),
                    rs.getInt("user_id")
                );
                staffList.add(staff);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }
        return staffList;
    }

    // UPDATE: Modify an existing staff record
    public void updateStaff(Staff staff) {
        
        String sql = "UPDATE staff SET first_name=?, last_name=?, role=?, phone_number=?, email=?, hire_date=?, salary=?, user_id=? WHERE id=?";
        
        try (Connection conn = DBConnection.getConnection(); 
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, staff.getFirstName());
            stmt.setString(2, staff.getLastName());
            stmt.setString(3, staff.getRole());
            stmt.setString(4, staff.getPhoneNumber());
            stmt.setString(5, staff.getEmail());
            stmt.setDate(6, Date.valueOf(staff.getHireDate()));
            stmt.setDouble(7, staff.getSalary());
            stmt.setInt(8, staff.getUserId());
            stmt.setInt(9, staff.getId());
            
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Staff updated successfully!");
            } else {
                System.out.println("Staff update failed.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating staff: " + e.getMessage());
        }
    }

    // DELETE: Remove a staff member
    public void deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE id=?";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, staffId);
            int rowsDeleted = stmt.executeUpdate();
             if (rowsDeleted > 0) {
                System.out.println(" Staff deleted successfully!");
            } else {
                System.out.println("Staff not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting staff: " + e.getMessage());
        }
    }
}

