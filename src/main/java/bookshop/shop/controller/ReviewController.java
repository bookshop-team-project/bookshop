package bookshop.shop.controller;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.Review;
import bookshop.shop.dto.request.ReviewRequestDto;
import bookshop.shop.dto.response.ReviewResponseDto;
import bookshop.shop.service.getReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//프론트에서 Review API 요청을 받는 컨트롤러
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    @Autowired
    private final getReviewService reviewService;

    @GetMapping("/read/{itemId}")
    public List<ReviewResponseDto> getList(@PathVariable(name = "itemId") Long itemId) {
        return reviewService.getReviews(itemId);
    }
}
