import './App.css';
import HeaderComponent from "./components/layouts/Header/header.component";
import SideBarComponent from "./components/layouts/SideBar/side-bar.component";
import React, {Component} from 'react';
import ProfilePage from "./pages/Profile/profile.page";
import ContactPage from "./pages/Contact/contact.page";
import FAQPage from "./pages/FrequentlyAskedQuestions/faq.page";
import ErrorPage from "./pages/Error-404/error.page";
import HomePage from "./pages/Home/home.page";

interface sideBarToggledState {
    sideBarToggled: boolean
}

class App extends Component<{}, sideBarToggledState> {

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
    }
    render() {
        const isToggleSidebar = this.state.sideBarToggled;
        const className = isToggleSidebar ? "toggle-sidebar" : "";
        return (
            <div className={className}>
                <HeaderComponent change={this.toggleSideBar}/>
                <SideBarComponent/>
                <HomePage/>

            </div>
        );
    }
}

export default App;
