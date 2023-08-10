import axios from "axios";
import AuthenticationService from "../services/auth-service/auth.service";

const axiosConfig = {
    baseURL: process.env.REACT_APP_API_URL!,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    }
}

export const uninterceptedAxios = axios.create(axiosConfig);

const httpClient = axios.create(axiosConfig);

httpClient.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const originalRequest = error.config;
        if (error.response.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;
            await AuthenticationService.refresh().then(
                () => {
                    httpClient(originalRequest)
                }
            ).catch(
                error => Promise.reject(error)
            )
        }

        return Promise.reject(error);
    }
);

export default httpClient;
