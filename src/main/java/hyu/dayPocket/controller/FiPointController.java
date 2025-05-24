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

@Controller
@RequiredArgsConstructor
public class FiPointController {

    private final FiPointService fiPointService;
    private final MemberRepository memberRepository;

    @GetMapping("/admin/page")
    public String showAdminForm(){
        return "admin/page";
    }

    @PostMapping("/admin/page")
    public String updateFiPoint(@ModelAttribute PhotoRequestDto photoRequestDto){
        fiPointService.updateFiPoint(photoRequestDto);
        return "redirect:/admin/page";
    }

}
