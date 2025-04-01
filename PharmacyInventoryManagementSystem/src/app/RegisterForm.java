/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

/**
 *
 * @author hp
 */
import dao.UsersDAO;
import models.Users;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class RegisterForm extends JFrame {
    private JTextField firstNameField, lastNameField, emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public RegisterForm() {
        setTitle("User Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"admin", "user"});
        add(roleComboBox);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> handleRegister());
        add(registerButton);

        setVisible(true);
    }

    private void handleRegister() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String role = roleComboBox.getSelectedItem().toString();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Users newUser = new Users(0, firstName, lastName, email, password, role, LocalDate.now());

        boolean success = UsersDAO.addUser(newUser);
        if (success) {
            JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Close registration form
        } else {
            JOptionPane.showMessageDialog(this, "Error registering user. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

