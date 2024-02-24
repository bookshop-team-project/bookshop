package bookshop.shop.repository;

import bookshop.shop.domain.Review;
import bookshop.shop.dto.response.ReviewResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//디비 테이블에서 정보 가져와서 List에 담아서 출력
@Repository
public interface GetReviewRepository extends JpaRepository<Review, Long> {
        @Query("select r from Review r inner join Member m on r.member.id = m.id where r.item.id = :itemId order by r.reg_date desc")
        List<Review> findAllByItemId(@Param("itemId") Long itemId);
}