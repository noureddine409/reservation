package naf.norsys.reservation.service;

import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.repository.ItemRepository;
import naf.norsys.reservation.service.impl.ItemServiceImpl;
import naf.norsys.reservation.utils.MapHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private MapHelper mapHelper;

    @InjectMocks
    private ItemServiceImpl itemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdate_SuccessfulUpdateOfExistingItem() throws ElementNotFoundException {
        // Prepare Test Data
        final Long entityId = 1L;
        final Item existingEntity = createMockItem(entityId, "name", "description", GenericEnum.ItemCategory.VEHICULE);
        final Item updatedEntity = createMockItem(entityId, "updated name", "updated Description", GenericEnum.ItemCategory.APARTMENT);

        // Mock Repository Methods
        when(itemRepository.findById(entityId)).thenReturn(Optional.of(existingEntity));
        when(itemRepository.save(existingEntity)).thenReturn(updatedEntity);

        // Perform the update operation
        Item result = itemService.update(entityId, updatedEntity);

        // Verify the returned entity
        assertEquals(updatedEntity, result);

        // Verify that the itemRepository.save method is called once with the updated entity
        verify(itemRepository, times(1)).save(existingEntity);

        // Verify that the modelMapper.map method is called once with the correct entities
        verify(mapHelper, times(1)).map(updatedEntity, existingEntity);
    }

    @Test
    public void testSave_NewEntity_SuccessfulSave() throws ElementAlreadyExistsException {
        // Prepare Test Data
        final Item newItem = createMockItem(null, "name", "descr", GenericEnum.ItemCategory.VEHICULE);

        // Mock Repository Method
        when(itemRepository.save(any())).thenAnswer(invocation -> {
            Item savedItem = invocation.getArgument(0);
            savedItem.setId(1L); // Simulate generated ID
            savedItem.setCreatedAt(LocalDateTime.now()); // Set createdAt to the actual date
            return savedItem;
        });

        // Perform the save operation
        Item result = itemService.save(newItem);

        // Verify the returned entity
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(newItem.getName(), result.getName());
        assertEquals(newItem.getDescription(), result.getDescription());

        // Verify that the genericRepository.save method is called once with the new entity
        verify(itemRepository, times(1)).save(any());

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testSave_ExistingEntity_ElementAlreadyExistsException() {
        // Prepare Test Data
        final Long existingEntityId = 1L;
        final Item existingItem = createMockItem(existingEntityId, "Existing Item", "Existing Description", GenericEnum.ItemCategory.VEHICULE);
        existingItem.setId(existingEntityId);
        // Mock Repository Method to return an existing entity with the same ID
        when(itemRepository.findById(existingEntityId)).thenReturn(Optional.of(existingItem));

        // Perform the save operation and expect ElementAlreadyExistsException to be thrown
        assertThrows(ElementAlreadyExistsException.class, () -> itemService.save(existingItem));

        // Verify that the genericRepository.findById method is called once with the given ID
        verify(itemRepository, times(1)).findById(existingEntityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testDelete_DataAccessException_ElementNotFoundException()  {
        // Prepare Test Data
        final Long entityId = 1L;

        // Mock Repository Method to throw DataAccessException (Generic data access exception)
        doThrow(Mockito.mock(DataAccessException.class)).when(itemRepository).deleteById(entityId);

        // Perform the delete operation and expect ElementNotFoundException to be thrown
        assertThrows(BusinessException.class, () -> itemService.delete(entityId));

        // Verify that the genericRepository.deleteById method is called once with the given ID
        verify(itemRepository, times(1)).deleteById(entityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testUpdate_NonExistingItem_ThrowElementNotFoundException() throws ElementNotFoundException {

        // Prepare Test Data
        final Long nonExistingEntityId = 99L;
        final Item updatedEntity = createMockItem(nonExistingEntityId, "updated name", "updated Description", GenericEnum.ItemCategory.VEHICULE);

        // Mock Repository Methods
        when(itemRepository.findById(nonExistingEntityId)).thenReturn(Optional.empty());

        // Perform the update operation and expect an ElementNotFoundException
        assertThrows(ElementNotFoundException.class, () -> itemService.update(nonExistingEntityId, updatedEntity));

        // Verify that the modelMapper.map method is not called since there's no item to update
        verify(mapHelper, never()).map(any(), any());

        // Verify that the itemRepository.save method is not called since there's no item to update
        verify(itemRepository, never()).save(any());

    }

    @Test
    public void testDelete_ExistingEntity_SuccessfulDeletion() throws ElementNotFoundException {
        // Prepare Test Data
        final Long entityId = 1L;

        // Mock Repository Method
        doNothing().when(itemRepository).deleteById(entityId);

        // Perform the delete operation
        boolean result = itemService.delete(entityId);

        // Verify the returned result
        assertTrue(result);

        // Verify that the genericRepository.deleteById method is called once with the given ID
        verify(itemRepository, times(1)).deleteById(entityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testFindById_ExistingEntity() throws ElementNotFoundException {
        // Prepare Test Data
        final Long entityId = 1L;
        final Item existingEntity = createMockItem(entityId, "name", "description", GenericEnum.ItemCategory.VEHICULE);
        // Mock Repository Method
        when(itemRepository.findById(entityId)).thenReturn(Optional.of(existingEntity));

        // Perform the findById operation
        Item result = itemService.findById(entityId);

        // Verify the returned entity
        assertEquals(existingEntity, result);

        // Verify that the genericRepository.findById method is called once with the given ID
        verify(itemRepository, times(1)).findById(entityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testDelete_NonExistingEntity_ElementNotFoundException() {
        // Prepare Test Data
        final Long nonExistingEntityId = 100L;

        // Mock Repository Method to throw EmptyResultDataAccessException (Entity not found)
        doThrow(EmptyResultDataAccessException.class).when(itemRepository).deleteById(nonExistingEntityId);

        // Perform the delete operation and expect ElementNotFoundException to be thrown
        assertThrows(ElementNotFoundException.class, () -> itemService.delete(nonExistingEntityId));

        // Verify that the genericRepository.deleteById method is called once with the given ID
        verify(itemRepository, times(1)).deleteById(nonExistingEntityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testFindById_EntityNotFound() {
        // Prepare Test Data
        final Long entityId = 1L;

        // Mock Repository Method
        when(itemRepository.findById(entityId)).thenReturn(Optional.empty());

        // Perform the findById operation and expect ElementNotFoundException to be thrown
        assertThrows(ElementNotFoundException.class, () -> itemService.findById(entityId));

        // Verify that the genericRepository.findById method is called once with the given ID
        verify(itemRepository, times(1)).findById(entityId);

        // Verify that there are no further interactions with the genericRepository
        verifyNoMoreInteractions(itemRepository);
    }

    @Test
    public void testSearchByKeywordAndCategory() {
        // Prepare test data
        final String keyword = "Item";
        final GenericEnum.ItemCategory category = GenericEnum.ItemCategory.APARTMENT;
        int page = 0;
        final int size = 10;
        final Pageable pageable = PageRequest.of(page, size);


        List<Item> expectedItems = Arrays.asList(
                Item.builder().name("Item 1").category(GenericEnum.ItemCategory.APARTMENT).build(),
                Item.builder().name("Item 3").category(GenericEnum.ItemCategory.APARTMENT).build()
        );

        // Mock the repository method
        when(itemRepository.searchByKeywordAndCategory(keyword, category, pageable)).thenReturn(expectedItems);

        // Perform the search
        List<Item> result = itemService.search(keyword, category, page, size);

        // Verify the result
        assertThat(result).isEqualTo(expectedItems);
    }

    @Test
    public void testSearchWithException() {
        // Prepare test data
        final String keyword = "InvalidKeyword";
        final GenericEnum.ItemCategory category = GenericEnum.ItemCategory.VEHICULE;
        final int page = 0;
        final int size = 10;

        // Mock the repository method to throw a DataAccessException
        when(itemRepository.searchByKeywordAndCategory(any(), any(), any())).thenThrow(Mockito.mock(DataAccessException.class));

        // Perform the search and expect BusinessException
        BusinessException exception = org.junit.jupiter.api.Assertions.assertThrows(BusinessException.class,
                () -> itemService.search(keyword, category, page, size));

        // Verify the BusinessException
        assertThat(exception.getKey()).isEqualTo(CoreConstant.Exception.FIND_ELEMENTS);
    }


    private static Item createMockItem(Long entityId, String name, String description, GenericEnum.ItemCategory category) {
        return Item.builder()
                .id(entityId)
                .createdAt(LocalDateTime.now())
                .name(name)
                .category(category)
                .description(description)
                .build();
    }
}
