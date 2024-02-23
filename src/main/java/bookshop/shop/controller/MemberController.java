package bookshop.shop.controller;

import bookshop.shop.dto.MemberRegisterRequest;
import bookshop.shop.dto.MemberResponseDto;
import bookshop.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info/{memberId}")
    public ResponseEntity<MemberResponseDto> memberDetails(@PathVariable Long memberId) {
        MemberResponseDto memberResponseDto = memberService.getMember(memberId);
        return ResponseEntity.ok(memberResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> memberCreate(@RequestBody MemberRegisterRequest request){
        memberService.createMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 되었습니다");
    }

    @GetMapping("/find-id")
    public ResponseEntity<Map<String, String>> findId(@RequestParam String email, @RequestParam String name){
        String maskedAccount = memberService.getAccount(email, name);
        Map<String, String> response = new HashMap<>();
        response.put("maskedAccount", maskedAccount);
        return ResponseEntity.ok(response);
    }


}
