import React from "react";
type CardProps = {
    item: {
        imgSrc: string;
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
                <div className="col-md-40">
                    <img src={item.imgSrc} className="img-fluid rounded-start" alt={item.name} />
                </div>
                <div className="col-md-8">
                    <div className="card-body">
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
