package bookshop.shop.service;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.ItemImage;
import bookshop.shop.dto.request.AdminItemRequestDto;
import bookshop.shop.repository.ItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemMainImageService itemMainImageService;

    public void createItem(AdminItemRequestDto adminItemRequestDto,
                           List<MultipartFile> itemImageList, MultipartFile itemMainImage) {
        Item item = itemRepository.save(toEntity(adminItemRequestDto));
        itemMainImageService.createItemMainImage(item, itemMainImage);
        itemImageService.createItemImage(item, itemImageList);
    }

    public void updateItem(Long itemId, AdminItemRequestDto  adminItemRequestDto, List<MultipartFile> itemImageList,
                           List<Long> deleteImageIdList, MultipartFile itemMainImage) {
        Item item = getItem(itemId);
        itemMainImageService.updateItemMainImage(item, itemMainImage);
        itemImageService.updateItemImage(item, itemImageList, deleteImageIdList);
        item.updateEntityInfo(toEntity(adminItemRequestDto));
    }

    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("적합한 id 값이 아닙니다."));
    }

    public void deleteItem(Long itemId) {
        Item item = getItem(itemId);
        itemMainImageService.deleteItemMainImage(item);
        List<Long> deleteList = itemImageService.getAllByItem(item).stream().map(ItemImage::getId).toList();
        itemImageService.deleteItemImage(deleteList);
        itemRepository.deleteById(itemId);
    }

    public Item toEntity(AdminItemRequestDto itemRequestDto) {
        return Item.builder()
                .itemName(itemRequestDto.getItemName())
                .content(itemRequestDto.getContent())
                .price(itemRequestDto.getPrice())
                .quantity(itemRequestDto.getQuantity())
                .author(itemRequestDto.getAuthor())
                .company(itemRequestDto.getCompany())
                .regDate(LocalDateTime.now())
                .build();
    }
}
