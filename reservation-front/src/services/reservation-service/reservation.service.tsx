import axios from "axios";
import {UserReservationSearch} from "../../model/reservation.model";
const baseUrl: string = process.env.REACT_APP_API_URL!;



const reservationService = {
    findByUser: async (search: UserReservationSearch) => {
        try {
            return await axios.post(`${baseUrl}/reservations/users`, search, {
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
export default reservationService;