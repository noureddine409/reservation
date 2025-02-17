import {Item, SearchItemDto} from "../../model/item.model";
import {AxiosResponse} from "axios";
import {Page} from "../../model/generic.model";
import interceptedAxios from "../../middleware/axios-auth-config";

const ItemService = {
    search: async (search: SearchItemDto) => {
        try {
            return await interceptedAxios.post("/items/search", search);
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    findByUser: async (pageable: Page): Promise<Item[]> => {
        try {
            const response: AxiosResponse<Item[]> = await interceptedAxios.get(`/items?page=${pageable.page}&size=${pageable.size}`);
            return response.data;
        } catch (error) {
            throw error;
        }
    },
    delete: async (id: number) => {
        try {
            const response: AxiosResponse<Item[]> = await interceptedAxios.delete(`/items/${id}`);
            return response.data;

        } catch (error) {
            throw error;
        }
    },
    update: async (itemId: number, item: Item) => {
        try {
            return await interceptedAxios.patch(`/items/${itemId}`, item);
        } catch (error) {
            throw error;
        }
    },
    save: async (item: Item) => {
        try {
            return await interceptedAxios.post("/items", item);
        } catch (error) {
            throw error;
        }
    }
};

export default ItemService;
