/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

/**
 *
 * @author hp
 */
import dao.SalesDAO;
import models.Sales;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class SalesForm extends JFrame {

    // --- Form Components ---
    private JTextField medicationsIdField, customerIdField, staffIdField, quantitySoldField, totalPriceField, paymentMethodField, saleDateField;
    // saleDateField format: YYYY-MM-DD
    
    // --- Buttons ---
    private JButton addButton, updateButton, deleteButton, viewButton;
    
    // --- Table Components ---
    private JTable salesTable;
    private DefaultTableModel tableModel;
    
    // --- Variable to store selected Sale ID for updates ---
    private int selectedSaleId = -1;
    
    public SalesForm() {
        setTitle("Sales Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        // -------------------- Form Panel (North) --------------------
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        
        // Row 0: Medications ID
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Medications ID:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        medicationsIdField = new JTextField(20);
        formPanel.add(medicationsIdField, gbc);
        
        // Row 1: Customer ID
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx = 1;
        customerIdField = new JTextField(20);
        formPanel.add(customerIdField, gbc);
        
        // Row 2: Staff ID
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Staff ID:"), gbc);
        gbc.gridx = 1;
        staffIdField = new JTextField(20);
        formPanel.add(staffIdField, gbc);
        
        // Row 3: Quantity Sold
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Quantity Sold:"), gbc);
        gbc.gridx = 1;
        quantitySoldField = new JTextField(20);
        formPanel.add(quantitySoldField, gbc);
        
        // Row 4: Total Price
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Total Price:"), gbc);
        gbc.gridx = 1;
        totalPriceField = new JTextField(20);
        formPanel.add(totalPriceField, gbc);
        
        // Row 5: Payment Method
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx = 1;
        paymentMethodField = new JTextField(20);
        formPanel.add(paymentMethodField, gbc);
        
        // Row 6: Sale Date (YYYY-MM-DD)
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Sale Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        saleDateField = new JTextField(20);
        formPanel.add(saleDateField, gbc);
        
        // Row 7: Add Sale Button
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        addButton = new JButton("Add Sale");
        addButton.addActionListener(this::addSale);
        formPanel.add(addButton, gbc);
        
        add(formPanel, BorderLayout.NORTH);
        
        // -------------------- Center Panel: Buttons and Table --------------------
        // Create a panel to hold the buttons and table vertically.
        JPanel centerPanel = new JPanel(new BorderLayout(5,5));
        
        // Buttons Panel (at the top of centerPanel)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View All");
        
        // For update: first load the selected sale then update
        updateButton.addActionListener(e -> {
            loadSelectedSale(); // Load data from the selected table row into the form fields
            updateSale(e);      // Update the sale in the database
        });
        deleteButton.addActionListener(this::deleteSale);
        viewButton.addActionListener(this::viewAllSales);
        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        
        // Table Panel: Sales Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Medications ID", "Customer ID", "Staff ID", "Quantity Sold", "Total Price", "Payment Method", "Sale Date"}, 0);
        salesTable = new JTable(tableModel);
        centerPanel.add(new JScrollPane(salesTable), BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Load existing sales into the table
        viewAllSales(null);
        
        setVisible(true);
    }
    
    // ------------------ Method to Add a Sale ------------------
    private void addSale(ActionEvent e) {
        try {
            // Validate numeric fields are not empty
            if (quantitySoldField.getText().trim().isEmpty() ||
                totalPriceField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantity Sold and Total Price cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int medicationsId = Integer.parseInt(medicationsIdField.getText().trim());
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            int staffId = Integer.parseInt(staffIdField.getText().trim());
            int quantitySold = Integer.parseInt(quantitySoldField.getText().trim());
            double totalPrice = Double.parseDouble(totalPriceField.getText().trim());
            
            String paymentMethod = paymentMethodField.getText().trim();
            String saleDateText = saleDateField.getText().trim();
            
            if (paymentMethod.isEmpty() || saleDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Payment Method and Sale Date must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate saleDate;
            try {
                saleDate = LocalDate.parse(saleDateText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Sale Date format! Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Sales sale = new Sales(medicationsId, customerId, staffId, quantitySold, totalPrice, paymentMethod, saleDate);
            SalesDAO salesDAO = new SalesDAO();
            salesDAO.addSale(sale);
            
            viewAllSales(null);
            clearSaleFields();
            JOptionPane.showMessageDialog(this, "Sale added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for IDs, Quantity Sold, and Total Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding sale: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ------------------ Method to Update a Sale ------------------
    private void updateSale(ActionEvent e) {
        if (selectedSaleId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a sale to update from the table!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int medicationsId = Integer.parseInt(medicationsIdField.getText().trim());
            int customerId = Integer.parseInt(customerIdField.getText().trim());
            int staffId = Integer.parseInt(staffIdField.getText().trim());
            int quantitySold = Integer.parseInt(quantitySoldField.getText().trim());
            double totalPrice = Double.parseDouble(totalPriceField.getText().trim());
            String paymentMethod = paymentMethodField.getText().trim();
            String saleDateText = saleDateField.getText().trim();
            
            if (paymentMethod.isEmpty() || saleDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Payment Method and Sale Date must be filled.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            LocalDate saleDate;
            try {
                saleDate = LocalDate.parse(saleDateText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Sale Date format! Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Sales sale = new Sales(selectedSaleId, medicationsId, customerId, staffId, quantitySold, totalPrice, paymentMethod, saleDate);
            SalesDAO salesDAO = new SalesDAO();
            salesDAO.updateSale(sale);
            
            viewAllSales(null);
            clearSaleFields();
            selectedSaleId = -1;
            JOptionPane.showMessageDialog(this, "Sale updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for IDs, Quantity Sold, and Total Price.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating sale: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // ------------------ Method to Delete a Sale ------------------
    private void deleteSale(ActionEvent e) {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a sale to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this sale?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            SalesDAO salesDAO = new SalesDAO();
            salesDAO.deleteSale(id);
            viewAllSales(null);
            JOptionPane.showMessageDialog(this, "Sale deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // ------------------ Method to View All Sales ------------------
    private void viewAllSales(ActionEvent e) {
        SalesDAO salesDAO = new SalesDAO();
        List<Sales> salesList = salesDAO.getAllSales();
        tableModel.setRowCount(0); // Clear existing table data
        for (Sales s : salesList) {
            // Table columns: ID, Medications ID, Customer ID, Staff ID, Quantity Sold, Total Price, Payment Method, Sale Date
            Object[] row = { s.getId(), s.getMedicationsId(), s.getCustomerId(), s.getStaffId(), s.getQuantitySold(), s.getTotalPrice(), s.getPaymentMethod(), s.getSaleDate().toString() };
            tableModel.addRow(row);
        }
    }
    
    // ------------------ Method to Load Selected Sale into Form for Update ------------------
    private void loadSelectedSale() {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a sale to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Retrieve selected sale's ID from the first column
        selectedSaleId = (int) tableModel.getValueAt(selectedRow, 0);
        // Populate form fields with values from the selected row.
        // For simplicity, we assume all required fields are present in the table.
        medicationsIdField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 1)));
        customerIdField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 2)));
        staffIdField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        quantitySoldField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 4)));
        totalPriceField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
        paymentMethodField.setText((String) tableModel.getValueAt(selectedRow, 6));
        saleDateField.setText((String) tableModel.getValueAt(selectedRow, 7));
    }
    
    // ------------------ Method to Clear Sales Form Fields ------------------
    private void clearSaleFields() {
        medicationsIdField.setText("");
        customerIdField.setText("");
        staffIdField.setText("");
        quantitySoldField.setText("");
        totalPriceField.setText("");
        paymentMethodField.setText("");
        saleDateField.setText("");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesForm::new);
    }
}

