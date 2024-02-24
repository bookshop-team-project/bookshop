package bookshop.shop.service;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.Review;
import bookshop.shop.dto.request.AdminItemRequestDto;
import bookshop.shop.dto.request.ReviewRequestDto;
import bookshop.shop.dto.response.ReviewResponseDto;
import bookshop.shop.repository.GetReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class getReviewService {
    @Autowired
    private final GetReviewRepository getReviewRepository;

    //쿼리문으로 응답받은 레포지토리 값을 응답 dto로 변환
    public List<ReviewResponseDto> getReviews(Long itemId) {
        return getReviewRepository.findAllByItemId(itemId)
                .stream()
                .map(review -> ReviewResponseDto.builder()
                        .account(review.getMember().getAccount())
                .content(review.getContent())
                .reg_date(review.getReg_date())
                .build())
                .collect(Collectors.toList());
    }
}
