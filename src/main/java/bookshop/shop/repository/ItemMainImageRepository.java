package bookshop.shop.repository;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.ItemMainImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMainImageRepository extends JpaRepository<ItemMainImage, Long> {
    ItemMainImage findByItem(Item item);
}
