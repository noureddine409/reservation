import './App.css';
import React, {Component} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import HomePage from "./pages/Home/home.page";
import ContactPage from "./pages/Contact/contact.page";
import FAQPage from "./pages/FrequentlyAskedQuestions/faq.page";
import ProfilePage from "./pages/Profile/profile.page";
import ErrorPage from "./pages/Error-404/error.page";
import HeaderComponent from "./components/layouts/Header/header.component";
import SideBarComponent from "./components/layouts/SideBar/side-bar.component";
import LoginPage from "./pages/Login/login.page";
import RegisterPage from "./pages/Register/register.page";
import ForgetPasswordPage from "./pages/ForgetPassword/forget-password.page";

interface SideBarToggledState {
    sideBarToggled: boolean;
}

class App extends Component<{}, SideBarToggledState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            sideBarToggled: false
        };
    }

    toggleSideBar = () => {
        this.setState(prevState => ({
            sideBarToggled: !prevState.sideBarToggled
        }));
    };



    render() {
        const { sideBarToggled } = this.state;
        const className = sideBarToggled ? "toggle-sidebar" : "";
        const displayHeaderAndSidebarRoutes = ['/', '/home', '/contact', '/f-a-q', '/profile'];


        return (
            <Router>
                {displayHeaderAndSidebarRoutes.includes(window.location.pathname) && (
                    <div className={className}>
                        <HeaderComponent change={this.toggleSideBar} />
                        <SideBarComponent />
                    </div>
                )}
                <Routes >
                    <Route path="/" element={<HomePage />} />
                    <Route path="/contact" element={<ContactPage />} />
                    <Route path="/f-a-q" element={<FAQPage />} />
                    <Route path="/profile" element={<ProfilePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />
                    <Route path="/forget-password" element={<ForgetPasswordPage />} />
                    <Route path="*" element={<ErrorPage />} />
                </Routes>
            </Router>
        );
    }
}

export default App;
