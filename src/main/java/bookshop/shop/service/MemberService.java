package bookshop.shop.service;

import bookshop.shop.domain.Member;
import bookshop.shop.dto.MemberRegisterRequest;
import bookshop.shop.dto.MemberResponseDto;
import bookshop.shop.exception.member.MemberPresentException;
import bookshop.shop.repository.MemberRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new MemberResponseDto(member);
    }


    public Long createMember(MemberRegisterRequest request) {
        validateForm(request);
        Member member = Member.createMember(request);
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.encodePassword(encodedPassword);
        memberRepository.save(member);

        return member.getId();
    }


    private void validateForm(MemberRegisterRequest request) {
        validateAccount(request.getAccount());
        validateEmail(request.getEmail());
        validatePassword(request.getPassword(), request.getPasswordConfirm());

    }

    private void validateAccount(String account) {
        validateIsExistsAccount(account);
        validateAccountFormat(account);
    }

    private void validateIsExistsAccount(String account) {
        boolean isExists = memberRepository.existsByAccount(account);
        if (isExists == true) {
            throw new MemberPresentException("이미 가입된 ID입니다.");
        }
    }

    private void validateAccountFormat(String account) {
        log.info("account = {} ",  account);
        if (account == null || !account.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("잘못된 형식의 ID입니다.");
        }
    }

    private void validatePassword(String password, String passwordConfirm) {
        if (password == null || passwordConfirm == null) {
            throw new IllegalArgumentException("비밀번호를 올바르게 입력해주세요.");
        }
        validatePasswordFormat(password);
        validatePasswordSame(password, passwordConfirm);
    }

    private void validatePasswordSame(String password, String passwordConfirm) {
        if (!password.equals(passwordConfirm)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validatePasswordFormat(String password) {
        // 비밀번호 형식을 나타내는 정규표현식
        String passwordPattern = "^(?=.*[@$!%*#?&])[a-zA-Z0-9@$!%*#?&]{8,16}$";
        // 정규표현식을 사용하여 비밀번호 형식을 검사
        if (!Pattern.compile(passwordPattern).matcher(password).matches()) {
            throw new IllegalArgumentException("비밀번호를 올바르게 입력해주세요.");
        }
    }


    private void validateEmail(String email) {
        validateEmailNotEmpty(email);
        validateEmailFormat(email);
    }

    private void validateEmailNotEmpty(String email) {
        if (email == null || email.trim().equals("")) {
            throw new IllegalArgumentException("이메일을 올바르게 입력해주세요.");
        }
    }

    private void validateEmailFormat(String email) {
        // 이메일 형식을 나타내는 정규표현식
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // 정규표현식을 사용하여 이메일 형식을 검사
        if (!Pattern.compile(emailPattern).matcher(email).matches()) {
            throw new IllegalArgumentException("이메일을 올바르게 입력해주세요.");
        }
    }
}
