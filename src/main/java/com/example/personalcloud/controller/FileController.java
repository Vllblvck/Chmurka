package com.example.personalcloud.controller;

import com.example.personalcloud.config.ApiRoutes;
import com.example.personalcloud.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

   private final StorageService storageService;

   @Autowired
   public FileController(StorageService storageService) {
      this.storageService = storageService;
   }

   //TODO test if spring automatically validates files for null
   @PostMapping(ApiRoutes.UPLOAD_FILE)
   public void uploadFile(@RequestParam("files") MultipartFile[] files) {
       storageService.store(files);
   }
}
