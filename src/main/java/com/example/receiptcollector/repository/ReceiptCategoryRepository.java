package com.example.receiptcollector.repository;


import com.example.receiptcollector.model.ReceiptCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ReceiptCategoryRepository extends JpaRepository<ReceiptCategory, Integer> {
    @Service
    public class ReceiptCategoryService {
        @Autowired
        private ReceiptCategoryRepository categoryRepository;

        public void addCategory(ReceiptCategory category) {
            categoryRepository.save(category);
        }

        public List<ReceiptCategory> listCategories() {
            return categoryRepository.findAll();
        }
    }

}
