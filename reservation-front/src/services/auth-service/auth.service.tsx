import axios from "axios";
import { LoginDto } from "../../model/user.model";
import httpClient from "../../middleware/auth";

const AuthentificationService = {
    login: async (loginDto: LoginDto) => {
        try {
            const response = await httpClient.post("/auth/login", loginDto);
            return response;
        } catch (error) {
            throw error; 
        }
    },
    refresh: async () => {
        try {
            const response = await httpClient.post("/auth/token");
            return response; 
        } catch (error) {
            throw error; 
        }
    },
    logout: async () => {
        try {
            const response = await httpClient.post("/auth/logout");
            return response.data; 
        } catch (error) {
            throw error; 
        }
    },
};

export default AuthentificationService;
