/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author hp
 */
import java.util.Date;

public class Medicine {
    private int id;
    private String name;
    private String brandName;
    private String form;
    private int dosage;
    private int quantity;
    private double price;
    private Date expiryDate;
    private int supplierId;
    private boolean prescriptionRequired;
    
    //constructor
    public Medicine(int id, String name, String brandName, String form, int dosage,int quantity, double price, Date expiryDate, int supplierId, boolean prescriptionRequired){
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.form = form;
        this.dosage = dosage;
        this.quantity = quantity;
        this.price = price;
        this.expiryDate = expiryDate;
        this.supplierId = supplierId;
        this.prescriptionRequired = prescriptionRequired;
    }
    
    //Getters 
    public int getId(){ return id; }
    public String getName() { return name; }
    public String getBrandName() { return brandName; }
    public String getForm() { return form; }
    public int getDosage() { return dosage; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public Date getExpiryDate() { return expiryDate; }
    public int getSupplierId() { return supplierId; }
    public boolean isPrescriptionRequired(){ return prescriptionRequired; }
    
    
    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setBrandName(String brandName) { this.brandName = brandName; }
    public void setForm(String form) { this.form = form; }
    public void setDosage(int dosage) { this.dosage = dosage; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setExpiryDate(Date expiryDate) { this.expiryDate = expiryDate; }
    public void setSupplierId(int supplierId) { this.supplierId = supplierId; }
    public void setPrescriptionRequired(boolean prescriptionRequired) { this.prescriptionRequired = prescriptionRequired; }
    
    
}
