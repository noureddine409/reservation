import { Item, SearchItemDto } from "../../model/item.model";
import axios, { AxiosResponse } from "axios";
import { Page } from "../../model/generic.model";

const baseUrl: string = process.env.REACT_APP_API_URL!;

const ItemService = {
    search: async (search: SearchItemDto) => {
        try {
            return await axios.post(`${baseUrl}/items/search`, search, {
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
            const response: AxiosResponse<Item[]> = await axios.get(`${baseUrl}/items?page=${pageable.page}&size=${pageable.size}`, {
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
            const response: AxiosResponse<Item[]> = await axios.delete(`${baseUrl}/items/${id}`, {
                headers: {
                    'Content-Type': 'application/json',
                },
            }); alert("Product deleted successfully ");
            return response.data; // Extract the data property from the AxiosResponse

        }
        catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    update: async (itemId: number, item: Item)=> {
        try {
            return await axios.put(`${baseUrl}/items/${itemId}`, item, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    },
    save: async (item: Item)=> {
        try {
            return await axios.post(`${baseUrl}/items`, item, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    }
};

export default ItemService;
