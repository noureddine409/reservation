import {ItemReservationSearch, UserReservationSearch} from "../../model/reservation.model";
import httpClient from "../../middleware/auth";


const ReservationService = {
    findByUser: async (search: UserReservationSearch) => {
        try {
            return await httpClient.post("/reservations/users", search);
        } catch (error) {
            throw error;
        }
    },
    findByItem: async (search: ItemReservationSearch) => {
        try {
            return await httpClient.post("/reservations/items", search);
        } catch (error) {
            throw error;
        }
    }

}

export default ReservationService;
