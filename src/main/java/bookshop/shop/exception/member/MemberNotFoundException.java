package bookshop.shop.exception.member;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(String message)  {
        super(message);
    }

    public MemberNotFoundException(){
        super("존재하지 않는 회원입니다.");
    }
}