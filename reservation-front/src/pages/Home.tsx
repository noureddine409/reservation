import React from 'react';
import Calendar from '../components/Calendar';

const HomePage = () => {
    return (
        <main id="main" className="main">
            <div className="pagetitle">
                <h1>Calendar</h1>
            </div>
            {/* End Page Title */}
            <section className="section">
                <div className="row">

                    <div className="App">
                        <Calendar />
                    </div>
                </div>
            </section>
        </main>
    );
}

export default HomePage;
