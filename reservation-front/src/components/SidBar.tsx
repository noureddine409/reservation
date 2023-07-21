import React, {Component} from 'react';
import {Link} from "react-router-dom"

const SideBarComponent = () => {
        return (
            <aside id="sidebar"
                   className="sidebar"
            >
                <ul className="sidebar-nav" id="sidebar-nav">
                    <li className="nav-item">
                        <Link to="/" className="nav-link collapsed">
                            <i className="bi bi-grid" />
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
                            <i className="bi bi bi-box-seam" />
                            <span>Products</span>
                            <i className="bi bi-chevron-down ms-auto" />
                        </a>
                        <ul
                            id="components-nav"
                            className="nav-content collapse "
                            data-bs-parent="#sidebar-nav"
                        >
                            <li>
                                <Link to="/add-product">
                                    <i className="bi bi-circle" />
                                    <span>Add product</span>
                                </Link>
                            </li>
                            <li>
                                <a href="/#">
                                    <i className="bi bi-circle" />
                                    <span>Breadcrumbs</span>
                                </a>
                            </li>
                            <li>
                                <a href="/#">
                                    <i className="bi bi-circle" />
                                    <span>Buttons</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <a
                            className="nav-link collapsed"
                            data-bs-target="#tables-nav"
                            data-bs-toggle="collapse"
                            href="/#"                        >
                            <i className="bi bi-calendar2-date-fill" />
                            <span>Reservations</span>
                            <i className="bi bi-chevron-down ms-auto" />
                        </a>
                        <ul
                            id="tables-nav"
                            className="nav-content collapse "
                            data-bs-parent="#sidebar-nav"
                        >
                            <li>
                                <a href="/#">
                                    <i className="bi bi-circle" />
                                    <span>Badges</span>
                                </a>
                            </li>
                            <li>
                                <a href="/#">
                                    <i className="bi bi-circle" />
                                    <span>Breadcrumbs</span>
                                </a>
                            </li>
                            <li>
                                <a href="/#">
                                    <i className="bi bi-circle" />
                                    <span>Buttons</span>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/profile">
                            <i className="bi bi-person" />
                            <span>Profile</span>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/contact">
                            <i className="bi bi-envelope" />
                            <span>Contact</span>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="f-a-q">
                            <i className="bi bi-question-circle" />
                            <span>F.A.Q</span>
                        </Link>
                    </li>

                </ul>
            </aside>
        );
}

export default SideBarComponent;
