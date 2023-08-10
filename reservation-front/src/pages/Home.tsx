import React, {useEffect, useState} from 'react';
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import Fullcalendar from "@fullcalendar/react";
import {Period, Reservation, UserReservationSearch} from "../model/reservation.model";
import {convertToEvents, formatDateToString} from "../utils/reservationUtils";
import ReservationService from "../services/reservation-service/reservation.service";

const HomePage = () => {


    const [reservations, setReservations] = useState<Reservation[]>([])

    const [currentPeriod, setCurrentPeriod] = useState<Period>()

    useEffect(() => {
        if (!currentPeriod) return;
        const requestBody: UserReservationSearch = {
            userId: 1,
            period: currentPeriod!
        }
        ReservationService.findByUser(requestBody).then((response) => {
            setReservations(response.data);
        }).catch((error) => {
            console.log(error);
        });
    }, [currentPeriod]);





    return (
        <main id="main" className="main">
            <div className="pagetitle">
                <h1>Calendar</h1>
            </div>
            <section className="section">
                <div className="row">

                    <div className="App">
                        <Fullcalendar
                            plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
                            initialView={"dayGridMonth"}
                            headerToolbar={{
                                start: "today prev,next",
                                center: "title",
                                end: "dayGridMonth,timeGridWeek,timeGridDay",
                            }}
                            height={"90vh"}
                            events={
                                convertToEvents(reservations)
                            }
                            datesSet={
                                (args => {
                                    setCurrentPeriod(
                                        {
                                            startDate: formatDateToString(args.start),
                                            endDate: formatDateToString(args.end)
                                        }
                                    )
                                })
                            }
                        />
                    </div>
                </div>
            </section>
        </main>
    );
}

export default HomePage;
