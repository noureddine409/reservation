package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    private final Item item1 = Item.builder()
            .name("Item 1")
            .category(GenericEnum.ItemCategory.APARTMENT)
            .build();

    private final Item item2 = Item.builder()
            .name("Item 2")
            .category(GenericEnum.ItemCategory.VEHICULE)
            .build();

    private final Item item3 = Item.builder()
            .name("Item 3")
            .category(GenericEnum.ItemCategory.APARTMENT)
            .build();

    @BeforeEach
    public void setUp() {
        // Save test data to the repository before each test
        itemRepository.saveAll(Arrays.asList(item1, item2, item3));
    }

    @Test
    void shouldSearchByKeywordAndCategory() {
        // Test the method
        List<Item> result1 = itemRepository.searchByKeywordAndCategory("Item", GenericEnum.ItemCategory.APARTMENT, PageRequest.of(0, 3));
        List<Item> result2 = itemRepository.searchByKeywordAndCategory("keyword", null, Pageable.unpaged());
        List<Item> result3 = itemRepository.searchByKeywordAndCategory("Item", null, Pageable.unpaged());
        List<Item> result4 = itemRepository.searchByKeywordAndCategory("", GenericEnum.ItemCategory.APARTMENT, PageRequest.of(0, 10));

        // Assert the results
        assertThat(result1).containsExactlyInAnyOrder(item1, item3);
        assertThat(result2).isEmpty();
        assertThat(result3).containsExactlyInAnyOrder(item1, item2, item3);
        assertThat(result4).containsExactlyInAnyOrder(item1, item3);

    }

    @Test
    void shouldSearchByKeywordAndCategoryWithPagination() {
        // Test the method with pagination
        Pageable pageable = PageRequest.of(0, 2, Sort.by("name").ascending());
        List<Item> result = itemRepository.searchByKeywordAndCategory("Item", GenericEnum.ItemCategory.APARTMENT, pageable);

        // Assert the results
        assertThat(result).containsExactly(item1, item3);
    }
}
