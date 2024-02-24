package bookshop.shop.service.email;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    void mailSendTest(){
        mailService.send("treesheep@naver.com", "안녕");
    }
}