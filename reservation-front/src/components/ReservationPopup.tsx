import React from "react";

interface ReservationPopupProps {
    selectedDate: Date | null;
    onClose: () => void;
}

const ReservationPopup: React.FC<ReservationPopupProps> = ({
                                                               selectedDate,
                                                               onClose,
                                                           }) => {
    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        // Handle the form submission here...
        // You can get the selected date and time values from the form fields
        console.log("Selected Date:", selectedDate);
        onClose();
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <h3>Reserve {selectedDate?.toLocaleDateString()}</h3>
                <form onSubmit={handleSubmit}>
                    {/* Add your form fields for selecting end date and time here */}
                    <div className="form-group">
                        <label htmlFor="endDate">Booking end date:</label>
                        <input type="date" id="endDate" className="form-control" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="startTime"> Start Time :</label>
                        <input type="time" id="startTime" className="form-control" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="endTime">End Time :</label>
                        <input type="time" id="endTime" className="form-control" />
                    </div>
                    <button type="submit" className="btn btn-primary">
                        Réserver

                    </button>
                    <button className="btn btn-danger" onClick={onClose}>
                        Annuler
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ReservationPopup;
