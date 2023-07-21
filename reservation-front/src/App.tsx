import './App.css';
import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import HomePage from './pages/Home/home.page';
import ContactPage from './pages/Contact/contact.page';
import FAQPage from './pages/FrequentlyAskedQuestions/faq.page';
import ProfilePage from './pages/Profile/profile.page';
import ErrorPage from './pages/Error-404/error.page';
import HeaderComponent from './components/layouts/Header/header.component';
import SideBarComponent from './components/layouts/SideBar/side-bar.component';
import LoginPage from './pages/Login/login.page';
import RegisterPage from './pages/Register/register.page';
import ForgetPasswordPage from './pages/ForgetPassword/forget-password.page';

const App = () => {
    const [sideBarToggled, setSideBarToggled] = useState(false);

    const toggleSideBar = () => {
        setSideBarToggled(!sideBarToggled);
    };

    return (
        <div className={sideBarToggled ? 'toggle-sidebar' : ''}>
            <Router>
                <AppContent toggleSideBar={toggleSideBar} sideBarToggled={sideBarToggled} />
            </Router>
        </div>
    );
};

interface AppContentProps {
    toggleSideBar: () => void;
    sideBarToggled: boolean;
}

const AppContent: React.FC<AppContentProps> = ({ toggleSideBar }) => {
    const location = useLocation();

    const shouldDisplayHeaderAndSidebar = () => {
        const { pathname } = location;
        const allowedURLs = ['/', '/home', '/contact', '/f-a-q', '/profile'];

        return allowedURLs.includes(pathname);
    };

    return (
        <>
            {shouldDisplayHeaderAndSidebar() && (
                <>
                    <HeaderComponent change={toggleSideBar} />
                    <SideBarComponent />

                </>
            )}
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/contact" element={<ContactPage />} />
                <Route path="/f-a-q" element={<FAQPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/forget-password" element={<ForgetPasswordPage />} />
                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </>
    );
};

export default App;
