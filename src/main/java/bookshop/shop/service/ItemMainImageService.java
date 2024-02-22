package bookshop.shop.service;

import bookshop.shop.domain.Item;
import bookshop.shop.domain.ItemMainImage;
import bookshop.shop.exception.item.NotFoundMainImageException;
import bookshop.shop.repository.ItemMainImageRepository;
import bookshop.shop.util.FileUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ItemMainImageService {
    @Value("${file.dir.main-image}")
    private String fileDir;
    private final ItemMainImageRepository itemMainImageRepository;
    private final FileUtil fileUtil;

    public void createItemMainImage(Item item, MultipartFile multipartFile) {
        if (multipartFile == null) {
            throw new NotFoundMainImageException("첨부된 메인 도서 이미지가 없습니다.");
        }
        String fileName = fileUtil.saveFile(fileDir, multipartFile);
        ItemMainImage itemMainImage = ItemMainImage.builder().fileName(fileName).item(item).build();
        itemMainImageRepository.save(itemMainImage);
    }
}
