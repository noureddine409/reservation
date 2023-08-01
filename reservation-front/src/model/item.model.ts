import {GenericModel} from "./generic.model";
import {User} from "./user.model";
import {SelectedCategory} from "./reservation.model";

export interface Item extends GenericModel {
    name: string;
    description: string;
    category: string;
    status: string;
    createdBy?: User | null;
}

export interface SearchItemDto {
    keyword?: string;
    category?: string | null;
    page?: number;
    size?: number;
}

export interface SearchParameter {
    keyword: string;
    category: SelectedCategory;
    page: number;
    size: number;
    totalPages: number
}
