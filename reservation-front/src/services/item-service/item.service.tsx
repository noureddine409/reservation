import {SearchItemDto} from "../../model/item.model";
import axios from "axios";

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
    }

}

export default ItemService;
