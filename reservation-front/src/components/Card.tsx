import React from "react";
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
                    <div className="card-body" style={{ height: "150px" }}>
                        <h5 className="card-title">{item.name}</h5>
                        <p className="card-text">
                            {item.description}
                        </p>
                        <a href="/#" className="btn btn-primary">Reserver</a>
                    </div>
                </div>
            </div>
        </div>


    );
};
