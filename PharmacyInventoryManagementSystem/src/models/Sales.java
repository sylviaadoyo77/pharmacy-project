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

public class Sales {
    private int id;
    private int medicationsId;
    private int customerId;
    private int staffId;
    private int quantitySold;
    private double totalPrice;
    private String paymentMethod;
    private LocalDate saleDate;
    
     public Sales() {
    }

    // Constructor
    public Sales(int id, int medicationsId, int customerId, int staffId, int quantitySold, double totalPrice, String paymentMethod, LocalDate saleDate) {
        this.id = id;
        this.medicationsId = medicationsId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.saleDate = saleDate;
    }

    // Constructors
    public Sales(int medicationsId, int customerId, int staffId, int quantitySold, double totalPrice, String paymentMethod, LocalDate saleDate) {
        this.medicationsId = medicationsId;
        this.customerId = customerId;
        this.staffId = staffId;
        this.quantitySold = quantitySold;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.saleDate = saleDate;
    }

    // Getters and Setters
    public int getId() { return id; }
    public int getMedicationsId() { return medicationsId; }
    public int getCustomerId() { return customerId; }
    public int getStaffId() { return staffId; }
    public int getQuantitySold() { return quantitySold; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentMethod() { return paymentMethod; }
    public LocalDate getSaleDate() { return saleDate; }

    public void setId(int id) { this.id = id; }
    public void setMedicationsId(int medicationsId) { this.medicationsId = medicationsId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
    public void setQuantitySold(int quantitySold) { this.quantitySold = quantitySold; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public void setSaleDate(LocalDate saleDate) { this.saleDate = saleDate; }

     @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", medicationsId=" + medicationsId +
                ", customerId=" + customerId +
                ", staffId=" + staffId +
                ", quantitySold=" + quantitySold +
                ", totalPrice=" + totalPrice +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", saleDate=" + saleDate +
                '}';
    }
}

