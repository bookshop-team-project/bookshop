package bookshop.shop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private String content;
    private int price;
    private int quantity;
    private String author;
    private String company;
    private LocalDateTime regDate;

    public void updateEntityInfo(Item item) {
        this.itemName = item.getItemName();
        this.content = item.getContent();
        this.price = item.getPrice();
        this.quantity = item.getQuantity();
        this.author = item.getAuthor();
        this.company = item.getCompany();
    }
}
