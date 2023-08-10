import './App.css';
import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes, useLocation} from 'react-router-dom';
import HeaderComponent from "./components/Header";
import SideBarComponent from "./components/SideBar";
import {routesConfig} from "./routes/routes-config";
import RouteGuardWrapper from "./guards/RouteGuardWrapper";


const App = () => {
    const [sideBarToggled, setSideBarToggled] = useState(false);

    const toggleSideBar = () => {
        setSideBarToggled(!sideBarToggled);
    };


    return (
        <div className={sideBarToggled ? 'toggle-sidebar' : ''}>

            <Router>
                <AppContent toggleSideBar={toggleSideBar} sideBarToggled={sideBarToggled}/>
            </Router>
        </div>
    );
};

interface AppContentProps {
    toggleSideBar: () => void;
    sideBarToggled: boolean;
}

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
                {routesConfig.map((route) => (
                    <Route
                        key={route.path}
                        path={route.path}
                        element={
                            <RouteGuardWrapper guard={route.guard} component={route.component}/>
                        }
                    />
                ))}
            </Routes>
        </>
    );
};

export default App;
