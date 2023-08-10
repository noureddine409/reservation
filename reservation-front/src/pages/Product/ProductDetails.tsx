import React from 'react';
import Calendar from "../../components/Calendar";
import {useLocation} from "react-router-dom";
import {Item} from "../../model/item.model";




const ProductDetails = ()=> {
    // const item = {
    //     imgSrc: 'https://kreconcept.fr/wp-content/uploads/2022/11/KRE_bg_espace_cuisine.jpg',
    //     name: 'Kitchen',
    //     description: 'You can reserve your kitchen at your convenience. Please select a date and time that suits you best, and we will ensure it\'s available for your use.',
    //     category: 'Room',
    //     status: 'Available',
    // };

        const location = useLocation();

        const item: Item = location.state;
    return (
        <main id="main" className="main">
            <div className="pagetitle">
                <h1>Detail Product</h1>
            </div>
            {/* End Page Title */}
            <section className="section contact">
                <div className="row gy-4">
                    <div className="row gy-4">
                        <div className="row">
                            <div className="card mb-3">

                                <img src="https://kreconcept.fr/wp-content/uploads/2022/11/KRE_bg_espace_cuisine.jpg" className="full-width-image " alt={item.name} />

                                <div>
                                    <h5 className="card-title">{item.name}
                                        <h6 > (Can be booked only by 4  people)</h6>
                                        <div className="button-container">
                                            <button type="button" className="right-button" data-bs-toggle="modal"
                                                    data-bs-target="#fullscreenModal" style={{ textAlign: 'right' }}>
                                                Open calendar
                                            </button>
                                        </div></h5>

                                </div>
                                <div className="modal fade" id="fullscreenModal" tabIndex={-1} style={{ display: 'none' }} aria-hidden="true">
                                    <div className="modal-dialog modal-fullscreen">
                                        <div className="modal-content">
                                            <div className="modal-header">
                                                <h5 className="modal-title">Reserve your product</h5>
                                                <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div className="modal-body">
                                                <Calendar></Calendar>
                                            </div>
                                            <div className="modal-footer">
                                                <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                                <button type="button" className="btn btn-primary">Save changes</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <p className="card-text">
                                    {item.description}
                                </p>
                                <div className="card-title">
                                    <h4>Material available</h4>
                                    {/*<h6> -Four</h6>*/}
                                    {/*<h6> -Furnace and ventilation</h6>*/}
                                    {/*<h6> -Freezer and refrigerator</h6>*/}
                                    {/*<h6> -Mixer beater-mixer</h6>*/}
                                    {/*<h6> -slicer</h6>*/}
                                    {/*<h6> -Work tables and cutting boards</h6>*/}
                                    {/*<h6> -Microwave</h6>*/}
                                    <p className="list-group">
                                        <li className="list-group-item"><i
                                            className="bi bi-star me-1 text-success"></i>Four
                                        </li>
                                        <li className="list-group-item">
                                            <i className="bi bi-star me-1 text-success"></i>Furnace and ventilation
                                        </li>
                                        <li className="list-group-item"><i
                                            className="bi bi-star me-1 text-success"></i> Freezer and refrigerator
                                        </li>
                                        <li className="list-group-item"><i
                                            className="bi bi-star me-1 text-success"></i> Mixer beater-mixer
                                        </li>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </main>
    );
}
export default ProductDetails;