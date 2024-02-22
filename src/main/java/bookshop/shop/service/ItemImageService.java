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

    public void createItemImage(Item item, List<MultipartFile> multipartFiles) {
        if (multipartFiles == null) {
            log.info("첨부된 도서 이미지가 없습니다.");
            return;
        }
        List<String> fileNames = fileUtil.saveFiles(fileDir, multipartFiles);
        List<ItemImage> itemImages = fileNames.stream()
                .map(fileName -> ItemImage.builder()
                        .fileName(fileName)
                        .item(item)
                        .build())
                .toList();
        itemImageRepository.saveAll(itemImages);

    }
}


