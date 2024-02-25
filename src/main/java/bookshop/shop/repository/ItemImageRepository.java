package bookshop.shop.repository;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {
//    @Query("SELECT i.id FROM ItemImage i WHERE i.item.id = :itemId")
    List<ItemImage> findAllByItem(Item item);
}
