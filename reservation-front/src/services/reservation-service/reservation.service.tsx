import axios from "axios";
import {ItemReservationSearch, UserReservationSearch} from "../../model/reservation.model";
const baseUrl: string = process.env.REACT_APP_API_URL!;



const ReservationService = {
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
    },
    findByItem: async (search: ItemReservationSearch) => {
        try {
            return await axios.post(`${baseUrl}/reservations/items`, search, {
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
