import axios from "axios";
import {UserReservationSearch} from "../../model/reservation.model";
import httpClient from "../../middleware/auth";
const baseUrl: string = process.env.REACT_APP_API_URL!;



const ReservationService = {
    findByUser: async (search: UserReservationSearch) => {
        try {
            return await httpClient.post("/reservations/users", search, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    }

}

export default ReservationService;
