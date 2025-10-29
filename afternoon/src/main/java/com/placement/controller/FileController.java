package com.placement.controller;

import com.placement.entity.User;
import com.placement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class FileController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/download/resume/{userId}")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long userId) {
        try {
            Optional<User> userOpt = userService.findById(userId);
            if (userOpt.isEmpty() || userOpt.get().getResumePath() == null) {
                return ResponseEntity.notFound().build();
            }
            
            User user = userOpt.get();
            String resumePath = user.getResumePath();
            
            // Convert web path to file system path
            String fileName = resumePath.substring(resumePath.lastIndexOf("/") + 1);
            Path filePath = Paths.get("src/main/resources/static" + resumePath);
            
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF)
                        .header(HttpHeaders.CONTENT_DISPOSITION, 
                               "attachment; filename=\"" + user.getName() + "_resume.pdf\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}