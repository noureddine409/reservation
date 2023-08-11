import { format } from 'date-fns';
import {Reservation} from "../model/reservation.model";

export const formatDateToString = (date: Date) => {
    return format(date, 'yyyy-MM-dd\'T\'HH:mm');
}

export const convertToEvents = (reservations: Reservation[]) => {
    return reservations.map((reservation) => {
        return {
            title: reservation.item.name,
            start: reservation.period.startDate,
            end: reservation.period.endDate,
        };
    });
};
