import {Item, SearchItemDto} from "../../model/item.model";
import {AxiosResponse} from "axios";
import {Page} from "../../model/generic.model";
import interceptedAxios from "../../middleware/axios-auth-config";

const ItemService = {
    search: async (search: SearchItemDto) => {
        try {
            return await interceptedAxios.post("/items/search", search, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    findByUser: async (pageable: Page): Promise<Item[]> => {
        try {
            const response: AxiosResponse<Item[]> = await interceptedAxios.get(`/items?page=${pageable.page}&size=${pageable.size}`, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            return response.data; // Extract the data property from the AxiosResponse
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    delete: async (id: number) => {
        try {
            const response: AxiosResponse<Item[]> = await interceptedAxios.delete(`/items/${id}`, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            alert("Product deleted successfully ");
            return response.data;

        } catch (error) {
            throw error;
        }
    },
    update: async (itemId: number, item: Item) => {
        try {
            return await interceptedAxios.put(`/items/${itemId}`, item);
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
