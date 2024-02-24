package bookshop.shop.controller;

import bookshop.shop.domain.Member;
import bookshop.shop.domain.Review;
import bookshop.shop.dto.MemberRegisterRequest;
import bookshop.shop.dto.response.ReviewResponseDto;
import bookshop.shop.repository.GetReviewRepository;
import bookshop.shop.service.getReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class ReviewControllerTest {
    @Mock
    private GetReviewRepository reviewRepository;

    @InjectMocks
    private getReviewService reviewService;


    @Test
    public void getReviewTest() {
        /*
            1. 리퀘스트 정의
            2. 결과값 제대로 넘어오는지 확인
            3.
        */
        MemberRegisterRequest request1 = createRequest("test", "test@gmail.com", "testPW123!", "testPW123!", "테스트");
        MemberRegisterRequest request2 = createRequest("test2", "test@gmail2.com", "testPW123!", "testPW123!", "테스트2");

        Review review1 = Review.builder()
                .member(Member.createMember(request1))
                .content("123123")
                .reg_date(LocalDateTime.now())
                .build();

        Review review2 = Review.builder()
                .member(Member.createMember(request2))
                .content("좋은일은 언제나 있어.")
                .reg_date(LocalDateTime.now())
                .build();

        List<Review> mockReviews = new ArrayList<>();
        mockReviews.add(review1);
        mockReviews.add(review2);

        Mockito.when(reviewRepository.findAllByItemId(anyLong())).thenReturn(mockReviews);

        List<ReviewResponseDto> result = reviewService.getReviews(1L);

        assertEquals(mockReviews.size(), result.size(), "리뷰 개수가 일치해야 함");

        int i = 0;
        String[] expectedContents = {"123123", "좋은일은 언제나 있어."};
        String[] expectedAccounts = {"test", "test2"};

        for (ReviewResponseDto dto : result) {
            assertEquals(expectedContents[i], dto.getContent());
            assertEquals(expectedAccounts[i], dto.getAccount());
            i++;
        }

        verify(reviewRepository, times(1)).findAllByItemId(anyLong());
    }

    private MemberRegisterRequest createRequest(String account, String email, String password, String passwordConfirm, String name) {
        return new MemberRegisterRequest(account, email, password, passwordConfirm, name);
    }
}
