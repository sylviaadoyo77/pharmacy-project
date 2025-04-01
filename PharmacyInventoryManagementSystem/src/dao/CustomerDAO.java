/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author hp
 */

import models.Customer;
import util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    
    // Method to add a new customer
    public void addCustomer(Customer customer) {
        String query = "INSERT INTO customers (first_name, last_name, date_of_birth, gender, phone, email, address, city) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7, customer.getAddress());
            stmt.setString(8, customer.getCity());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Customer added successfully!");
            } else {
                System.out.println("Customer not added.");
            }

        } catch (SQLException e) {
            System.out.println(" Error adding customer: " + e.getMessage());
        }
    }

    // Method to fetch all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getDate("date_of_birth").toLocalDate(),
                        rs.getString("gender"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("city")
                );
                customers.add(customer);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching customers: " + e.getMessage());
        }

        return customers;
    }

    // Method to update a customer's details
    public void updateCustomer(Customer customer) {
        String query = "UPDATE customers SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, email = ?, address = ?, city = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setDate(3, Date.valueOf(customer.getDateOfBirth()));
            stmt.setString(4, customer.getGender());
            stmt.setString(5, customer.getPhone());
            stmt.setString(6, customer.getEmail());
            stmt.setString(7,customer.getAddress());
            stmt.setString(8, customer.getCity());
            stmt.setInt(9, customer.getId());

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer updated successfully!");
            } else {
                System.out.println("Customer update failed.");
            }

        } catch (SQLException e) {
             System.out.println("Error updating customer: " + e.getMessage());
        }
    }

    // Method to delete a customer by ID
    public void deleteCustomer(int id) {
        String query = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
             if (rowsDeleted > 0) {
                System.out.println(" Customer deleted successfully!");
            } else {
                System.out.println("Customer not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }
}

