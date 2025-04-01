/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author hp
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardAdmin extends JFrame {
    public DashboardAdmin() {
        setTitle("Admin Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(2, 3, 10, 10)); // Example layout with 2 rows, 3 columns
        
        // Buttons for each module
        JButton medicineButton = new JButton("Medicine");
        JButton customerButton = new JButton("Customer");
        JButton salesButton = new JButton("Sales");
        JButton staffButton = new JButton("Staff");
        JButton suppliersButton = new JButton("Suppliers");
        JButton usersButton = new JButton("Users");
        
        // Action Listeners to open the corresponding forms (assuming these forms exist)
        medicineButton.addActionListener((ActionEvent e) -> new MedicineForm().setVisible(true));
        customerButton.addActionListener((ActionEvent e) -> new CustomerForm().setVisible(true));
        salesButton.addActionListener((ActionEvent e) -> new SalesForm().setVisible(true));
        
        add(medicineButton);
        add(customerButton);
        add(salesButton);
        add(staffButton);
        add(suppliersButton);
        add(usersButton);
        
        // Logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginForm().setVisible(true);
        });
        add(logoutButton);
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(DashboardAdmin::new);
    }
}
