package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.dto.ClientChallengeType;
import hyu.dayPocket.service.AzureBlobService;
import hyu.dayPocket.service.FiPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final AzureBlobService azureBlobService;
    private final FiPointService fiPointService;


    @PostMapping("/upload")
    public String upload(@RequestPart("file") MultipartFile file,
                         @RequestPart("challenge") String challenge,
                         @AuthenticationPrincipal CustomUserDetails userDetails) throws IOException {
        azureBlobService.uploadFile(file);
        fiPointService.addFiPointHistory(challenge, userDetails.getMember());
        return "Upload successful";
    }
}
