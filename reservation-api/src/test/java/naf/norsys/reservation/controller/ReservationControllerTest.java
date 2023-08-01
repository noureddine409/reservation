package naf.norsys.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import naf.norsys.reservation.dto.ItemDto;
import naf.norsys.reservation.dto.PeriodDto;
import naf.norsys.reservation.dto.ReservationDto;
import naf.norsys.reservation.dto.UserDto;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.ReservationPeriod;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.ItemService;
import naf.norsys.reservation.service.ReservationService;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.ClassTypeProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ReservationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ItemService itemService;

    @Mock
    private UserService userService;

    @Mock
    private ReservationService reservationService;

    @Mock
    private ClassTypeProvider classTypeProvider;

    @Mock
    private ModelMapper modelMapper;

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
    public void testSaveReservation() throws Exception {
        // Arrange
        ReservationDto dto = createSampleReservationDto();

        // Mock the behavior of the services
        mockServiceBehavior();

        // Act & Assert
        mockMvc.perform(post("/api/v1/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.item.id").value(dto.getItem().getId()))
                .andExpect(jsonPath("$.user.id").value(dto.getUser().getId()))
                .andExpect(jsonPath("$.period.startDate").value(dto.getPeriod().getStartDate().toString()))
                .andExpect(jsonPath("$.period.endDate").value(dto.getPeriod().getEndDate().toString()))
                .andReturn();

        // Verify that the services were called with the correct arguments
        verify(itemService, times(1)).checkItemStatus(1L);
        verify(userService, times(1)).findById(1L);
        verify(reservationService, times(1)).save(any(Reservation.class));
    }

    // Helper method to create a sample ReservationDto for testing
    private ReservationDto createSampleReservationDto() {
        UserDto user = UserDto.builder().id(1L).email("John@domain.me").build();
        ItemDto item = ItemDto.builder().id(1L).name("Item A").build();

        return ReservationDto.builder()
                .user(user)
                .item(item)
                .period(PeriodDto.builder()
                        .startDate(LocalDateTime.of(2023, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2023, 8, 10, 12, 0))
                        .build())

                .build();
    }

    // Helper method to mock the behavior of the services
    private void mockServiceBehavior() {
        when(classTypeProvider.getClasses(any(), any())).thenReturn(new Class[]{Reservation.class, ReservationDto.class});

        Item item = Item.builder().id(1L).name("Item A").build();
        when(itemService.findById(anyLong())).thenReturn(item);

        User user = User.builder().id(1L).email("John@domain.me").build();
        when(userService.findById(anyLong())).thenReturn(user);

        when(modelMapper.map(any(), eq(Reservation.class))).thenAnswer(invocation -> {
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

        when(modelMapper.map(any(), eq(ReservationDto.class))).thenAnswer(invocation -> {
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

        Reservation savedReservation = Reservation.builder()
                .id(1L)
                .item(item)
                .user(user)
                .period(ReservationPeriod.builder()
                        .startDate(LocalDateTime.of(2023, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2023, 8, 10, 12, 0))
                        .build())
                .build();
        savedReservation.setId(1L);
        savedReservation.setUser(user);
        savedReservation.setItem(item);
        when(reservationService.save(any(Reservation.class))).thenReturn(savedReservation);
    }
}
