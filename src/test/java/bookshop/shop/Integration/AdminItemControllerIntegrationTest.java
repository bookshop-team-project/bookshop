package bookshop.shop.Integration;

import bookshop.shop.dto.request.AdminItemRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class AdminItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private AdminItemRequestDto createTestItemDto() {
        return AdminItemRequestDto.builder()
                .itemName("TestItemName")
                .content("TestContent")
                .price(1000)
                .quantity(10)
                .author("TestAuthor")
                .company("TestCompany")
                .build();
    }

    /**
     * @param dto : 사용자로부터 요청받는 itemDto
     * @return : MockMultipartFile 타입의 itemDto, itemImageList, itemMainImage 매개 변수를 생성하여 List에 담아 반환
     */
    private List<MockMultipartFile> dummyMultipartFileList(AdminItemRequestDto dto) {
        String jsonItem;
        try {
            jsonItem = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        MockMultipartFile itemPart = new MockMultipartFile("item", "", "application/json", jsonItem.getBytes());
        MockMultipartFile itemImage1 = new MockMultipartFile("itemImageList", "testImage1.jpg", "multipart/form-data", "image1 data".getBytes());
        MockMultipartFile itemImage2 = new MockMultipartFile("itemImageList", "testImage2.jpg", "multipart/form-data", "image2 data".getBytes());
        MockMultipartFile mainImagePart = new MockMultipartFile("itemMainImage", "testMainImage.jpg", "multipart/form-data", "main image data".getBytes());
        return List.of(itemPart, itemImage1, itemImage2, mainImagePart);
    }

    /**
     * @return : (POST) url, dummyMultipartFileList 를 기반으로 동작한 결과 반환
     */
    private ResultActions performRequest(String url, List<MockMultipartFile> multipartFiles) throws Exception {
        return mockMvc.perform(multipart(url)
                .file(multipartFiles.get(0)) //itemPart
                .file(multipartFiles.get(1)) //itemImage1
                .file(multipartFiles.get(2)) //itemImage2
                .file(multipartFiles.get(3)) //mainImagePart
                .contentType(MediaType.MULTIPART_FORM_DATA));
    }

    @Test
    @DisplayName("item 생성 테스트 : 기본으로 다 있는 경우")
    public void itemCreateTest() throws Exception {
        List<MockMultipartFile> dummyList = dummyMultipartFileList(createTestItemDto());
        performRequest("/admin/item", dummyList)
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("상품이 신규 등록되었습니다."));
    }

    @Test
    @DisplayName("item 생성 테스트 : 메인 이미지 없는 경우 오류 발생")
    public void itemCreateTest_noMainImage() throws Exception {
        List<MockMultipartFile> dummyList = dummyMultipartFileList(createTestItemDto());

        mockMvc.perform(multipart("/admin/item")
                        .file(dummyList.get(0)) //itemPart
                        .file(dummyList.get(1)) //itemImage1
                        .file(dummyList.get(2)) //itemImage2
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("첨부된 메인 도서 이미지가 없습니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("메인 이미지 없음"));
    }
    @Test
    @DisplayName("item 생성 테스트 : 금액 1000원 미만인 경우 오류 발생")
    public void itemCreateTest_priceLessThen1000() throws Exception {
        AdminItemRequestDto dto = AdminItemRequestDto.builder()
                .itemName("TestItemName")
                .content("TestContent")
                .price(500)
                .quantity(10)
                .author("TestAuthor")
                .company("TestCompany")
                .build();
        performRequest("/admin/item", dummyMultipartFileList(dto))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("금액은 최소 1,000원입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("입력값 오류"));
    }

    /**
     * DB 세팅 필요 → deleteImageIdList 의 값이 db에도 있어야 오류 없이 실행 가능
     */
    @Test
    @DisplayName("item 수정 테스트 : 전체 변경")
    public void itemUpdateTest_All() throws Exception {
        List<MockMultipartFile> dummyList = dummyMultipartFileList(createTestItemDto());
        byte[] bytes = objectMapper.writeValueAsString(List.of(1, 2)).getBytes();
        MockMultipartFile deleteImageIdList = new MockMultipartFile("deleteImageIdList", "", "application/json", bytes);
        mockMvc.perform(multipart("/admin/item/{itemId}", 1L)
                        .file(dummyList.get(0))
                        .file(dummyList.get(1))
                        .file(dummyList.get(2))
                        .file(dummyList.get(3))
                        .file(deleteImageIdList))
                .andExpect(status().isCreated())
                .andExpect(content().string("상품이 수정되었습니다."));
    }

    @Test
    @DisplayName("item 수정 테스트 : Dto 유효성 검사")
    public void itemUpdateTest_dtoError() throws Exception {
        long itemId = 1L;
        //given 1 : quantity 10개 미만
        AdminItemRequestDto dto = AdminItemRequestDto.builder()
                .itemName("TestItemName")
                .content("TestContent")
                .price(1000)
                .quantity(1)
                .author("TestAuthor")
                .company("TestCompany")
                .build();
        performRequest("/admin/item/" + itemId, dummyMultipartFileList(dto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("최소 수량은 10개입니다."));

        //given 2 : content 값 없음
        AdminItemRequestDto dto2 = AdminItemRequestDto.builder()
                .itemName("TestItemName")
                .content("")
                .price(1000)
                .quantity(10)
                .author("TestAuthor")
                .company("TestCompany")
                .build();
        performRequest("/admin/item/" + itemId, dummyMultipartFileList(dto))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("입력값 오류"));
    }
}
