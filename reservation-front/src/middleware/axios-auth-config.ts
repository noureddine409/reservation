import axios from "axios";
import AuthenticationService from "../services/auth-service/auth.service";

const defaultAxiosConfig = {
    baseURL: process.env.REACT_APP_API_URL!,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json',
    }
}

export const axiosInstance = axios.create(defaultAxiosConfig);

const interceptedAxios = axios.create(defaultAxiosConfig);

interceptedAxios.interceptors.response.use(
    (response) => {
        return response;
    },
    async (error) => {
        const retryConfig = error.config;
        if (error.response.status === 401 && !retryConfig._retry) {
            retryConfig._retry = true;
            await AuthenticationService.refresh().then(
                () => {
                    interceptedAxios(retryConfig)
                }
            ).catch(
                error => Promise.reject(error)
            )
        }

        return Promise.reject(error);
    }
);

export default interceptedAxios;
