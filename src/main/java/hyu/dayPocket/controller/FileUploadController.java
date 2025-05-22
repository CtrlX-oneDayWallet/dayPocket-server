package hyu.dayPocket.controller;

import hyu.dayPocket.service.AzureBlobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final AzureBlobService azureBlobService;


    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        azureBlobService.uploadFile(file);
        return "Upload successful";
    }
}
