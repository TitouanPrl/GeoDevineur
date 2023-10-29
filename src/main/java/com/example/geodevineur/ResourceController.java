package com.example.geodevineur;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import org.springframework.core.io.Resource;

@RestController
public class ResourceController {

    @GetMapping("/resources/logo.png")
    public ResponseEntity<byte[]> getImage() throws IOException {
        Resource resource = new ClassPathResource("logo.png");
        try (InputStream inputStream = resource.getInputStream()) {
            byte[] data = inputStream.readAllBytes();
            return ResponseEntity.ok()
                    .header("Content-Type", "image/png")
                    .body(data);
        }
    }
}
