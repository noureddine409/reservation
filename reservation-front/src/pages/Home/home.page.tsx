import React, {Component} from 'react';

class HomePage extends Component {
    render() {
        return (
                <main id="main" className="main">
                    <div className="pagetitle">
                        <h1>Home</h1>
                    </div>
                    {/* End Page Title */}
                    <section className="section">
                        <div className="row">
                            <div className="col-lg-6">
                                <div className="card">
                                    <div className="card-body">
                                        <h5 className="card-title">Example Card</h5>
                                        <p>
                                            This is an examle page with no contrnt. You can use it as a
                                            starter for your custom pages.
                                        </p>
                                    </div>
                                </div>
                            </div>
                            <div className="col-lg-6">
                                <div className="card">
                                    <div className="card-body">
                                        <h5 className="card-title">Example Card</h5>
                                        <p>
                                            This is an examle page with no contrnt. You can use it as a
                                            starter for your custom pages.
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </main>
        );
    }
}

export default HomePage;
