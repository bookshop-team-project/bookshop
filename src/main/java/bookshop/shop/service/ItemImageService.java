package bookshop.shop.service;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.ItemImage;
import bookshop.shop.repository.ItemImageRepository;
import bookshop.shop.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemImageService {
    @Value("${file.dir.item-image}")
    private String fileDir;
    private final ItemImageRepository itemImageRepository;
    private final FileUtil fileUtil;

    public void createItemImage(Item item, List<MultipartFile> itemImageList) {
        if (itemImageList == null || itemImageList.isEmpty()) {
            log.trace("첨부된 도서 이미지가 없습니다.");
            return;
        }
        List<String> fileNames = fileUtil.saveFiles(fileDir, itemImageList);
        List<ItemImage> itemImages = fileNames.stream()
                .map(fileName -> ItemImage.builder()
                        .fileName(fileName)
                        .item(item)
                        .build())
                .toList();
        itemImageRepository.saveAll(itemImages);

    }

    public void updateItemImage(Item item, List<MultipartFile> itemImageList, List<Long> deleteImageIdList) {
        createItemImage(item, itemImageList);
        deleteItemImage(deleteImageIdList);
    }

    public void deleteItemImage(List<Long> deleteImageIdList) {
        if (deleteImageIdList == null || deleteImageIdList.isEmpty()) {
            log.trace("삭제할 도서 이미지가 없습니다.");
        } else {
            List<String> imageFileNameList = itemImageRepository.findAllById(deleteImageIdList)
                    .stream().map(ItemImage::getFileName).toList();
            if (imageFileNameList.size() != 0) {
                fileUtil.deleteFiles(fileDir, imageFileNameList);
                itemImageRepository.deleteAllById(deleteImageIdList);
            } else {
                throw new IllegalArgumentException("알 수 없는 파일 삭제 요청입니다.");
            }
        }
    }
}


