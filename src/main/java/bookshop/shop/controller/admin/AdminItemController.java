package bookshop.shop.controller.admin;

import bookshop.shop.dto.request.AdminItemRequestDto;
import bookshop.shop.exception.global.ValidationExceptionUtil;
import bookshop.shop.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminItemController {
    private final ItemService itemService;

    @PostMapping("/item")
    public ResponseEntity<String> itemCreate(@Valid @RequestPart(name = "item") AdminItemRequestDto adminItemRequestDto,
                                             BindingResult bindingResult,
                                             @RequestPart(required = false) List<MultipartFile> itemImageList,
                                             @RequestPart MultipartFile itemMainImage) {
        ValidationExceptionUtil.checkBindingResult(bindingResult);
        itemService.createItem(adminItemRequestDto, itemImageList, itemMainImage);
        return ResponseEntity.status(HttpStatus.CREATED).body("상품이 신규 등록되었습니다.");
    }

}
