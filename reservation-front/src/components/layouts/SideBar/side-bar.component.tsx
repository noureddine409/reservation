import React, {Component} from 'react';


class SideBarComponent extends Component {
    render() {
        return (
            <aside id="sidebar"
                   className="sidebar"
            >
                <ul className="sidebar-nav" id="sidebar-nav">
                    <li className="nav-item">
                        <a className="nav-link collapsed">
                            <i className="bi bi-grid" />
                            <span>Home</span>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a
                            className="nav-link collapsed"
                            data-bs-target="#components-nav"
                            data-bs-toggle="collapse"
                            href="#"
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
                                <a>
                                    <i className="bi bi-circle" />
                                    <span>Badges</span>
                                </a>
                            </li>
                            <li>
                                <a >
                                    <i className="bi bi-circle" />
                                    <span>Breadcrumbs</span>
                                </a>
                            </li>
                            <li>
                                <a>
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
                            href="#"
                        >
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
                                <a>
                                    <i className="bi bi-circle" />
                                    <span>Badges</span>
                                </a>
                            </li>
                            <li>
                                <a >
                                    <i className="bi bi-circle" />
                                    <span>Breadcrumbs</span>
                                </a>
                            </li>
                            <li>
                                <a>
                                    <i className="bi bi-circle" />
                                    <span>Buttons</span>
                                </a>
                            </li>
                        </ul>
                    </li>

                    <li className="nav-item">
                        <a className="nav-link collapsed">
                            <i className="bi bi-person" />
                            <span>Profile</span>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link collapsed" href="#">
                            <i className="bi bi-question-circle" />
                            <span>F.A.Q</span>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link collapsed">
                            <i className="bi bi-envelope" />
                            <span>Contact</span>
                        </a>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link collapsed">
                            <i className="bi bi-exclamation-triangle" />
                            <span>Help</span>
                        </a>
                    </li>
                </ul>
            </aside>
        );
    }
}

export default SideBarComponent;
