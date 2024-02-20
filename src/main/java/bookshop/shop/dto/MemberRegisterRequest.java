package bookshop.shop.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberRegisterRequest {

    private String account;
    private String email;
    private String password;
    private String passwordConfirm;
    private String name;

}
