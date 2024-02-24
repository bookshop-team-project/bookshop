package bookshop.shop.Integration;

import bookshop.shop.ShopApplication;
import bookshop.shop.dto.request.AdminItemRequestDto;
import bookshop.shop.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ShopApplication.class)
@Transactional
@AutoConfigureMockMvc
public class AdminItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ItemService itemService;
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

    @Test
    @DisplayName("item 생성 테스트 : 이미지 리스트, 메인 이미지 있는 경우")
    public void itemCreateTest_WithImageListAndMainImage() throws Exception {
        //given
        AdminItemRequestDto testItem = createTestItemDto();
        String jsonItem = objectMapper.writeValueAsString(testItem);
        MockMultipartFile itemPart = new MockMultipartFile("item", "", "application/json", jsonItem.getBytes());
        MockMultipartFile itemImage1 = new MockMultipartFile("itemImageList", "testImage1.jpg", "multipart/form-data", "image1 data".getBytes());
        MockMultipartFile itemImage2 = new MockMultipartFile("itemImageList", "testImage2.jpg", "multipart/form-data", "image2 data".getBytes());
        List<MultipartFile> itemImages = List.of(itemImage1, itemImage2);
        MockMultipartFile mainImagePart = new MockMultipartFile("itemMainImage", "testMainImage.jpg", "multipart/form-data", "main image data".getBytes());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/item")
                .file(itemImage1)
                .file(itemImage2)
                .file(mainImagePart)
                .file(itemPart)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("상품이 신규 등록되었습니다."));

        verify(itemService, times(1)).createItem(any(AdminItemRequestDto.class), eq(itemImages), eq(mainImagePart));
    }

    @Test
    @DisplayName("item 생성 테스트 : 이미지 리스트만 있는 경우 오류 발생")
    public void itemCreateTest_WithImageList() throws Exception { //given
        AdminItemRequestDto testItem = createTestItemDto();
        String jsonItem = objectMapper.writeValueAsString(testItem);
        MockMultipartFile itemPart = new MockMultipartFile("item", "", "application/json", jsonItem.getBytes());
        MockMultipartFile itemImage1 = new MockMultipartFile("itemImageList", "testImage1.jpg", "multipart/form-data", "image1 data".getBytes());
        MockMultipartFile itemImage2 = new MockMultipartFile("itemImageList", "testImage2.jpg", "multipart/form-data", "image2 data".getBytes());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/item")
                        .file(itemImage1)
                        .file(itemImage2)
                        .file(itemPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("item 생성 테스트 : 금액 1000원 미만인 경우 오류 발생")
    public void itemCreateTest_priceLessThen1000() throws Exception {
        //given
        AdminItemRequestDto build = AdminItemRequestDto.builder()
                .itemName("TestItemName")
                .content("TestContent")
                .price(500)
                .quantity(10)
                .author("TestAuthor")
                .company("TestCompany")
                .build();

        String jsonItem = objectMapper.writeValueAsString(build);
        MockMultipartFile itemPart = new MockMultipartFile("item", "", "application/json", jsonItem.getBytes());
        MockMultipartFile mainImagePart = new MockMultipartFile("itemMainImage", "testMainImage.jpg", "multipart/form-data", "main image data".getBytes());

        //when & then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/item")
                        .file(mainImagePart)
                        .file(itemPart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("금액은 최소 1,000원입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("입력값 오류"));

    }
}
