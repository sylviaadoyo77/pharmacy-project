/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 
 */
package app;

/**
 *
 * @author hp
 */
import dao.CustomerDAO;
import models.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CustomerForm extends JFrame {

    // Form fields
    private JTextField firstNameField, lastNameField, dobField, genderField,phoneField, emailField, addressField, cityField;
    
    //buttons
    private JButton addButton, updateButton, deleteButton, viewButton;
    
    // Table Components
    private JTable customerTable;
    private DefaultTableModel tableModel;
    
    // Variable to track selected customer ID for updates
    private int selectedCustomerId = -1;
    
    public CustomerForm() {
        setTitle("Customer Management");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
        
        //  Form Panel (North)
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5);
        
        // Row 0: First Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        formPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        firstNameField = new JTextField(20);
        formPanel.add(firstNameField, gbc);
        
        // Row 1: Last Name
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx = 1;
        lastNameField = new JTextField(20);
        formPanel.add(lastNameField, gbc);
        
        // Row 2: Date of Birth (YYYY-MM-DD)
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        dobField = new JTextField(20);
        formPanel.add(dobField, gbc);
        
        // Row 3: Gender
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        genderField = new JTextField(20);
        formPanel.add(genderField, gbc);
        
        // Row 4: phone
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        formPanel.add(phoneField, gbc);
        
        // Row 5: Email
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        formPanel.add(emailField, gbc);
        
        // Row 6: Address
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        formPanel.add(addressField, gbc);
        
        // Row 7: City
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("City:"), gbc);
        gbc.gridx = 1;
        cityField = new JTextField(20);
        formPanel.add(cityField, gbc);
        
        // Row 8: Add Button (to add a new customer)
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        addButton = new JButton("Add Customer");
        addButton.addActionListener(this::addCustomer);
        formPanel.add(addButton, gbc);
        
        add(formPanel, BorderLayout.NORTH);
        
        // Button Panel (Center) for Update, Delete, and View All
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        viewButton = new JButton("View All");
        
        // For update: load selected customer then update
        updateButton.addActionListener(e -> {
            loadSelectedCustomer();
            updateCustomer(e);
        });
        deleteButton.addActionListener(this::deleteCustomer);
        viewButton.addActionListener(this::viewAllCustomers);
        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // Table Panel (South)
        tableModel = new DefaultTableModel(new String[]{"ID", "First Name", "Last Name", "DOB", "Gender", "City"}, 0);
        customerTable = new JTable(tableModel);
        add(new JScrollPane(customerTable), BorderLayout.SOUTH);
        
        // Load existing customers
        viewAllCustomers(null);
        
        setVisible(true);
    }
    
    //Method to Add a Customer
    private void addCustomer(ActionEvent e) {
        try {
            // Retrieve input values
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String dobText = dobField.getText().trim();
            String gender = genderField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            
            // Validate required fields
            if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty() || gender.isEmpty() || phone.isEmpty() || email.isEmpty() || address.isEmpty() || city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //convert date from string to localdate
            LocalDate dob;
            try{
                dob = LocalDate.parse(dobText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create Customer object (assuming id=0 for new record)
            Customer customer = new Customer(0, firstName, lastName, dob, gender, phone, email, address, city);
            
            // Save to database
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.addCustomer(customer);
            
            // Refresh table and clear fields
            viewAllCustomers(null);
            clearFields();
            JOptionPane.showMessageDialog(this, "Customer added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to Update a Customer
    private void updateCustomer(ActionEvent e) {
        if (selectedCustomerId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update from the table!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String dobText = dobField.getText().trim();
            String gender = genderField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();
            String address = addressField.getText().trim();
            String city = cityField.getText().trim();
            
            if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty() || gender.isEmpty() ||phone.isEmpty() || email.isEmpty() || address.isEmpty() || city.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //convert date from string to localdate
            LocalDate dob;
            try{
                dob = LocalDate.parse(dobText);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date format! Use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = new Customer(selectedCustomerId, firstName, lastName, dob, gender,phone, email, address, city);
            customerDAO.updateCustomer(customer);
            
            viewAllCustomers(null);
            clearFields();
            selectedCustomerId = -1;
            JOptionPane.showMessageDialog(this, "Customer updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating customer: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //  Method to Delete a Customer 
    private void deleteCustomer(ActionEvent e) {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            CustomerDAO customerDAO = new CustomerDAO();
            customerDAO.deleteCustomer(id);
            viewAllCustomers(null);
            JOptionPane.showMessageDialog(this, "Customer deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Method to View All Customers 
    private void viewAllCustomers(ActionEvent e) {
        CustomerDAO customerDAO = new CustomerDAO();
        List<Customer> customers = customerDAO.getAllCustomers();
        tableModel.setRowCount(0);
        for (Customer c : customers) {
            Object[] row = {c.getId(), c.getFirstName(), c.getLastName(), c.getDateOfBirth(), c.getGender(), c.getCity()};
            tableModel.addRow(row);
        }
    }
    
    // Method to Load Selected Customer into Form for Update
    
    private void loadSelectedCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        selectedCustomerId = (int) tableModel.getValueAt(selectedRow, 0);
        firstNameField.setText((String) tableModel.getValueAt(selectedRow, 1));
        lastNameField.setText((String) tableModel.getValueAt(selectedRow, 2));
        dobField.setText((String) tableModel.getValueAt(selectedRow, 3));
        genderField.setText((String) tableModel.getValueAt(selectedRow, 4));
        cityField.setText((String) tableModel.getValueAt(selectedRow, 5));
        //other fields like phone , email, address might not be on the table
        //if needed you may retrieve them from the database or leave them blank.
    }
    
    //  Method to Clear Form Fields
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        dobField.setText("");
        genderField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
        cityField.setText("");
    }
    //main method to run the form
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CustomerForm::new);
    }
}

