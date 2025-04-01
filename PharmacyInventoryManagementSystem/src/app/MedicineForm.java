/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

/**
 *
 * @author hp
 */
import dao.MedicineDAO;
import models.Medicine;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MedicineForm extends JFrame {
    
    //form components and table
    private JComboBox<String> supplierComboBox;
    private JTextField nameField, brandNameField, quantityField, dosageField, expiryField, priceField;
    private JCheckBox prescriptionCheckBox;
    private JTable medicineTable;
    private DefaultTableModel tableModel;
    
    
    //variable to store the selected medicine ID when updating a record
    private int selectedMedicineId = -1;
    
    //constructor : set up the UI
    public MedicineForm() {
        setTitle("Medicine Inventory Management");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10,10));

        // Form Panel(NORTH) contains input fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5,5,5,5); //padding for better spacing

        // Medicine Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.3;
        formPanel.add(new JLabel("Medicine Name:"), gbc);
        gbc.gridx = 1; gbc.weightx=0.7;
        nameField = new JTextField(20);
        formPanel.add(nameField, gbc);

        // Manufacturer (brandname)
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Manufacturer:"), gbc);
        gbc.gridx = 1;
        brandNameField = new JTextField(20);
        formPanel.add(brandNameField, gbc);

        // Quantity
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        quantityField = new JTextField(20);
        formPanel.add(quantityField, gbc);

        // Expiry Date
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Expiry Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1;
        expiryField = new JTextField(20);
        formPanel.add(expiryField, gbc);

        // Price
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        priceField = new JTextField(20);
        formPanel.add(priceField, gbc);

        // Dosage
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Dosage (mg):"), gbc);
        gbc.gridx = 1;
        dosageField = new JTextField(20);
        formPanel.add(dosageField, gbc);

        // Prescription Checkbox
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Prescription Required:"), gbc);
        gbc.gridx = 1;
        prescriptionCheckBox = new JCheckBox();
        formPanel.add(prescriptionCheckBox, gbc);

        // Supplier Dropdown
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Supplier:"), gbc);
        gbc.gridx = 1;
        supplierComboBox = new JComboBox<>();
        formPanel.add(supplierComboBox, gbc);
        loadSuppliers(); //populate supplier combo box

        // Submit Button(for adding a new medicine)
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton submitButton = new JButton("Add Medicine");
        submitButton.addActionListener(this::addMedicine);
        formPanel.add(submitButton, gbc);

        mainPanel.add(formPanel, BorderLayout.NORTH);
        
        //Center panel:buttons panel for update, delete and view
        //create panel to hold buttons and table vertically
        JPanel centerPanel = new JPanel(new BorderLayout(5,5));
        
        
        //buttons panel for update, delete and view all
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton viewButton = new JButton("View All");
        
        //for update: first load selected row data, then update
        updateButton.addActionListener(e -> {
            loadSelectedMedicine(); //load selected row data into form fields
            updateMedicine(e); //update the record with new data
        });
        deleteButton.addActionListener(this::deleteMedicine);
        viewButton.addActionListener(this::viewAllMedications);
        
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewButton);
        
        //Add buttonpanel to the north of the mainpanel
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        

        // Table Panel(south): displays medicine records
        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Manufacturer", "Qty", "Expiry", "Price"}, 0);
        medicineTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(medicineTable);
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        //add centerpanel to main panel's center region
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        //add main panel to the frame
        add(mainPanel, BorderLayout.CENTER);

        viewAllMedications(null);

        setVisible(true);
    }
    
    //method to load supplier names from the database
    private void loadSuppliers(){
        try {
            MedicineDAO medicineDAO = new MedicineDAO();
            List<String> suppliers = medicineDAO.getSupplierList(); //retrives supplier names
            supplierComboBox.removeAllItems(); //clear any existing items
            for (String supplier : suppliers) {
                supplierComboBox.addItem(supplier);
            }
        }catch (Exception ex) {
            System.out.println("Error loading suppliers: " + ex.getMessage());
        }
    }
    //Method to add a medicine
    private void addMedicine(ActionEvent e) {
        try {
            
            //validate that numeric fields are not empty
            if (quantityField.getText().trim().isEmpty() ||
                dosageField.getText().trim().isEmpty() ||
                priceField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Quantity, Dosage, and Price fields cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            //convert numeric fields safely
            int quantity = Integer.parseInt(quantityField.getText().trim());
            int dosage = Integer.parseInt(dosageField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            
            // Retrieve other input values
            String name = nameField.getText().trim();
            String manufacturer = brandNameField.getText().trim();
            String expiryDateText = expiryField.getText().trim();
            boolean prescriptionRequired = prescriptionCheckBox.isSelected();
            String selectedSupplier = (String) supplierComboBox.getSelectedItem();


            // 4. Validate required fields
            if (name.isEmpty() || manufacturer.isEmpty() || expiryDateText.isEmpty() || selectedSupplier == null) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. Retrieve Supplier ID
            MedicineDAO medicineDAO = new MedicineDAO();
            int supplierId = medicineDAO.getSupplierIdByName(selectedSupplier);
            if (supplierId == -1) {
                JOptionPane.showMessageDialog(this, "Supplier not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 6. Create Medicine Object(assuming default form as "tablet"
            Medicine medicine = new Medicine(0, name, manufacturer, "Tablet", dosage, quantity, price, java.sql.Date.valueOf(expiryDateText), supplierId, prescriptionRequired);

            // 7. Save to Database
            medicineDAO.addMedicine(medicine);

            // 8. Refresh Table
            viewAllMedications(null);
            clearFields();
            JOptionPane.showMessageDialog(this, "Medicine added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity, Dosage, and Price must be valid numbers.", "Input Error",JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error adding medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    //Method to delete a medicine
    private void deleteMedicine(ActionEvent e) {
        int selectedRow = medicineTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int id = (int) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this medicine?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            MedicineDAO medicineDAO = new MedicineDAO();
            medicineDAO.deleteMedicine(id);
            viewAllMedications(null);
            JOptionPane.showMessageDialog(this, "Medicine deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void updateMedicine(ActionEvent e) {
        if (selectedMedicineId == -1) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to update from the table!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String name = nameField.getText().trim();
            String manufacturer = brandNameField.getText().trim();
            int quantity = Integer.parseInt(quantityField.getText().trim());
            int dosage = Integer.parseInt(dosageField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            String expiryDateText = expiryField.getText().trim();
            boolean prescriptionRequired = prescriptionCheckBox.isSelected();
            String selectedSupplier = (String) supplierComboBox.getSelectedItem();

            MedicineDAO medicineDAO = new MedicineDAO();
            int supplierId = medicineDAO.getSupplierIdByName(selectedSupplier);
            if (supplierId == -1) {
                JOptionPane.showMessageDialog(this, "Supplier not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (name.isEmpty() || manufacturer.isEmpty() || expiryDateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create updated Medicine object using the selectedMedicineId
            Medicine medicine = new Medicine(selectedMedicineId, name, manufacturer, "Tablet", dosage, quantity, price, java.sql.Date.valueOf(expiryDateText), supplierId, prescriptionRequired);
            medicineDAO.updateMedicine(medicine);
            viewAllMedications(null);
            clearFields();
            selectedMedicineId = -1;
            JOptionPane.showMessageDialog(this, "Medicine updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity, Dosage, and Price must be valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error updating medicine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    //Method to view all medicines
    private void viewAllMedications(ActionEvent e) {
        MedicineDAO medicineDAO = new MedicineDAO();
        List<Medicine> medications = medicineDAO.getAllMedications();
        tableModel.setRowCount(0); //clear table before adding rows
        for (Medicine m : medications) {
            //assuming columns: ID, Name, manufacturer, quantity, expiry as string, price
            Object[] row = {m.getId(), m.getName(), m.getBrandName(),m.getQuantity(), m.getExpiryDate().toString(),m.getPrice()};
            tableModel.addRow(row);
        }
    }
    
    //method to load selected medicine into form fields for update
    private void loadSelectedMedicine(){
        int selectedRow = medicineTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a medicine to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
            
        }
        //retrieve the ID from the first column for the selected row
        selectedMedicineId = (int) tableModel.getValueAt(selectedRow, 0);
        //populate form fields with values from the selected row
        nameField.setText((String) tableModel.getValueAt(selectedRow, 1));
        brandNameField.setText((String) tableModel.getValueAt(selectedRow, 2));
        quantityField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 3)));
        expiryField.setText((String) tableModel.getValueAt(selectedRow, 4)); //assumes expiry date stored as string
        priceField.setText(String.valueOf(tableModel.getValueAt(selectedRow, 5)));
        dosageField.setText("0");
        prescriptionCheckBox.setSelected(false);
    }
    //method to clear all form fields
    private void clearFields() {
        nameField.setText("");
        brandNameField.setText("");
        quantityField.setText("");
        priceField.setText("");
        expiryField.setText("");
        dosageField.setText("");
        prescriptionCheckBox.setSelected(false);
        if (supplierComboBox.getItemCount() > 0){
            supplierComboBox.setSelectedIndex(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MedicineForm::new);
    }
    
    
    

}
