package bookshop.shop.dto.response;

import bookshop.shop.domain.Review;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

//엔티티 중에서 특정 컬럼만 응답으로 받고싶을때 사용하는 응답 DTO
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class ReviewResponseDto {
    private String account;
    private String content;
    private LocalDateTime reg_date;
}
