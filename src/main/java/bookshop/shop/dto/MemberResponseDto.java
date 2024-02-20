package bookshop.shop.dto;

import bookshop.shop.domain.Member;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {

    private String account;
    private String email;
    private String name;

    public MemberResponseDto (Member member){
        this.account = member.getAccount();
        this.email = member.getEmail();
        this.name = member.getName();
    }

}
