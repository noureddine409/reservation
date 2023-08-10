import {LoginDto} from "../../model/user.model";
import {uninterceptedAxios} from "../../middleware/auth";

const AuthenticationService = {
    login: async (loginDto: LoginDto) => {
        try {
            return  await uninterceptedAxios.post("/auth/login", loginDto);
        } catch (error) {
            throw error; 
        }
    },
    refresh: async () => {
        try {
            return await uninterceptedAxios.post("/auth/token");
        } catch (error) {
            throw error; 
        }
    },
    logout: async () => {
        try {
            return await uninterceptedAxios.post("/auth/logout");
        } catch (error) {
            throw error; 
        }
    },
};

export default AuthenticationService;
