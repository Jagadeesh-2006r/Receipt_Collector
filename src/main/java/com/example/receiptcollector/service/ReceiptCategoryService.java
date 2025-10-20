package com.example.receiptcollector.service;

import com.example.receiptcollector.model.ReceiptCategory;
import com.example.receiptcollector.repository.ReceiptCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptCategoryService {
    @Autowired
    private ReceiptCategoryRepository categoryRepository;

    public ReceiptCategory addCategory(ReceiptCategory category) {
        return categoryRepository.save(category);
    }

    public List<ReceiptCategory> listCategories() {
        return categoryRepository.findAll();
    }
}
