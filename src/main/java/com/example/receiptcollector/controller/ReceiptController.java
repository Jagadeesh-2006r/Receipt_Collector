package com.example.receiptcollector.controller;

import com.example.receiptcollector.model.Receipt;
import com.example.receiptcollector.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipts/upload")
    public String handleUpload(@ModelAttribute("receipt") Receipt receipt,
                               @RequestParam("file") MultipartFile file) {
        receiptService.saveReceipt(receipt, file);
        return "redirect:/receipts";
    }
    @GetMapping("/receipts")
    public String receipts(Model model) {
        model.addAttribute("receipts", receiptService.listReceipts());
        return "receipts"; // This loads receipts.html
    }



    @GetMapping("/user/{user_id}")
    public List<Receipt> getReceiptsByUser(@PathVariable Integer user_id) {
        return receiptService.getReceiptsByUser(user_id);
    }

    @GetMapping("/{receipt_id}")
    public Optional<Receipt> getReceiptById(@PathVariable Integer receipt_id) {
        return receiptService.getReceiptById(receipt_id);
    }

    @GetMapping("/category/{category_id}")
    public List<Receipt> getReceiptsByCategory(@PathVariable Integer category_id) {
        return receiptService.getReceiptsByCategory(category_id);
    }

    @GetMapping("/view-receipts")
    public String viewReceipts(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        return "receipts";
    }
    
    @PostMapping("/upload-receipt")
    public String uploadReceipt(@ModelAttribute Receipt receipt, Model model) {
        receiptService.save(receipt); // Save uploaded receipt details
        return "redirect:/receipts";
    }




}
