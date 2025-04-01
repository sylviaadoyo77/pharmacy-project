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

public class Suppliers {
    private int supplierId;
    private String supplierName;
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String address;
    private String city;
    private String country;
    private LocalDate createdAt;

    // Constructor
    public Suppliers(int supplierId, String supplierName, String contactPerson, String phoneNumber, String email, String address, String city, String country, LocalDate createdAt) {
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.contactPerson = contactPerson;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.createdAt = createdAt;
    }

    // Empty Constructor
    public Suppliers() {}

    // Getters and Setters
    public int getSupplierId() { return supplierId; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }

    // Override toString for debugging
    @Override
    public String toString() {
        return "Suppliers{" +
                "supplierId=" + supplierId +
                ", supplierName='" + supplierName + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

