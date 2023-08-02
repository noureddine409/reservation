import React, {useEffect, useState} from 'react';
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";
import Fullcalendar from "@fullcalendar/react";
import {Period, Reservation, UserReservationSearch} from "../model/reservation.model";
import {formatDateToString} from "../utils/dateTime-conversion";
import reservationService from "../services/reservation-service/reservation.service";

const HomePage = () => {

    const [reservations, setReservations] = useState<Reservation[]>([])

    const [currentPeriod, setCurrentPeriod] = useState<Period>()

    useEffect(() => {
        const requestBody: UserReservationSearch = {
            userId: 1,
            period: currentPeriod!
        }
        reservationService.findByUser(requestBody).then((response) => {
            setReservations(response.data);
        }).catch((error) => {
            console.log(error);
        });
    }, [currentPeriod]);


    const convertToEvents = (reservations: Reservation[]) => {
        return reservations.map(reservation => {
            return {
                title: reservation.item.name,
                start: reservation.period.startDate,
                end: reservation.period.endDate,
            };
        });
    }


    return (
        <main id="main" className="main">
            <div className="pagetitle">
                <h1>Calendar</h1>
            </div>
            {/* End Page Title */}
            <section className="section">
                <div className="row">

                    <div className="App">
                        <Fullcalendar
                            plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
                            initialView={"dayGridMonth"}
                            headerToolbar={{
                                start: "today prev,next", // will normally be on the left. if RTL, will be on the right
                                center: "title",
                                end: "dayGridMonth,timeGridWeek,timeGridDay", // will normally be on the right. if RTL, will be on the left
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
