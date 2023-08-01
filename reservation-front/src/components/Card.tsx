import React from "react";
import {Link} from "react-router-dom";

type CardProps = {
    item: {

        name: string;
        description: string;
        category: string;
        status: string;
    }
}

export const Card = ({
                         item
                     }: CardProps) => {
    return (

        <div className="card mb-3">
            <div className="row g-0">

                <div className="col-md-8">
                    <div className="card-body" style={{height: "150px"}}>
                        <h5 className="card-title">{item.name}</h5>
                        <p className="card-text">
                            {item.description}
                        </p>
                        <Link to="/product-details" className="btn btn-primary">Reserver</Link>
                    </div>
                </div>
            </div>
        </div>
    );
};
