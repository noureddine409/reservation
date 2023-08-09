import AuthentificationService from "../services/auth-service/auth.service";
import axios from "axios";

const httpClient = axios.create({
    baseURL: process.env.REACT_APP_API_URL!,
    withCredentials: true,
});

// should not intercept for /auth
httpClient.interceptors.request.use(
    (config) => {
        if (config.url?.includes("/auth")) {
            return config;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

httpClient.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const refreshedResponse = await AuthentificationService.refresh();
                return refreshedResponse;
            } catch (refreshError) {
                await AuthentificationService.logout();

                // Redirect to login page
                window.location.href = "/login";
                throw refreshError;
            }
        }

        return Promise.reject(error);
    }
);

export default httpClient;
