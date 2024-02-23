package bookshop.shop.service.email;

public interface MailService {
    void send(String toEmail, String tempPassword);
}
