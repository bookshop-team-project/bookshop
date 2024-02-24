package bookshop.shop.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class ReviewRequestDto {
    private Long id;
    private String content;
    private LocalDateTime reg_date;
    private String use;
    private Long member_id;
    private Long item_id;

}
