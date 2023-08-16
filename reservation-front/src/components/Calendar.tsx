import React, {useState, useEffect} from "react";
import Fullcalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import {ItemReservationSearch, Period, Reservation, ReservationRequest} from "../model/reservation.model";
import reservationService from "../services/reservation-service/reservation.service";
import interactionPlugin, {DateClickArg} from "@fullcalendar/interaction";
import {convertToEvents, formatDateToString} from "../utils/reservationUtils";
import ReservationPopup from "./ReservationPopup";
import {DateSelectArg, EventClickArg} from "@fullcalendar/core";
import {Item} from "../model/item.model";
import {User} from "../model/user.model";

interface CalendarProps {
    itemId: number; // Assurez-vous que le type correspond au type attendu
}

const Calendar: React.FC<CalendarProps> = ({itemId}) => {
    const [reservations, setReservations] = useState<Reservation[]>([]);
    const [currentPeriod, setCurrentPeriod] = useState<Period | undefined>();
    const [selectedDate, setSelectedDate] = useState<Date | null>(null);
    const [isPopupOpen, setPopupOpen] = useState(false); // Ensure this state is defined
    const [selectedReservation, setSelectedReservation] = useState<Reservation | null>(null);


    useEffect(() => {
        const requestBody: ItemReservationSearch = {
            itemId: itemId,
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
    }, [currentPeriod, itemId]);



    const handleDateClick = (arg: DateClickArg) => { // Utiliser DateClickArg
        const date = arg.date; // Récupérer la date à partir de l'objet DateClickArg
        setSelectedDate(date);
        setPopupOpen(true);
    };


    const handleDateSelection = (dates: DateSelectArg) => {

        const reservation: ReservationRequest = {
            period: {
                startDate: formatDateToString(dates.start),
                endDate: formatDateToString(dates.end)
            },
            item: {
                id: itemId
            },
            user: {
                id: 1
            }
        }


        reservationService.addReservation(reservation).then(
            response => {
                // TODO handle save

                // reservations . add (response.data)
                const newReservation: Reservation = response.data;
                setReservations([...reservations, newReservation]);
            }
        ).catch(
            error => {
                // TODO handle save
                console.error("Erreur lors de la réservation :", error);

            }
        )

    }
    const handleEventClick = (clickInfo: EventClickArg) => {
        const clickedEvent = clickInfo.event;
        const reservation = clickedEvent.extendedProps.reservation;
        setSelectedReservation(reservation);
        setPopupOpen(true);
    };



    const handleReservationDeletion = () => {
        if (selectedReservation) {
            reservationService.deleteReservation(selectedReservation.id)
                .then(() => {
                    const updatedReservations = reservations.filter(res => res.id !== selectedReservation.id);
                    setReservations(updatedReservations);
                    setPopupOpen(false); // Fermez la popup après la suppression
                })
                .catch(error => {
                    console.error("Erreur lors de la suppression de la réservation :", error);
                });
        }
    };




    return (
        <div>
            <Fullcalendar
                selectable={true}
                select={handleDateSelection}
                plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
                initialView="dayGridMonth"
                headerToolbar={{
                    start: "today prev,next",
                    center: "title",
                    end: "timeGridWeek",
                }}
                height="90vh"
                events={convertToEvents(reservations)}
                dateClick={handleDateClick}
                eventClick={handleEventClick}


                datesSet={(args) => {
                    setCurrentPeriod({
                        startDate: formatDateToString(args.start),
                        endDate: formatDateToString(args.end),
                    });
                }}




            />
            {isPopupOpen && selectedReservation && (
                <ReservationPopup
                    reservation={selectedReservation}
                    onSelect={() => {}}
                    onClose={() => setPopupOpen(false)}
                    onDelete={handleReservationDeletion}
                />
            )}

            {reservations.map((reservation) => (
                <div key={reservation.id}>
                    <p>{reservation.item.name}</p>
                    <p>{reservation.period.startDate} - {reservation.period.endDate}</p>
                    <button onClick={() => {
                        setSelectedReservation(reservation); // Mettre à jour la réservation sélectionnée pour la suppression
                        setPopupOpen(true); // Ouvrir la popup de suppression
                    }}>
                        Supprimer
                    </button>
                </div>

            ))}

        </div>
    );
};

export default Calendar;
