package com.example.personalcloud;

import com.example.personalcloud.service.FileSystemStorageService;
import com.example.personalcloud.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersonalCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalCloudApplication.class, args);
    }

    @Bean
    StorageService storageService() {
        return new FileSystemStorageService();
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
}
