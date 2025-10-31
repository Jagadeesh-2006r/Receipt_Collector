package com.example.receiptcollector.service;
<<<<<<< HEAD

=======
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
import com.example.receiptcollector.model.ReceiptCategory;
import com.example.receiptcollector.repository.ReceiptCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD

import java.util.List;
=======
import java.util.List;
import java.util.Optional;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)

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
<<<<<<< HEAD
=======

    public Optional<ReceiptCategory> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public void updateCategoryById(Integer id, ReceiptCategory updatedCategory) {
        Optional<ReceiptCategory> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            ReceiptCategory category = optionalCategory.get();
            category.setName(updatedCategory.getName());
            category.setDescription(updatedCategory.getDescription());
            categoryRepository.save(category);
        }
    }

    public void deleteCategoryById(Integer id) {
        categoryRepository.deleteById(id);
    }
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
}
