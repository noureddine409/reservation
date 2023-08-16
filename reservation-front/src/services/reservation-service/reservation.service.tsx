import axios from "axios";
import {ItemReservationSearch, ReservationRequest, UserReservationSearch} from "../../model/reservation.model";
import interceptedAxios from "../../middleware/axios-auth-config";


const baseUrl: string = process.env.REACT_APP_API_URL!;



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
    },
    addReservation: async (reservation: ReservationRequest ) => {
        try {
            return await axios.post(`${baseUrl}/reservations`, reservation, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    deleteReservation: async ( reservationId: number | undefined) => {
        try {
            return await axios.delete(`${baseUrl}/reservations/${reservationId}`);
        } catch (error) {
            console.error('Error deleting reservation:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },




}

export default ReservationService;
