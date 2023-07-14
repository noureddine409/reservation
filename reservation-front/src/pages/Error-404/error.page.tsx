import React, {Component} from 'react';
import "./error.page.css"
class ErrorPage extends Component {
    render() {
        return (
            <main>
                <div className="container">
                    <section className="section error-404 min-vh-100 d-flex flex-column align-items-center justify-content-center">
                        <h1>404</h1>
                        <h2>The page you are looking for doesn't exist.</h2>
                        <a className="btn" href="index.html">
                            Back to home
                        </a>
                        <img
                            src="assets/img/not-found.svg"
                            className="img-fluid py-5"
                            alt="Page Not Found"
                        />
                    </section>
                </div>
            </main>

        );
    }
}

export default ErrorPage;
