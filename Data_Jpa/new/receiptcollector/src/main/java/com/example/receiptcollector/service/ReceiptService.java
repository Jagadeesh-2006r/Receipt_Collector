package com.example.receiptcollector.service;

import com.example.receiptcollector.model.Receipt;
import com.example.receiptcollector.repository.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    public List<Receipt> listReceipts() {
        return receiptRepository.findAll();
    }

    public Receipt saveReceipt(Receipt receipt, MultipartFile file) {
        return receiptRepository.save(receipt);
    }

    public List<Receipt> getReceiptsByUser(Integer userId) {
        return receiptRepository.findByUser_UserId(userId);  // corrected method name here
    }

    public List<Receipt> getReceiptsByCategory(Integer categoryId) {
        return receiptRepository.findByCategory_CategoryId(categoryId);  // corrected method name here
    }


    public Optional<Receipt> getReceiptById(Integer receipt_id) {
        return receiptRepository.findById(receipt_id);
    }


    public void save(Receipt receipt) {
        receiptRepository.save(receipt);
    }

    public List<Receipt> getAllReceipts() {
        return receiptRepository.findAll();
    }


    public void deleteReceiptById(Integer id) {
        receiptRepository.deleteById(id);
    }
}
