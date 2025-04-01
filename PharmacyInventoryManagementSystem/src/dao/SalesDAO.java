/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */
import models.Sales;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    // Add a new sale
    public void addSale(Sales sale) {
        String sql = "INSERT INTO sales (medications_id, customer_id, staff_id, quantity_sold, total_price, payment_method, sale_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getMedicationsId());
            stmt.setInt(2, sale.getCustomerId());
            stmt.setInt(3, sale.getStaffId());
            stmt.setInt(4, sale.getQuantitySold());
            stmt.setDouble(5, sale.getTotalPrice());
            stmt.setString(6, sale.getPaymentMethod());
            stmt.setDate(7, Date.valueOf(sale.getSaleDate()));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Sale added successfully!");
            } else {
                System.out.println("sale not added.");
            }

        } catch (SQLException e) {
            System.out.println(" Error adding sales: " + e.getMessage());
        }
    }

    // Retrieve all sales
    public List<Sales> getAllSales() {
        List<Sales> salesList = new ArrayList<>();
        String sql = "SELECT * FROM sales";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setId(rs.getInt("id"));
                sale.setMedicationsId(rs.getInt("medications_id"));
                sale.setCustomerId(rs.getInt("customer_id"));
                sale.setStaffId(rs.getInt("staff_id"));
                sale.setQuantitySold(rs.getInt("quantity_sold"));
                sale.setTotalPrice(rs.getDouble("total_price"));
                sale.setPaymentMethod(rs.getString("payment_method"));
                sale.setSaleDate(rs.getDate("sale_date").toLocalDate());

                salesList.add(sale);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching sales: " + e.getMessage());
        }
        return salesList;
    }

    // Update a sale
    public void updateSale(Sales sale) {
        String sql = "UPDATE sales SET medications_id = ?, customer_id = ?, staff_id = ?, quantity_sold = ?, total_price = ?, payment_method = ?, sale_date = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, sale.getMedicationsId());
            stmt.setInt(2, sale.getCustomerId());
            stmt.setInt(3, sale.getStaffId());
            stmt.setInt(4, sale.getQuantitySold());
            stmt.setDouble(5, sale.getTotalPrice());
            stmt.setString(6, sale.getPaymentMethod());
            stmt.setDate(7, Date.valueOf(sale.getSaleDate()));
            stmt.setInt(8, sale.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Sales updated successfully!");
            } else {
                System.out.println("Sales update failed.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating sales: " + e.getMessage());
        }
    }

    // Delete a sale
    public void deleteSale(int id) {
        String sql = "DELETE FROM sales WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
             if (rowsDeleted > 0) {
                System.out.println(" Sales deleted successfully!");
            } else {
                System.out.println("Sales not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting sales: " + e.getMessage());
        }
    }
}

