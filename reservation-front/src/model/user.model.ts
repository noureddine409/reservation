import {GenericModel} from "./generic.model";

export interface User extends GenericModel {
    firstName: string;
    lastName: string;
    email: string;
    password?: string;
    birthDay?: string;
    profilePicture?: string;
    verificationCode?: string;
    profileCompleted?: boolean;
    bio?: string;
    enabled?: boolean;
}
 export interface LoginDto{
    email: string;
    password: string;
 }