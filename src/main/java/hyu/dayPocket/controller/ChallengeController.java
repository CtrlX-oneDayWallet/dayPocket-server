package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.dto.StatusDto;
import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.service.FiPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChallengeController {

    private final FiPointService fiPointService;

    @GetMapping("/dayPocket/challenge/status")
    public ResponseEntity<StatusDto> getLatestStatus (@AuthenticationPrincipal CustomUserDetails userDetails,
                                                      @RequestParam ChallengeType type){
        StatusDto status = fiPointService.statusDto(userDetails.getMember(), type);
        return ResponseEntity.ok(status);
    }
}
