package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.dto.PhotoRequestDto;
import hyu.dayPocket.repository.MemberRepository;
import hyu.dayPocket.service.FiPointService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
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
        fiPointService.addFiPoint(photoRequestDto);
        return "redirect:/admin/fiPointForm";
    }

}
