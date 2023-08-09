import React, { useState, useEffect } from 'react';
import AddProduct from "./Add";
import EditProduct from "./Edit";
import {Item} from "../../model/item.model";
import ItemService from "../../services/item-service/item.service";
import {DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE} from "../../common/constants";
import Pagination from "../../components/Pagination";


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
            name: "zzz"
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


                                    <div className="modal fade" id="AddProductModal" tabIndex={1} >
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
                                    <div className="modal fade" id="EditProductModal" tabIndex={1} >
                                        <div className="modal-dialog modal-xl">
                                            <div className="modal-content">
                                                <div className="modal-header">
                                                    <h5 className="modal-title">Add a new product</h5>
                                                    <button type="button" className="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div className="modal-body ">
                                                    <EditProduct product={productToUpdate!}/>
                                                </div>

                                            </div>
                                        </div>
                                    </div>

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