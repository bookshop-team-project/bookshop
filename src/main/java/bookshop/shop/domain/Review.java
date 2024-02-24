package bookshop.shop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

//DB의 테이블 컬럼과 맞는 엔티티
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Table(name="Review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime reg_date;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="member_id")
    private Member member; // n:1 관계

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name="item_id")
    private Item item; // n:1 관계
}
