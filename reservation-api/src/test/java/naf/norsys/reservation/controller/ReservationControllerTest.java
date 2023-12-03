package naf.norsys.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import naf.norsys.reservation.dto.*;
import naf.norsys.reservation.model.*;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.service.ReservationService;
import naf.norsys.reservation.utils.AuthenticationHelper;
import naf.norsys.reservation.utils.MapHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;


    @Mock
    private ReservationService reservationService;

    @Mock
    private MapHelper mapHelper;

    @Mock
    private AuthenticationHelper authenticationHelper;


    @InjectMocks
    private ReservationController reservationController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reservationController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Test
    void testSaveReservation() throws Exception {
        // Arrange
        ReservationDto dto = createSampleReservationDto();

        ReservationRequestDto requestDto = createSampleReservationRequestDto();

        // Mock the behavior of the services
        mockServiceBehavior();

        // Act & Assert
        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.item.id").value(dto.getItem().getId()))
                .andExpect(jsonPath("$.user.id").value(dto.getUser().getId()))
                .andExpect(jsonPath("$.period.startDate").value(dto.getPeriod().getStartDate().toString()))
                .andExpect(jsonPath("$.period.endDate").value(dto.getPeriod().getEndDate().toString()))
                .andReturn();

        // Verify that the services were called with the correct arguments
        verify(itemService, times(1)).checkItemStatus(1L);
        verify(authenticationHelper, times(1)).getCurrentUser();
        verify(reservationService, times(1)).save(any(Reservation.class));
    }

    // Helper method to create a sample ReservationDto for testing

    private ReservationRequestDto createSampleReservationRequestDto() {
        return ReservationRequestDto.builder()
                .itemId(1L)
                .period(PeriodDto.builder()
                        .startDate(LocalDateTime.of(2030, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2030, 8, 10, 12, 0))
                        .build())
                .build();
    }

    private ReservationDto createSampleReservationDto() {
        UserDto user = UserDto.builder().id(1L).email("John@domain.me").build();
        ItemDto item = ItemDto.builder().id(1L).name("Item A")
                .status(GenericEnum.ItemStatus.AVAILABLE)
                .description("description")
                .category(GenericEnum.ItemCategory.VEHICULE)
                .build();

        return ReservationDto.builder()
                .user(user)
                .item(item)
                .period(PeriodDto.builder()
                        .startDate(LocalDateTime.of(2030, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2030, 8, 10, 12, 0))
                        .build())

                .build();
    }

    // Helper method to mock the behavior of the services
    private void mockServiceBehavior() {

        Item item = Item.builder().id(1L).name("Item A").build();
        when(itemService.findById(anyLong())).thenReturn(item);

        User user = User.builder().id(1L).email("John@domain.me").build();
        when(authenticationHelper.getCurrentUser()).thenReturn(user);

        when(mapHelper.convertToEntity(any(), eq(Reservation.class))).thenAnswer(invocation -> {
            ReservationDto reservationDto = invocation.getArgument(0);
            return Reservation.builder()
                    .id(reservationDto.getId())
                    .user(User.builder().id(reservationDto.getUser().getId()).build())
                    .item(Item.builder().id(reservationDto.getUser().getId()).build())
                    .period(ReservationPeriod.builder()
                            .startDate(reservationDto.getPeriod().getStartDate())
                            .endDate(reservationDto.getPeriod().getEndDate())
                            .build())
                    .build();
        });

        when(mapHelper.convertToDto(any(), eq(ReservationDto.class))).thenAnswer(invocation -> {
            Reservation reservation = invocation.getArgument(0);
            return ReservationDto.builder()
                    .id(reservation.getId())
                    .user(UserDto.builder().id(1L).email("John@domain.me").build())
                    .item(ItemDto.builder().id(1L).name("Item A").build())
                    .period(PeriodDto.builder()
                            .startDate(reservation.getPeriod().getStartDate())
                            .endDate(reservation.getPeriod().getEndDate())
                            .build())

                    .build();
        });

        doAnswer(invocation -> {
            Reservation reservation = invocation.getArgument(1);
            ReservationRequestDto reservationRequestDto = invocation.getArgument(0);
            reservation.setPeriod(
                    ReservationPeriod.builder()
                            .startDate(reservationRequestDto.getPeriod().getStartDate())
                            .endDate(reservationRequestDto.getPeriod().getEndDate())
                            .build()
            );
            return null;
        }).when(mapHelper).map(any(), any());


        Reservation savedReservation = Reservation.builder()
                .id(1L)
                .item(item)
                .user(user)
                .period(ReservationPeriod.builder()
                        .startDate(LocalDateTime.of(2030, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2030, 8, 10, 12, 0))
                        .build())
                .build();
        savedReservation.setId(1L);
        savedReservation.setUser(user);
        savedReservation.setItem(item);
        when(reservationService.save(any(Reservation.class))).thenReturn(savedReservation);
    }
}
