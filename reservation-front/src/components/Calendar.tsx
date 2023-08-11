import React, {useEffect, useState} from "react";
import Fullcalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin, {DateClickArg} from "@fullcalendar/interaction";
import {convertToEvents, formatDateToString} from "../utils/reservationUtils";
import {ItemReservationSearch, Period, Reservation} from "../model/reservation.model";
import reservationService from "../services/reservation-service/reservation.service";
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
