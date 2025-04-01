/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import util.DBConnection;
import models.Medicine;

public class MedicineDAO {
    
    //CREATE: Add a new medicine to the database
    public void addMedicine(Medicine medicine) {
        String sql = "INSERT INTO medications (name, brand_name, form, dosage, quantity, price, expiry_date, supplier_id, prescription_required) VALUES (?,?,?,?,?,?,?,?,?)";
        
        
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            
            System.out.println("Debug: Selected Supplier ID before inserting medicine: " + medicine.getSupplierId());
            
            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setString(2, medicine.getBrandName());
            preparedStatement.setString(3, medicine.getForm());
            preparedStatement.setInt(4, medicine.getDosage());
            preparedStatement.setInt(5, medicine.getQuantity());
            preparedStatement.setDouble(6, medicine.getPrice());
            preparedStatement.setDate(7, new java.sql.Date(medicine.getExpiryDate().getTime()));
            preparedStatement.setInt(8, medicine.getSupplierId());
            preparedStatement.setBoolean(9, medicine.isPrescriptionRequired());

            preparedStatement.executeUpdate();
            System.out.println("Medicine added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding medicine: " + e.getMessage());
        }
        

    }
    
    
    //READ: Gets all medications
    public List<Medicine> getAllMedications() {
        List<Medicine> medications = new ArrayList<>();
        String sql = "SELECT * FROM medications";
        
        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Medicine medicine = new Medicine(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("brand_name"),
                rs.getString("form"),
                rs.getInt("dosage"),
                rs.getInt("quantity"),
                rs.getDouble("price"),
                rs.getDate("expiry_date"),
                rs.getInt("supplier_id"),
                rs.getBoolean("prescription_required")
                );
                medications.add(medicine);
            }
            System.out.println("Medications retrieved successfully");
        } catch (SQLException e) {
            System.out.println("Error fetching medicines: " + e.getMessage());
        }
        return medications;
    }
    
    //READ:Get medicine by ID
    public Medicine getMedicineById(int id){
        String sql = "SELECT * FROM medications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
            
                if (rs.next()) {
                    return new Medicine (
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("brand_name"),
                    rs.getString("form"),
                    rs.getInt("dosage"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getDate("expiry_date"),
                    rs.getInt("supplier_id"),
                    rs.getBoolean("prescription_required")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching medicine: "+ e.getMessage());
        }
        return null;
    }
    
    //UPDATE: Modify an existing medicine
    public boolean updateMedicine(Medicine medicine){
        String sql = "UPDATE medications SET name = ?, brand_name = ?, form = ?, dosage = ?, quantity = ?, price = ?, expiry_date = ?, supplier_id = ?, prescription_required = ? WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medicine.getName());
            pstmt.setString(2, medicine.getBrandName());
            pstmt.setString(3, medicine.getForm());
            pstmt.setInt(4, medicine.getDosage());
            pstmt.setInt(5, medicine.getQuantity());
            pstmt.setDouble(6, medicine.getPrice());
            pstmt.setDate(7, new java.sql.Date(medicine.getExpiryDate().getTime()));
            pstmt.setInt(8, medicine.getSupplierId());
            pstmt.setBoolean(9, medicine.isPrescriptionRequired());
            pstmt.setInt(10, medicine.getId());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Medicine updated successfully!");
                return true;
            } else {
                System.out.println("Medicine update failed. No matching record found.");
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error updating medicine: " + e.getMessage());
            return false;
        }
    }
    
    //DELETE: Remove a medicine by its ID
    public boolean deleteMedicine(int medicineId) {
        String sql = "DELETE FROM medications WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, medicineId);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0 ) {
                System.out.println("Medicine deleted successfully!");
                return true;
            } else {
                System.out.println("No medicine found with the given ID.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error deleting medicine: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getSupplierList() {
        List<String> suppliers = new ArrayList<>();
        String query = "SELECT supplier_name FROM suppliers"; // Adjust column name if needed

        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                suppliers.add(rs.getString("supplier_name")); // Ensure column name matches your DB
            }
        } catch (SQLException e) {
            System.out.println("Error fetching suppliers: " + e.getMessage());
        }
           return suppliers;
    }
    
    public int getSupplierIdByName(String supplierName) {
        String query = "SELECT supplier_id FROM suppliers WHERE supplier_name = ?";
    
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
         
            stmt.setString(1, supplierName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int supplierId = rs.getInt("supplier_id");
                //A debug statement to confirm that java is retrieving the supplier ID from the database
                System.out.println("Debug: Retrieved Supplier ID for " +supplierName + " is " + supplierId);
                
                return supplierId;
                        
            }
        } catch (SQLException e) {
            System.out.println("Error fetching supplier ID: " + e.getMessage());
        }
        return -1; //return -1 if the supplier is not found
}


       
}
