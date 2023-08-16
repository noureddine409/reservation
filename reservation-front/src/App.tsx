import './App.css';
import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom';
import HeaderComponent from './components/Header';
import SideBarComponent from './components/SideBar';
import { routesConfig } from './routes/routes-config';
import RouteGuardWrapper from './guards/RouteGuardWrapper';
import ContactPage from './pages/Contact';
import ProfilePage from './pages/Profile';
import LoginPage from './pages/Login';
import RegisterPage from './pages/Register';
import ForgetPassword from './pages/ForgotPassword';
import ProductDetails from './pages/Product/ProductDetails';
import SearchProductPage from './pages/Product/Search';
import ErrorPage from './pages/Error';
import HomePage from './pages/Home';
import ShowProduct from './pages/Product/ShowProduct';

const App = () => {
    const [sideBarToggled, setSideBarToggled] = useState(false);

    const toggleSideBar = () => {
        setSideBarToggled((prev) => !prev);
    };

    return (
        <div className={sideBarToggled ? 'toggle-sidebar' : ''}>
            <Router>
                <AppContent toggleSideBar={toggleSideBar} sideBarToggled={sideBarToggled} />
            </Router>
        </div>
    );
};

const AppContent: React.FC<AppContentProps> = ({toggleSideBar}) => {
    const location = useLocation();

    const shouldDisplayHeaderAndSidebar = () => {
        const {pathname} = location;
        const allowedURLs = ['/', '/home', "/product-details", '/contact', '/f-a-q', '/profile', '/add-product', '/search-product', '/show-product'];
        return allowedURLs.includes(pathname);
    };

    return (
        <>
            {shouldDisplayHeaderAndSidebar() && (
                <>
                    <HeaderComponent change={toggleSideBar}/>
                    <SideBarComponent/>
                </>
            )}
            <Routes>
                <Route path="/" element={<HomePage/>}/>
                {/*<Route path="/add-product" element={<AddProduct updateProductList={function(): void {*/}
                {/*    throw new Error('Function not implemented.');*/}
                {/*} }/>} />*/}
                <Route path="/show-product" element={<ShowProduct/>} />
                <Route path="/contact" element={<ContactPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route path="/forget-password" element={<ForgetPassword />} />
                <Route path="/search-product" element={<SearchProductPage/>} />
                <Route path="/product-details" element={<ProductDetails/>} />


                <Route path="*" element={<ErrorPage />} />
            </Routes>
        </>
    );
};

interface AppContentProps {
    toggleSideBar: () => void;
    sideBarToggled: boolean;
}

export default App;
