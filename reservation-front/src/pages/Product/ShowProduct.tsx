import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddProduct from "./Add";

interface ProductData {
    id: number;
    name: string;
    description: string;
    // Add more properties based on your actual item model
}
const ShowProduct: React.FC = () => {




    const handleDelete = async (productId: number) => {
        try {
            // Make a DELETE request to delete the product with the given productId
            await axios.delete(`http://localhost:8080/api/v1/items/${productId}`, {
                headers: {
                    'Content-Type': 'Application/json',
                    "Access-Control-Allow-Origin":"*",
                    "Access-Control-Allow-Credentials":true,
                },
            });

            // Remove the deleted product from the products state
            setProducts((prevProducts) => prevProducts.filter((product) => product.id !== productId));
        } catch (error) {
            console.error('Error deleting product:', error);
        }
    };


    const [products, setProducts] = useState<ProductData[]>([]);
    useEffect(() => {
        // Function to fetch products from the API
        const fetchProducts = async (page: number, size: number) => {
            try {
                const response = await axios.get<ProductData[]>(`http://localhost:8080/api/v1/items?page=${page}&size=${size}`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Access-Control-Allow-Origin': '*',
                        'Access-Control-Allow-Credentials': true,
                    },
                });
                setProducts(response.data);
            } catch (error) {
                console.error('Error fetching products:', error);
            }
        };


        fetchProducts(0, 10);
    }, []);




    return (
        <main id="main" className="main">
            <div>
                <section className="section">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="card">
                                <div className="card-body">
                                    <h5 className="card-title">All Product</h5>

                                    <div className="modal-body">
                                        <button type="submit" data-bs-toggle="modal" data-bs-target="#ExtralargeModal">ADD PRODUCT</button>
                                    </div><br/>


                                    <div className="modal fade" id="ExtralargeModal" tabIndex={1} >
                                        <div className="modal-dialog modal-xl">
                                            <div className="modal-content">
                                                <div className="modal-header">
                                                    <h5 className="modal-title">Add a new product</h5>
                                                    <button type="button" className="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div className="modal-body ">
                                                    <AddProduct></AddProduct>
                                                </div>

                                            </div>
                                        </div>
                                    </div>

                                    <table className="table datatable">
                                        <thead>
                                        <tr>
                                            <th scope="col">Image</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {products.map((product) => (
                                            <tr key={product.id}>
                                                <td></td>
                                                <td>{product.name}</td>
                                                <td>{product.description}</td>
                                                <td>
                                                    <button type="button" className="btn btn-link" onClick={() => handleDelete(product.id)}>
                                                        <span className="bi bi-trash"></span>
                                                    </button>
                                                    <button type="button" className="btn btn-link">
                                                        <span className="bi bi-pen"></span>
                                                    </button>
                                                </td>
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>

        </main>
    );
};
export default ShowProduct;