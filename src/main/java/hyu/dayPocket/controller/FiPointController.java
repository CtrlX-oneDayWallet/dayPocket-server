package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.dto.PhotoRequestDto;
import hyu.dayPocket.dto.StatusDto;
import hyu.dayPocket.enums.ChallengeType;
import hyu.dayPocket.enums.PointPaymentState;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.service.FiPointService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FiPointController {

    private final FiPointService fiPointService;
    private final MemberRepository memberRepository;

    @GetMapping
    public String showAdminForm(Model model){
        model.addAttribute("members", memberRepository.findAll() );
        return "admin/fiPointForm";
    }

    @PostMapping("/admin/fiPointForm")
    public String updateFiPoint(@ModelAttribute PhotoRequestDto photoRequestDto){
        fiPointService.updateFiPoint(photoRequestDto);
        return "redirect:/admin/fiPointForm";
    }

    @GetMapping("/dayPocket/challenge/status")
    public ResponseEntity<StatusDto> getLatestStatus ( @AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestParam ChallengeType type){
        StatusDto status = fiPointService.statusDto(userDetails.getMember(), type);
        return ResponseEntity.ok(status);
    }


}
