import React, { useState, useEffect } from "react";
import Fullcalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import { formatDateToString } from "../utils/dateTime-conversion";
import { ItemReservationSearch, Period, Reservation, UserReservationSearch } from "../model/reservation.model";
import reservationService from "../services/reservation-service/reservation.service";
import { DateClickArg } from "@fullcalendar/interaction";
import ReservationPopup from "./ReservationPopup";


const Calendar = () => {
    const [reservations, setReservations] = useState<Reservation[]>([]);
    const [currentPeriod, setCurrentPeriod] = useState<Period | undefined>();
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [isPopupOpen, setPopupOpen] = useState(false); // Ensure this state is defined

    useEffect(() => {
        const requestBody: ItemReservationSearch = {
            itemId: 1,
            period: currentPeriod!,
        };
        reservationService
            .findByItem(requestBody)
            .then((response) => {
                setReservations(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [currentPeriod]);

    const convertToEvents = (reservations: Reservation[]) => {
        return reservations.map((reservation) => {
            return {
                title: reservation.item.name,
                start: reservation.period.startDate,
                end: reservation.period.endDate,
            };
        });
    };

    const handleDateClick = (arg: DateClickArg) => { // Utiliser DateClickArg
        const date = arg.date; // Récupérer la date à partir de l'objet DateClickArg
        setSelectedDate(date);
        setPopupOpen(true);
    };



    return (
        <div>
            <Fullcalendar
                plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                headerToolbar={{
                    start: "today prev,next",
                    center: "title",
                    end: "timeGridWeek",
                }}
                height="90vh"
                events={convertToEvents(reservations)}
                datesSet={(args) => {
                    setCurrentPeriod({
                        startDate: formatDateToString(args.start),
                        endDate: formatDateToString(args.end),
                    });
                }}
                dateClick={handleDateClick}
            />
            {isPopupOpen && (
                <ReservationPopup
                    selectedDate={selectedDate}
                    onClose={() => setPopupOpen(false)}
                />
            )}
        </div>
    );
};

export default Calendar;