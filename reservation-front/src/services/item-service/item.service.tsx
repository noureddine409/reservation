import {GenericService} from "../generic.service";
import {Item} from "../../model/item.model";

export class ItemService extends GenericService {

    public async saveItem(dto: Item): Promise<Item> {
        return this.post<Item>('reservations', dto);
    }

    public async updateItem(id: number, dto: Item): Promise<Item> {
        return this.put<Item>(`reservations/${id}`, dto);
    }

    public async getItemById(id: number): Promise<Item> {
        return this.get<Item>(`reservations/${id}`);
    }

    public async deleteItem(id: number): Promise<boolean> {
        return this.delete(`reservations/${id}`);
    }
}
