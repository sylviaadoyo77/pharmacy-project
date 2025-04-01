/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

/**
 *
 * @author hp
 */
import dao.UsersDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import models.Users;
import app.DashboardAdmin;
import app.DashboardUser;


public class LoginForm extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("User Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::handleLogin);
        add(loginButton);
        
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> new RegisterForm());
        add(registerButton);


        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        Users user = UsersDAO.authenticateUser(email, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login successful! Welcome, " + user.getFirstName());

            // Redirect based on role
            if (user.getRole().equalsIgnoreCase("admin")) {
                new DashboardAdmin();
            } else {
                new DashboardUser();
            }
            dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        
    }

    public static void main(String[] args) {
        new LoginForm();
    }
    
}

