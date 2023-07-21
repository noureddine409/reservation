import React from 'react';
import { Link } from "react-router-dom"

const LoginPage = () => {
    return (
        <main>
            <div className="container">
                <section className="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">
                    <div className="container">
                        <div className="row justify-content-center">
                            <div className="col-lg-4 col-md-6 d-flex flex-column align-items-center justify-content-center">
                                <div className="d-flex justify-content-center py-4">
                                    <Link
                                        to="/"
                                        className="logo d-flex align-items-center w-auto"
                                    >
                                        <img alt="" />
                                        <span className="d-none d-lg-block">NorSys</span>
                                    </Link>
                                </div>
                                {/* End Logo */}
                                <div className="card mb-3">
                                    <div className="card-body">
                                        <div className="pt-4 pb-2">
                                            <h5 className="card-title text-center pb-0 fs-4">
                                                Login to Your Account
                                            </h5>
                                            <p className="text-center small">
                                                Enter your Email &amp; password to login
                                            </p>
                                        </div>
                                        <form className="row g-3 needs-validation">
                                            <div className="col-12">
                                                <label htmlFor="yourUsername" className="form-label">
                                                    Email
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
                                                <label htmlFor="yourPassword" className="form-label">
                                                    Password
                                                </label>
                                                <input
                                                    type="password"
                                                    name="password"
                                                    className="form-control"
                                                    id="yourPassword"
                                                />
                                                <div className="invalid-feedback">
                                                    Please enter your password!
                                                </div>
                                            </div>
                                            <div className="col-12">
                                                <div className="form-check">
                                                    <input
                                                        className="form-check-input"
                                                        type="checkbox"
                                                        name="remember"
                                                        defaultValue="true"
                                                        id="rememberMe"
                                                    />
                                                    <label className="form-check-label" htmlFor="rememberMe">
                                                        Remember me
                                                    </label>
                                                </div>
                                            </div>
                                            <div className="col-12">
                                                <button className="btn btn-primary w-100" type="submit">
                                                    Login
                                                </button>
                                            </div>
                                            <div className="col-12">
                                                <p className="small mb-0">
                                                    Don't have account?{" "}
                                                    <Link to="/register">Create an account</Link>
                                                </p>
                                                <p className="small mb-0">
                                                    Forget your Password?{" "}
                                                    <Link to="/register">Reset Password</Link>
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


export default LoginPage;
