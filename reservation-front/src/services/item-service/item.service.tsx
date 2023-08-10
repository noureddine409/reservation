import {Item, SearchItemDto} from "../../model/item.model";
import {AxiosResponse} from "axios";
import {Page} from "../../model/generic.model";
import httpClient from "../../middleware/auth";

const ItemService = {
    search: async (search: SearchItemDto) => {
        try {
            return await httpClient.post("/items/search", search, {
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
            const response: AxiosResponse<Item[]> = await httpClient.get(`/items?page=${pageable.page}&size=${pageable.size}`, {
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
        try{
            const response: AxiosResponse<Item[]> = await httpClient.delete(`/items/${id}`, {
                headers: {
                    'Content-Type': 'application/json',
                },
            }); alert("Product deleted successfully ");
            return response.data;

        }
        catch (error) {
            throw error;
        }
    },
    update: async (itemId: number, item: Item)=> {
        try {
            return await httpClient.put(`/items/${itemId}`, item);
        } catch (error) {
            throw error;
        }
    },
    save: async (item: Item)=> {
        try {
            return await httpClient.post("/items", item);
        } catch (error) {
            throw error;
        }
    }
};

export default ItemService;
