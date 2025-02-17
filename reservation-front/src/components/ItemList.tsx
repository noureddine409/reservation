import React from "react";
import Card from "./Card";
import {Item} from "../model/item.model";


interface ItemListProps {
    items: Item[];
}

const ItemList: React.FC<ItemListProps> = ({ items }) => {
    return (
        <section className="section">
            <div className="container">
                <div className="row">
                    {items.map((item) => (
                        <div key={item.id!} className="col-md-4">
                            {/* Custom card styles to ensure fixed size and text overflow */}
                            <div className="card-container">
                                <Card item={item} />
                            </div>
                        </div>
                    ))}
                </div>
            </div>
        </section>
    );
};

export default ItemList;
