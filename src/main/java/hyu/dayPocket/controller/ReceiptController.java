package hyu.dayPocket.controller;

import hyu.dayPocket.config.CustomUserDetails;
import hyu.dayPocket.domain.Member;
import hyu.dayPocket.dto.TargetReceiptFiPointDto;
import hyu.dayPocket.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReceiptController {

    public final ReceiptService receiptService;
    @PostMapping("/dayPocket/receipt/target")
    public ResponseEntity<TargetReceiptFiPointDto> createTargetReceiptFiPoint(@RequestBody Integer targetReceiptFiPoint,
                                                                              @AuthenticationPrincipal CustomUserDetails userDetails){
         Member member =  userDetails.getMember();
         receiptService.setTargetReceiptFiPoint(member, targetReceiptFiPoint);
         return ResponseEntity.ok().build();
    }


}
