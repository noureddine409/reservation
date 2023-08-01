import axios, { AxiosResponse } from 'axios';

export class GenericService {
    baseUrl: string = process.env.REACT_APP_API_URL!;

    protected async post<T>(endpoint: string, data: any): Promise<T> {
        try {
            const response: AxiosResponse<T> = await axios.post<T>(
                `${this.baseUrl}/${endpoint}`,
                data
            );
            return response.data;
        } catch (error: any) {
            throw new Error(
                `Failed to send POST request to ${endpoint}: ${error.message}`
            );
        }
    }

    protected async get<T>(endpoint: string): Promise<T> {
        try {
            const response: AxiosResponse<T> = await axios.get<T>(
                `${this.baseUrl}/${endpoint}`
            );
            return response.data;
        } catch (error: any) {
            throw new Error(`Failed to send GET request to ${endpoint}: ${error.message}`);
        }
    }

    protected async put<T>(endpoint: string, data: any): Promise<T> {
        try {
            const response: AxiosResponse<T> = await axios.put<T>(
                `${this.baseUrl}/${endpoint}`,
                data
            );
            return response.data;
        } catch (error: any) {
            throw new Error(
                `Failed to send PUT request to ${endpoint}: ${error.message}`
            );
        }
    }

    protected async delete(endpoint: string): Promise<boolean> {
        try {
            const response: AxiosResponse<boolean> = await axios.delete<boolean>(
                `${this.baseUrl}/${endpoint}`
            );
            return response.data;
        } catch (error: any) {
            throw new Error(
                `Failed to send DELETE request to ${endpoint}: ${error.message}`
            );
        }
    }
}
