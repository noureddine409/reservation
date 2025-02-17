import React from 'react';
import {Link} from "react-router-dom"

const SideBarComponent = () => {
    return (
        <aside id="sidebar"
               className="sidebar"
        >
            <ul className="sidebar-nav" id="sidebar-nav">
                <li className="nav-item">
                    <Link to="/" className="nav-link collapsed">
                        <i className="bi bi-grid"/>
                        <span>Home</span>
                    </Link>
                </li>
                <li className="nav-item">
                    <a
                        className="nav-link collapsed"
                        data-bs-target="#components-nav"
                        data-bs-toggle="collapse"
                        href="/#"
                    >
                        <i className="bi bi bi-box-seam"/>
                        <span>Products</span>
                        <i className="bi bi-chevron-down ms-auto"/>
                    </a>
                    <ul
                        id="components-nav"
                        className="nav-content collapse "
                        data-bs-parent="#sidebar-nav"
                    >
                        <li>
                            <Link to="/show-product">
                                <i className="bi bi-circle"/>
                                <span>My Product</span>
                            </Link>
                        </li>
                        <li>
                            <Link to="/search-product">
                                <i className="bi bi-circle"/>
                                <span>Search Product</span>
                            </Link>
                        </li>
                    </ul>
                </li>
                <li className="nav-item">
                    <Link to="/" className="nav-link collapsed">
                        <i className="bi bi-grid"/>
                        <span>Reservations</span>
                    </Link>
                </li>

                <li className="nav-item">
                    <Link className="nav-link collapsed" to="/profile">
                        <i className="bi bi-person"/>
                        <span>Profile</span>
                    </Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link collapsed" to="/contact">
                        <i className="bi bi-envelope"/>
                        <span>Contact</span>
                    </Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link collapsed" to="f-a-q">
                        <i className="bi bi-question-circle"/>
                        <span>F.A.Q</span>
                    </Link>
                </li>

            </ul>
        </aside>
    );
}

export default SideBarComponent;
