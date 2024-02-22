package bookshop.shop.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminItemRequestDto {
    @NotBlank
    private String itemName;
    @NotBlank
    private String content;
    @NotNull
    @Min(value = 1000, message = "금액은 최소 1,000원입니다.")
    @Max(value = 10000000, message = "금액은 최대 10,000,000원입니다.")
    private int price;
    @NotNull
    @Min(value = 10, message = "최소 수량은 10개입니다.")
    @Max(value = 10000000, message = "최대 수량은 10,000,000개입니다.")
    private int quantity;
    @NotBlank
    private String author;
    @NotBlank
    private String company;
}
