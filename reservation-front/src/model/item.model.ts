import {GenericModel} from "./generic.model";
import {User} from "./user.model";

export interface Item extends GenericModel {
    name: string;
    description: string;
    category: string;
    status: string;
    createdBy: User | null;
}
