package com.example.receiptcollector.controller;
<<<<<<< HEAD

import com.example.receiptcollector.model.Receipt;
import com.example.receiptcollector.model.ReceiptCategory;
import com.example.receiptcollector.model.User;
=======
import com.example.receiptcollector.model.Receipt;
import com.example.receiptcollector.model.ReceiptCategory;
import com.example.receiptcollector.model.User;
import com.example.receiptcollector.repository.UserRepository;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
import com.example.receiptcollector.service.ReceiptCategoryService;
import com.example.receiptcollector.service.ReceiptService;
import com.example.receiptcollector.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
<<<<<<< HEAD

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
=======
import java.io.File;
import java.io.IOException;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class MainPageController {
<<<<<<< HEAD
    @Autowired
    private UserService userService;
=======

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
    private final ReceiptCategoryService receiptCategoryService;
    private final ReceiptService receiptService;

    @Autowired
    public MainPageController(ReceiptCategoryService receiptCategoryService, ReceiptService receiptService) {
        this.receiptCategoryService = receiptCategoryService;
        this.receiptService = receiptService;
    }// <-- ADD this line

    @GetMapping("/")
    public String home() {
        return "index";
    }
<<<<<<< HEAD

=======
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
    // UPDATE your /users method so it adds all users to the model:
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "users";
    }

    @GetMapping("/user_register")
    public String userRegister(Model model) {
        model.addAttribute("user", new User());
        return "user_register";
    }

    @PostMapping("/users/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.addUser(user);
        return "redirect:/users";
    }

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", receiptCategoryService.listCategories());
        return "categories"; // This will render categories.html
    }
    @GetMapping("/category_add")
    public String categoryAdd(Model model) {
        model.addAttribute("category", new ReceiptCategory());
        return "category_add";
    }


    @PostMapping("/categories/add")
    public String addCategory(@ModelAttribute("category") ReceiptCategory category) {
        receiptCategoryService.addCategory(category);
        return "redirect:/categories";
    }


<<<<<<< HEAD



=======
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
    @GetMapping("/receipt_upload")
    public String receiptUpload(Model model) {
        model.addAttribute("receipt", new Receipt());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("categories", receiptCategoryService.listCategories());
        return "receipt_upload";
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        List<User> users = userService.listUsers(); // get all users
        List<ReceiptCategory> categories = receiptCategoryService.listCategories(); // get all categories
        model.addAttribute("users", users);
        model.addAttribute("categories", categories);
        model.addAttribute("receipt", new Receipt()); // your form backing bean
        return "receipt_upload"; // your HTML/Thymeleaf file name
    }
    @GetMapping("/view-receipts")
    public String viewReceipts(Model model) {
        List<Receipt> receipts = receiptService.getAllReceipts();
        model.addAttribute("receipts", receipts);
        return "receipts";
    }
    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer id) {
        // Use your service method that returns Optional<Receipt>
        Optional<Receipt> optionalReceipt = receiptService.getReceiptById(id);
        System.out.println("id=" + id);
        System.out.println("receipt=" + optionalReceipt);

        if (optionalReceipt.isEmpty() || optionalReceipt.get().getFilePath() == null) {
            return ResponseEntity.notFound().build();
        }

        Receipt receipt = optionalReceipt.get();
        Path path = Paths.get(receipt.getFilePath());
        try {
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }
            String fileName = path.getFileName().toString();
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/receipts")
    public String receipts(Model model) {
        List<Receipt> receipts = receiptService.listReceipts();
        model.addAttribute("receipts", receipts);
        return "receipts"; // This return string must match receipts.html
    }


    @PostMapping("/receipts/upload")
    public String uploadReceipt(@ModelAttribute Receipt receipt, @RequestParam("file") MultipartFile file) {
        String uploadDir = System.getProperty("user.dir") + "/uploads/";

        // Create directory if it doesn't exist
        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        // Save file to folder
        String fileName = file.getOriginalFilename();
        String filePath = uploadDir + fileName;
        try {
            file.transferTo(new File(filePath));
            receipt.setFilePath(filePath);  // Save file path to entity
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the error, maybe retain original upload page with message
        }
<<<<<<< HEAD

=======
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)
        // Save receipt entity to DB with updated filePath
        receiptService.saveReceipt(receipt, file);

        return "redirect:/receipts";
    }

<<<<<<< HEAD


=======
    @PutMapping("/users/edit/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute User user) {
        userService.updateUserById(id, user);
        return "redirect:/users";
    }


    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String showEditUserForm(@PathVariable Integer id, Model model) {
        User user = userService.listUsers()
                .stream()
                .filter(u -> u.getUserId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + id));
        model.addAttribute("user", user);
        return "user_edit";
    }

    // For editing categories
    @GetMapping("/categories/edit/{id}")
    public String showEditCategoryForm(@PathVariable Integer id, Model model) {
        ReceiptCategory category = receiptCategoryService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        model.addAttribute("category", category);
        return "category_edit";
    }

    @PostMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable Integer id, @ModelAttribute ReceiptCategory category) {
        receiptCategoryService.updateCategoryById(id, category);
        return "redirect:/categories";
    }

    // For deleting categories
    @PostMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable Integer id) {
        receiptCategoryService.deleteCategoryById(id);
        return "redirect:/categories";
    }

    @PostMapping("/receipts/delete/{id}")
    public String deleteReceipt(@PathVariable Integer id) {
        receiptService.deleteReceiptById(id);
        return "redirect:/receipts";
    }
>>>>>>> 4810415 (Add Maven Wrapper for Render deployment)

}
