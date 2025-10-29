package com.example.receiptcollector.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "receipts")
@Data
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer receipt_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private ReceiptCategory category;

    @Column(length = 100)
    private String vendorname;

    @Column(nullable = false)
    private BigDecimal amount;

    private Date purchase_date;
    private Timestamp upload_date;

    private Integer fileId;

    @Column(length = 255)
    private String filePath;   // use camelCase

    @Column(columnDefinition = "TEXT")
    private String notes;

    // --- Getters and Setters (as provided) ---

    public Integer getReceipt_id() {
        return receipt_id;
    }

    public void setReceipt_id(Integer receipt_id) {
        this.receipt_id = receipt_id;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ReceiptCategory getCategory() {
        return category;
    }

    public void setCategory(ReceiptCategory category) {
        this.category = category;
    }

    public String getVendorname() {
        return vendorname;
    }

    public void setVendor_name(String vendor_name) {
        this.vendorname = vendor_name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(Date purchase_date) {
        this.purchase_date = purchase_date;
    }

    public Timestamp getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Timestamp upload_date) {
        this.upload_date = upload_date;
    }

    public String getFilePath() { return filePath; }

    public void setFilePath(String filePath) { this.filePath = filePath; }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
