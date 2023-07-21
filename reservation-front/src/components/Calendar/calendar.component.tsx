import React from "react";
import Fullcalendar from "@fullcalendar/react";
import dayGridPlugin from "@fullcalendar/daygrid";
import timeGridPlugin from "@fullcalendar/timegrid";
import interactionPlugin from "@fullcalendar/interaction";

function Calendar() {
    const handleEventClick = (info:any) => {
        alert('Event: ' + info.event.title);
        alert('Coordinates: ' + info.jsEvent.pageX + ',' + info.jsEvent.pageY);
        alert('View: ' + info.view.type);

        // change the border color just for fun
        info.el.style.borderColor = 'red';
    };

    const handleSelect = (info : any) => {
        alert('selected ' + info.startStr + ' to ' + info.endStr + ' on resource ' + info.resource.id);
    };

    return (
        <div>
            <Fullcalendar
                plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
                initialView={"dayGridMonth"}
                selectable={true}
                headerToolbar={{

                    start: "today prev,next", // will normally be on the left. if RTL, will be on the right
                    center: "title",
                    end: "dayGridMonth,timeGridWeek,timeGridDay", // will normally be on the right. if RTL, will be on the left
                }}
                events={[
                    { title: 'Cuisine réserver', date: '2023-07-18T12:00:00' },
                    { title: 'Salle de réuinion réserver', date: '2023-07-20T15:00:00' },
                    { title: 'Salle de réuinion réserver', date: '2023-08-20T15:00:00' },
                    // Ajoutez plus d'événements ici
                ]}
                eventClick={handleEventClick} // Add the event click handler here
                select={(val)=> alert(val.start.toJSON())}
                // dateClick={handleDateClick}
                // eventClick={handleEventClick}
                height={"90vh"}
            />
        </div>
    );
}

export default Calendar;