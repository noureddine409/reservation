
import React, { useState, useEffect } from 'react';
import AddProduct from "./Add";
import EditProduct from "./Edit";
import {Item} from "../../model/item.model";
import ItemService from "../../services/item-service/item.service";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../common/constants";
import Pagination from "../../components/Pagination";
import Modal from '../../components/Modal';


const ShowProduct: React.FC = () => {

    const [pageable, setPageable] = useState({
        page: DEFAULT_PAGE_NUMBER,
        size: DEFAULT_PAGE_SIZE
    })
    const [products, setProducts] = useState<Item[]>([]);
    const [productToUpdate, setProductToUpdate] = useState<Item>(
        {
            id: 1,
            description: "sq",
            status: "AVAILABLE",
            category: "APARTMENT",
            name: "zzz",
        }
    );
    const handleDelete = async (productId: number) => {
        ItemService.delete(productId).then(
            (response) => {
                if(response) {
                    setProducts((prevProducts) => prevProducts.filter((product) => product.id !== productId));
                }
            }
        ).catch(
            (error) => {
                console.log(error);
            }
        )
    };


    useEffect(() => {
        // Function to fetch products from the API
        ItemService.findByUser({
            page: pageable.page -1,
            size: pageable.size
        })
            .then((response) => {
                // Execute these updates after receiving the response
                setProducts(response);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [pageable]);


    function handleUpdateClick(item: Item) {
        setProductToUpdate(item)
    }

    const nextPage = () => {

        if (pageable.page < 5) {
            setPageable({
                ...pageable,
                page: pageable.page + 1,
            });
        }
    }
    const previousPage = () => {
        if (pageable.page > 1) {
            setPageable({
                ...pageable,
                page: pageable.page - 1,
            });
        }
    }
    const changeCurrentPage = (pageNumber: number) => {
        setPageable({
            ...pageable,
            page: pageNumber,
        });
    }
    const updateProductList = (newProduct: Item) => {
        setProducts((prevProducts) => [...prevProducts, newProduct]);
    };
    const updateProduct = (editedProduct: Item) => {
        setProducts((prevProducts) =>
            prevProducts.map((product) =>
                product.id === editedProduct.id ? editedProduct : product
            )
        );
    };


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
                                        <button type="submit" data-bs-toggle="modal" data-bs-target="#AddProductModal">ADD PRODUCT</button>
                                    </div><br/>
                                                <Modal id="AddProductModal" title="Add a new product">
                                                    <AddProduct updateProductList={updateProductList} />
                                                </Modal>
                                    <table className="table datatable">
                                        <thead>
                                        <tr>
                                            <th scope="col">Image</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Category</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Action</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        {products.map((product) => (
                                            <tr key={product.id}>
                                                <td><img src="https://kreconcept.fr/wp-content/uploads/2022/11/KRE_bg_espace_cuisine.jpg" alt="Product" width="100" height="100" /></td>
                                                <td>{product.name}</td>
                                                <td>{product.category}</td>
                                                <td>{product.description}</td>
                                                <td>
                                                    <button type="button" className="btn btn-link" onClick={() => handleDelete(product.id!)}>
                                                        <span className="bi bi-trash"></span>
                                                    </button>
                                                    <button type="button" className="btn btn-link" data-bs-toggle="modal" data-bs-target="#EditProductModal" onClick={()=>handleUpdateClick(product)} >
                                                        <span className="bi bi-pen"></span>
                                                    </button>
                                                </td>
                                            </tr>
                                        ))}
                                        </tbody>
                                    </table>
                                    <Modal id="EditProductModal" title="Edit product">
                                        <EditProduct product={productToUpdate} updateProduct={updateProduct} />
                                    </Modal>
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <Pagination
                currentPage={pageable.page}
                totalPages={5}
                previousPage={previousPage}
                nextPage={nextPage}
                changeCurrentPage={changeCurrentPage}
            />

        </main>
    );
};
export default ShowProduct;