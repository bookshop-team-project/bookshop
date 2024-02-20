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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String account;
    private String password;
    private String name;
    private String email;

    public static Member createMember(MemberRegisterRequest request) {
        Member member = new Member();
        member.password = request.getAccount();
        member.account = request.getAccount();
        member.name = request.getName();
        member.email = request.getAccount();
        return member;
    }

    public void encodePassword(String encodedPassword){
        password = encodedPassword;
    }

}
