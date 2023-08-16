import React from "react";
import {Reservation} from "../model/reservation.model";

interface ReservationPopupProps {

    reservation: Reservation;
    onSelect: (reservation: Reservation) => void;
    onClose: () => void;
    onDelete: () => void;

}

const ReservationPopup: React.FC<ReservationPopupProps> = ({
                                                               reservation,
                                                               onSelect,
                                                               onClose,
                                                               onDelete, // Ajoutez cette prop pour gérer la suppression
                                                           }) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        // ... (Gérer la réservation ici)
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <div className="event" onClick={() => onSelect(reservation)}>
                    <p>{reservation.item.name}</p>
                    <p>
                        {reservation.period.startDate} - {reservation.period.endDate}
                    </p>
                </div>
                <h3>Réservation</h3>
                <p>Produit : {reservation.item.name}</p>
                <p>Date : {reservation.period.startDate} - {reservation.period.endDate}</p>
                <button className="btn btn-danger" onClick={onDelete}>
                    Supprimer
                </button>
                <button className="btn btn-secondary" onClick={onClose}>
                    Fermer
                </button>
            </div>
        </div>
    );
};

export default ReservationPopup
