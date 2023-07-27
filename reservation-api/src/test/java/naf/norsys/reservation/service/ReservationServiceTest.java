package naf.norsys.reservation.service;


import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ItemAvailabilityException;
import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.repository.ReservationRepository;
import naf.norsys.reservation.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static naf.norsys.reservation.model.GenericEnum.ItemCategory.VEHICULE;
import static naf.norsys.reservation.model.GenericEnum.ItemStatus.AVAILABLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    Reservation reservation;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

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
                .startDate(LocalDateTime.of(2023, 7, 1, 12, 0))
                .endDate(LocalDateTime.of(2023, 7, 10, 12, 0))
                .user(user)
                .item(item)
                .build();
    }

    @Test
    public void testSaveSuccessfulReservation() throws ElementAlreadyExistsException {
        // Arrange
        final Long reservationId = 1L; // Assuming the reservation ID is 1L

        // Assume that the reservation is available
        when(reservationRepository.checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(true);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        // Act
        Reservation savedReservation = reservationService.save(reservation);

        // Assert
        assertNotNull(savedReservation);
        assertEquals(reservation.getStartDate(), savedReservation.getStartDate());
        assertEquals(reservation.getEndDate(), savedReservation.getEndDate());
        assertEquals(reservation.getUser(), savedReservation.getUser());
        assertEquals(reservation.getItem(), savedReservation.getItem());

        // Verify that the repository methods were called with the correct parameters
        verify(reservationRepository, times(1)).checkAvailability(eq(reservationId), any(LocalDateTime.class), any(LocalDateTime.class));
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    public void testSaveUnavailableReservation() {
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
