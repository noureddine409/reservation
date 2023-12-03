package naf.norsys.reservation.service;


import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ItemAvailabilityException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.ReservationPeriod;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.ReservationRepository;
import naf.norsys.reservation.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static naf.norsys.reservation.model.GenericEnum.ItemCategory.VEHICULE;
import static naf.norsys.reservation.model.GenericEnum.ItemStatus.AVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    Reservation reservation;
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    public void setUp() {
        // Create test data for the reservation
        User user = User.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .email("user@domain.me")
                .build();

        Item item = Item.builder()
                .id(1L)
                .name("Test Item")
                .description("Test Item Description")
                .category(VEHICULE)
                .createdBy(user)
                .status(AVAILABLE)
                .build();

        reservation = Reservation.builder()
                .period(ReservationPeriod.builder()
                        .startDate(LocalDateTime.of(2023, 7, 1, 12, 0))
                        .endDate(LocalDateTime.of(2023, 7, 10, 12, 0))
                        .build())
                .user(user)
                .item(item)
                .build();
    }

    @Test
    void testSaveSuccessfulReservation() throws ElementAlreadyExistsException {
        // Arrange
        final Long reservationId = 1L; // Assuming the reservation ID is 1L

        // Assume that the reservation is available
        when(reservationRepository.checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation savedReservation = reservationService.save(reservation);

        // Assert
        assertNotNull(savedReservation);
        assertEquals(reservation.getPeriod().getStartDate(), savedReservation.getPeriod().getStartDate());
        assertEquals(reservation.getPeriod().getEndDate(), savedReservation.getPeriod().getEndDate());
        assertEquals(reservation.getUser(), savedReservation.getUser());
        assertEquals(reservation.getItem(), savedReservation.getItem());

        // Verify that the repository methods were called with the correct parameters
        verify(reservationRepository, times(1)).checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testSaveUnavailableReservation() {
        // Arrange
        final Long reservationId = 1L; // Assuming the reservation ID is 1L

        // Assume that the reservation is not available
        when(reservationRepository.checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(false);

        // Act & Assert
        assertThrows(ItemAvailabilityException.class, () -> reservationService.save(reservation));

        // Verify that the repository method was called with the correct parameters
        verify(reservationRepository, times(1)).checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class));
        // Ensure that save method was not called when reservation is unavailable
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
