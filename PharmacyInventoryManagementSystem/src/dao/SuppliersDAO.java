/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */
import models.Suppliers;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuppliersDAO {

    // Create (Add Supplier)
    public static boolean addSupplier(Suppliers supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, phone_number, email, address, city, country, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getPhoneNumber());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setDate(8, Date.valueOf(supplier.getCreatedAt()));

            return stmt.executeUpdate() > 0; // Returns true if at least one row is inserted
            
        } catch (SQLException e) {
            System.out.println("Error adding supplier: " + e.getMessage());
            return false;
        }
    }

    // Read (Get All Suppliers)
    public static List<Suppliers> getAllSuppliers() {
        List<Suppliers> suppliersList = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Suppliers supplier = new Suppliers(
                        rs.getInt("supplier_id"),
                        rs.getString("supplier_name"),
                        rs.getString("contact_person"),
                        rs.getString("phone_number"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("city"),
                        rs.getString("country"),
                        rs.getDate("created_at").toLocalDate()
                );
                suppliersList.add(supplier);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error fetching suppliers: " + e.getMessage());
        }
        return suppliersList;
    }

    // Update (Modify Supplier)
    public static boolean updateSupplier(Suppliers supplier) {
        String sql = "UPDATE suppliers SET supplier_name = ?, contact_person = ?, phone_number = ?, email = ?, address = ?, city = ?, country = ? WHERE supplier_id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getPhoneNumber());
            stmt.setString(4, supplier.getEmail());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setInt(8, supplier.getSupplierId());

            return stmt.executeUpdate() > 0; // Returns true if at least one row is updated
            
        } catch (SQLException e) {
            System.out.println("❌ Error updating supplier: " + e.getMessage());
            return false;
        }
    }

    // Delete (Remove Supplier)
public static boolean deleteSupplier(int supplierId) {
    String sql = "DELETE FROM suppliers WHERE supplier_id = ?";
    
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, supplierId);
        return stmt.executeUpdate() > 0; // Returns true if at least one row is deleted
        
    } catch (SQLException e) {
        System.out.println("❌ Error deleting supplier: " + e.getMessage());
        return false; // Ensure it returns false when an error occurs
    }
}

}
           

