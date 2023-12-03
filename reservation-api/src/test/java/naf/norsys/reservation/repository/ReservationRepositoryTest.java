package naf.norsys.reservation.repository;

import naf.norsys.reservation.model.Item;
import naf.norsys.reservation.model.Reservation;
import naf.norsys.reservation.model.ReservationPeriod;
import naf.norsys.reservation.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static naf.norsys.reservation.model.GenericEnum.ItemCategory.VEHICULE;
import static naf.norsys.reservation.model.GenericEnum.ItemStatus.AVAILABLE;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    private Item item;

    @BeforeEach
    public void setUp() {
        User user = User.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .email("user@domain.me")
                .build();
        user = userRepository.save(user);

        item = Item.builder()
                .name("Test Item")
                .description("Test Item Description")
                .category(VEHICULE)
                .createdBy(user)
                .status(AVAILABLE)
                .build();
        item = itemRepository.save(item);

        Reservation reservation1 = Reservation.builder()
                .period(ReservationPeriod.builder()
                        .startDate(LocalDateTime.of(2023, 7, 1, 12, 0))
                        .endDate(LocalDateTime.of(2023, 7, 10, 12, 0))
                        .build())
                .user(user)
                .item(item)
                .build();

        Reservation reservation2 = Reservation.builder()
                .period(ReservationPeriod.builder()
                        .startDate(LocalDateTime.of(2023, 8, 1, 12, 0))
                        .endDate(LocalDateTime.of(2023, 8, 10, 12, 0))
                        .build())
                .user(user)
                .item(item)
                .build();

        reservationRepository.saveAll(List.of(reservation1, reservation2));
    }

    @Test
    void testExistingReservations_ItemAvailable() {
        // First reservation check
        Long itemId1 = item.getId();
        LocalDateTime startDate1 = LocalDateTime.of(2023, 7, 5, 12, 0);
        LocalDateTime endDate1 = LocalDateTime.of(2023, 7, 15, 12, 0);
        Specification<Reservation> specification1 = reservationRepository.existingReservationsByItem(itemId1, startDate1, endDate1);
        List<Reservation> reservations1 = reservationRepository.findAll(specification1);
        Assertions.assertEquals(1, reservations1.size());

        // Second reservation check
        Long itemId2 = item.getId();
        LocalDateTime startDate2 = LocalDateTime.of(2023, 9, 5, 12, 0);
        LocalDateTime endDate2 = LocalDateTime.of(2023, 9, 15, 12, 0);
        Specification<Reservation> specification2 = reservationRepository.existingReservationsByItem(itemId2, startDate2, endDate2);
        List<Reservation> reservations2 = reservationRepository.findAll(specification2);
        Assertions.assertEquals(0, reservations2.size());
    }

    @Test
    void testCheckAvailability_ItemAvailable() {
        // Test the method with a time period that doesn't overlap with existing reservations
        Long itemId = item.getId();
        LocalDateTime startDate = LocalDateTime.of(2023, 9, 5, 12, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 9, 15, 12, 0);

        Boolean isAvailable = reservationRepository.checkAvailability(itemId, startDate, endDate);

        // Assert that the item is available for the given time period
        Assertions.assertTrue(isAvailable);
    }

    @Test
    void testCheckAvailability_ItemNotAvailable() {
        // Test the method with a time period that overlaps with an existing reservation
        Long itemId = item.getId();
        LocalDateTime startDate = LocalDateTime.of(2023, 7, 5, 12, 0);
        LocalDateTime endDate = LocalDateTime.of(2023, 7, 15, 12, 0);

        Boolean isAvailable = reservationRepository.checkAvailability(itemId, startDate, endDate);

        // Assert that the item is not available for the given time period
        Assertions.assertFalse(isAvailable);
    }
}
