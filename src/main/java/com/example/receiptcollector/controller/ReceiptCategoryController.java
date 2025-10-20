package com.example.receiptcollector.controller;

import com.example.receiptcollector.model.ReceiptCategory;
import com.example.receiptcollector.service.ReceiptCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class ReceiptCategoryController {
    @Autowired
    private ReceiptCategoryService categoryService;

    @PostMapping
    public ReceiptCategory addCategory(@RequestBody ReceiptCategory category) {
        return categoryService.addCategory(category);
    }

    @GetMapping
    public List<ReceiptCategory> getAllCategories() {
        return categoryService.listCategories();
    }
}
