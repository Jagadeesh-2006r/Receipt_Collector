package com.example.receiptcollector.repository;

import com.example.receiptcollector.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer> {
    List<Receipt> findByUser_UserId(Integer userId);      // userId matches Java property in User class
    List<Receipt> findByCategory_CategoryId(Integer categoryId);  // categoryId matches Java property in ReceiptCategory
    List<Receipt> findByUserUserId(Integer userId);
    void deleteByUserUserId(Integer userId);

}

