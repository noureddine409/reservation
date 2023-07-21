import React, {Component} from 'react';
import {Link} from "react-router-dom"


class ForgetPasswordPage extends Component {
    render() {
        return (
            <main>
                <div className="container">
                    <section
                        className="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
                        <div className="container">
                            <div className="row justify-content-center">
                                <div
                                    className="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">
                                    <div className="d-flex justify-content-center py-4">
                                        <Link
                                            to="/"
                                            className="logo d-flex align-items-center w-auto"
                                        >
                                            <img alt=""/>
                                            <span className="d-none d-lg-block">NorSys</span>
                                        </Link>
                                    </div>
                                    {/* End Logo */}
                                    <div className="card mb-3">
                                        <div className="card-body">
                                            <div className="pt-4 pb-2">
                                                <h5 className="card-title text-center pb-0 fs-4">
                                                    Forget Password
                                                </h5>
                                                <p className="text-center small">
                                                    Enter your email to reset your password
                                                </p>
                                            </div>
                                            <form className="row g-3 needs-validation">
                                                <div className="col-12">
                                                    <label htmlFor="yourUsername" className="form-label">
                                                        Email Address
                                                    </label>
                                                    <div className="input-group has-validation">
                      <span className="input-group-text" id="inputGroupPrepend">
                        @
                      </span>
                                                        <input
                                                            type="text"
                                                            name="username"
                                                            className="form-control"
                                                            id="yourUsername"

                                                        />
                                                        <div className="invalid-feedback">
                                                            Please enter your username.
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="col-12">
                                                    <button className="btn btn-primary w-100" type="submit">
                                                        Confirm
                                                    </button>
                                                </div>
                                                <div className="col-12">
                                                    <p className="small mb-0">
                                                        <Link to="/login">Back to login</Link>
                                                    </p>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </main>


        );
    }
}

export default ForgetPasswordPage;
