package bookshop.shop.service;

import bookshop.shop.dto.MemberRegisterRequest;
import bookshop.shop.dto.MemberResponseDto;
import bookshop.shop.exception.member.MemberPresentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void getMemberTest() {
        MemberRegisterRequest request = createRequest("testAccount", "test@gmail.com", "testPW123!","testPW123!", "테스트");
        Long savedId = memberService.createMember(request);

        MemberResponseDto findMember = memberService.getMember(savedId);
        assertThat(findMember.getAccount()).isEqualTo("testAccount");
    }

    @Test
    void memberCreateTest() {
        MemberRegisterRequest request = createRequest("testAccount", "test@gmail.com", "testPW123!","testPW123!", "테스트");
        Long savedId = memberService.createMember(request);
        assertThat(savedId).isNotNull();
    }

    @Test
    void whenDuplicateAccount_thenThrowException(){
        MemberRegisterRequest request = createRequest("testAccount", "test@gmail.com", "testPW123!","testPW123!", "테스트");
        memberService.createMember(request);
        assertThatThrownBy(()->memberService.createMember(request))
                .isInstanceOf(MemberPresentException.class)
                .hasMessageContaining("이미 가입된 ID입니다.");

    }

    @Test
    void whenInvalidAccountFormat_thenThrowException(){
        MemberRegisterRequest request = createRequest("한글계정", "test@gmail.com", "testPW123!","testPW123!", "테스트");
        assertThatThrownBy(()->memberService.createMember(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("잘못된 형식의 ID입니다.");

    }

    @Test
    void whenRegisteringInvalidEmail_thenThrowException(){
        MemberRegisterRequest request = createRequest("testAccount", "invalidEmail", "testPW123!","testPW123!", "테스트");
        assertThatThrownBy(()->memberService.createMember(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("이메일을 올바르게 입력해주세요.");

    }

    @Test
    void whenRegisteringInvalidPassword_thenThrowException(){
        MemberRegisterRequest request = createRequest("testAccount", "test@gmail.com", "testPW123","testPW123", "테스트");
        assertThatThrownBy(()->memberService.createMember(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호를 올바르게 입력해주세요.");
    }

    @Test
    void whenRegisteringNotMatchedPasswords_thenThrowException(){
        MemberRegisterRequest request = createRequest("testAccount", "test@gmail.com", "invalidPW","invalidPW", "테스트");
        assertThatThrownBy(()->memberService.createMember(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비밀번호를 올바르게 입력해주세요.");
    }


    private MemberRegisterRequest createRequest(String account, String email, String password, String passwordConfirm, String name){
        return new MemberRegisterRequest(account, email, password, passwordConfirm, name);
    }
}