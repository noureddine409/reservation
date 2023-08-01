import './App.css';
import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import HomePage from './pages/Home';
import HeaderComponent from "./components/Header";
import SideBarComponent from "./components/SideBar";
import AddProduct from "./pages/Product/Add";
import ContactPage from './pages/Contact';
import ProfilePage from './pages/Profile';
import LoginPage from './pages/Login';
import RegisterPage from './pages/Register';
import ForgetPassword from './pages/ForgotPassword';
import ErrorPage from './pages/Error';
import SearchProductPage from "./pages/Product/Search";
import ShowProduct from "./pages/Product/ShowProduct";


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
        const allowedURLs = ['/', '/home', '/contact', '/f-a-q', '/profile', '/add-product', '/search-product','/show-product'];

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
                <Route path="/add-product" element={<AddProduct/>} />
                <Route path="/show-product" element={<ShowProduct/>} />
                <Route path="/contact" element={<ContactPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/forget-password" element={<ForgetPassword />} />
                <Route path="/search-product" element={<SearchProductPage/>} />
                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </>
    );
};

export default App;
