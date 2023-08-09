import axios from "axios";
import { LoginDto } from "../../model/user.model";
import httpClient from "../../middleware/auth";

const AuthentificationService = {
    login: async (loginDto: LoginDto) => {
        try {
            const response = await httpClient.post("/auth/login", loginDto);
            return response; // Return the response data (if needed)
        } catch (error) {
            throw error; // Handle the error or rethrow it for the caller to handle
        }
    },
    refresh: async () => {
        try {
            const response = await httpClient.post("/auth/token");
            return response; // Return the response data (if needed)
        } catch (error) {
            throw error; // Handle the error or rethrow it for the caller to handle
        }
    },
    logout: async () => {
        try {
            const response = await httpClient.post("/auth/logout");
            return response; // Return the response data (if needed)
        } catch (error) {
            throw error; // Handle the error or rethrow it for the caller to handle
        }
    },
};

export default AuthentificationService;
