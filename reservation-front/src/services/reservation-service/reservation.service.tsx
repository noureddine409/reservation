import {ItemReservationSearch, UserReservationSearch} from "../../model/reservation.model";
import interceptedAxios from "../../middleware/axios-auth-config";


const ReservationService = {
    findByUser: async (search: UserReservationSearch) => {
        try {
            return await interceptedAxios.post("/reservations/users", search);
        } catch (error) {
            throw error;
        }
    },
    findByItem: async (search: ItemReservationSearch) => {
        try {
            return await interceptedAxios.post("/reservations/items", search);
        } catch (error) {
            throw error;
        }
    }

}

export default ReservationService;
