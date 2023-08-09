import React from "react";
import {Link, useNavigate} from "react-router-dom";
import {Interface} from "readline";
import {Button} from "react-bootstrap";


interface ItemData {
    imgSrc: string;
    name: string;
    description: string;
    category: string;
    status: string;
}
type CardProps = {
    item: ItemData;
};
export const Card = ({
                         item
                     }: CardProps) => {
    const navigate = useNavigate();

    const handleReserveClick = () => {
        navigate("/product-details", { state: { item } });
    };
    return (

        <div className="card mb-3">
            <div className="row g-0">
                <div className="col-md-40">
                    <img src={item.imgSrc} className="img-fluid rounded-start" alt={item.name} />
                </div>
                <div className="col-md-8">
                    <div className="card-body">
                        <h5 className="card-title">{item.name}</h5>
                        <p className="card-text">
                            {item.description}
                        </p>

                        <Button onClick={handleReserveClick} className="btn btn-primary">Reserver</Button>
                    </div>
                </div>
            </div>
        </div>


    );
};
