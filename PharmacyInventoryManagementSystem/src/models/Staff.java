/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author hp
 */

import java.time.LocalDate;

public class Staff {
    private int id;
    private String firstName;
    private String lastName;
    private String role;
    private String phoneNumber;
    private String email;
    private LocalDate hireDate;
    private double salary;
    private int userId; // Foreign key reference to users table

    // Constructor
    public Staff(int id, String firstName, String lastName, String role, String phoneNumber, String email, LocalDate hireDate, double salary, int userId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.userId = userId;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "Staff{id=" + id + ", firstName='" + firstName + "', lastName='" + lastName + "', role='" + role + 
               "', phone='" + phoneNumber + "', email='" + email + "', hireDate=" + hireDate + ", salary=" + salary + 
               ", userId=" + userId + "}";
    }
}


