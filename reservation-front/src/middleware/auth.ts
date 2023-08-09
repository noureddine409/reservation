import AuthentificationService from "../services/auth-service/auth.service";
import axios from "axios";

const httpClient = axios.create({
    baseURL: process.env.REACT_APP_API_URL!,
    withCredentials: true,
});

// should not intercept for /auth

// Add a response interceptor
httpClient.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;

        // TODO: Handle token refresh and redirection for unauthorized access
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                await AuthentificationService.refresh().then(
                    response => {
                        return httpClient(originalRequest);
                    }
                )
            } catch (refreshError) {
                // Handle token refresh failure
                // Call logout service and redirect to login
                AuthentificationService.logout(); 

                window.location.href = "/login"; // Redirect to login page
                throw refreshError;
            }
        }

        return Promise.reject(error);
    }
);

export default httpClient;
