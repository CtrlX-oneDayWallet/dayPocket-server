package hyu.dayPocket.controller;


import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.AssetDto;
import hyu.dayPocket.dto.HomeDto;
import hyu.dayPocket.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    public final MemberService memberService;

    @GetMapping("dayPocket/main/home")
    public ResponseEntity<HomeDto> getHome(@AuthenticationPrincipal CustomUserDetails userDetails ) {
        Member member = userDetails.getMember();
        HomeDto homeDto = memberService.getHomeDto(member);
        return new ResponseEntity<>(homeDto, HttpStatus.OK);
    }

    @GetMapping("/dayPocket/main/asset")
    public ResponseEntity<AssetDto> getAsset(@AuthenticationPrincipal CustomUserDetails userDetails){
        Member member = userDetails.getMember();
        AssetDto assetDto = memberService.getAssetDto(member);
        return new ResponseEntity<>(assetDto, HttpStatus.OK);
    }

}
