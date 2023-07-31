import {ItemReservationSearch, Reservation, UserReservationSearch} from "../../model/reservation.model";
import {GenericService} from "../generic.service";

class ReservationService extends GenericService{

    async createReservation(reservation: Reservation): Promise<Reservation> {
        return this.post<Reservation>('reservations', reservation);
    }

    async searchReservationsByItem(search: ItemReservationSearch): Promise<Reservation[]> {
        return this.post<Reservation[]>('reservations/items', search);
    }

    async searchReservationsByUser(search: UserReservationSearch): Promise<Reservation[]> {
        return this.post<Reservation[]>('reservations/users', search);
    }
}
