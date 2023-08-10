import {LoginDto} from "../../model/user.model";
import {axiosInstance} from "../../middleware/axios-auth-config";

const AuthenticationService = {
    login: async (loginDto: LoginDto) => {
        try {
            return await axiosInstance.post("/auth/login", loginDto);
        } catch (error) {
            throw error;
        }
    },
    refresh: async () => {
        try {
            return await axiosInstance.post("/auth/token");
        } catch (error) {
            throw error;
        }
    },
    logout: async () => {
        try {
            return await axiosInstance.post("/auth/logout");
        } catch (error) {
            throw error;
        }
    },
};

export default AuthenticationService;
