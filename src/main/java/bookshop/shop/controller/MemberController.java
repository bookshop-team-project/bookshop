package bookshop.shop.controller;

import bookshop.shop.dto.MemberRegisterRequest;
import bookshop.shop.dto.MemberResponseDto;
import bookshop.shop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok("회원 가입 되었습니다");
    }
}
