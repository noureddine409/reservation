import React from "react";
import {useNavigate} from "react-router-dom";
import {Item} from "../model/item.model";

interface CardProps {
    item: Item
}

const Card = (props: CardProps) => {
    const navigate = useNavigate();

    const handleReserveClick = () => {
        navigate("/product-details", { state: props.item  });
    };
    return (
    <div className="card mb-3">
            <div className="row g-0">
                <div className="col-md-40">
                    <img src="https://kreconcept.fr/wp-content/uploads/2022/11/KRE_bg_espace_cuisine.jpg" className="img-fluid rounded-start" alt={props.item.name} />
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{props.item.name}</h5>
                        <p className="card-text">
                            {props.item.description}
                        </p>
                        <div onClick={handleReserveClick} className="btn btn-primary">Book Now</div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Card;
