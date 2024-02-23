package bookshop.shop.domain;

import bookshop.shop.dto.MemberRegisterRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String account;
    private String password;
    private String name;
    private String email;

    public static Member createMember(MemberRegisterRequest request) {
        Member member = new Member();
        member.password = request.getPassword();
        member.account = request.getAccount();
        member.name = request.getName();
        member.email = request.getEmail();
        return member;
    }

    public void encodePassword(String encodedPassword){
        password = encodedPassword;
    }

}
