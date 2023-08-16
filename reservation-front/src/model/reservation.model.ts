import {GenericModel} from "./generic.model";
import {User} from "./user.model";
import {Item} from "./item.model";

export interface Reservation extends GenericModel {
    period: Period;
    item: Item;
    user: User;
}export interface ReservationRequest extends GenericModel {
    period: Period;
    item: {
        id: number
    };
    user: {
        id: number
    };
}


export interface UserReservationSearch {
    userId: number;
    period: Period;
}

export interface ItemReservationSearch {
    itemId: number;
    period: Period;
}
export interface Period {
    startDate: string;
    endDate: string;
}

export interface SelectedCategory {
    label: string
    value: string|null
}


