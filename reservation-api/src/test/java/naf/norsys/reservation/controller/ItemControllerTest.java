package naf.norsys.reservation.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import naf.norsys.reservation.dto.ItemDto;
import naf.norsys.reservation.dto.SearchItemDto;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.utils.MapHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService itemService;

    @Mock
    private MapHelper mapHelper;

    @InjectMocks
    private ItemController itemController;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
    }

    @Test
    void testSearchItems() throws Exception {
        // Prepare test data
        List<Item> items = Arrays.asList(
                Item.builder().name("Item 1").category(GenericEnum.ItemCategory.APARTMENT).build(),
                Item.builder().name("Item 3").category(GenericEnum.ItemCategory.APARTMENT).build()
        );

        // Mock the service method
        when(itemService.search(anyString(), any(), anyInt(), anyInt())).thenReturn(items);
        when(itemService.getTotalPages(anyInt())).thenReturn(2); // Assuming 2 pages for this test
        when(mapHelper.convertListToDto(any(), eq(ItemDto.class))).thenAnswer(invocation -> {
            List<Item> entities = invocation.getArgument(0);
            return entities.stream().map(
                    entity -> ItemDto.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .category(entity.getCategory())
                            .description(entity.getDescription())
                            .build()
            ).collect(Collectors.toList());

        });
        // Prepare request DTO
        SearchItemDto searchItemDto = SearchItemDto.builder()
                .keyword("Item")
                .category(GenericEnum.ItemCategory.APARTMENT)
                .page(0)
                .size(10)
                .build();

        // Perform the request
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/items/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchItemDto)))
                .andExpect(status().isOk())
                .andExpect(header().string("X-Total-Pages", "2"))
                .andReturn();

        // Verify the response content
        String responseJson = mvcResult.getResponse().getContentAsString();
        List<ItemDto> responseDto = objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        org.junit.jupiter.api.Assertions.assertEquals(2, responseDto.size());
        org.junit.jupiter.api.Assertions.assertEquals("Item 1", responseDto.get(0).getName());
        org.junit.jupiter.api.Assertions.assertEquals("Item 3", responseDto.get(1).getName());
    }
}
