import { useForm, SubmitHandler } from 'react-hook-form';
import React from 'react';
import {Item} from "../../model/item.model";
import {ERROR_MESSAGES} from "../../common/constants";
import ItemService from "../../services/item-service/item.service";

interface AddProductProps {
    updateProductList: (newProduct: Item) => void;
}

interface FormData {
    productName: string;
    description: string;
    productImage: FileList;
}

const AddProduct: React.FC<AddProductProps> = ({ updateProductList }) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
    } = useForm<FormData>();


    const onSubmit: SubmitHandler<FormData> = async (data) => {
        try {
            const item: Item = {
                name: data.productName,
                description:data.description,
                status: "AVAILABLE",
                // image:data.productImage,
                category:"APARTMENT",
            }

            ItemService.save(item).then(
                (response)=> {
                    updateProductList(response.data);
                })
                .catch(
                    (error) => {
                        console.error(error)
                    }
                )
            reset();
        } catch (error) {
            console.error('Error adding product:', error);
        }


    };
    return (

        <div className="col-xl">
            <div className="card p-4">
                <form onSubmit={handleSubmit(onSubmit)}>
                    <div>
                        <label htmlFor="formFileSm" className="form-label">
                            Product Name
                        </label>
                        <input
                            className={`form-control ${errors.productName ? 'is-invalid' : ''}`}
                            placeholder="Product name"
                            {...register('productName', { required: true })}
                        />
                        {errors.productName && <p className="error-message">{ERROR_MESSAGES.required}</p>}
                    </div>
                    <br />
                    <div>
                        <label htmlFor="formFileSm" className="form-label">
                            Description
                        </label>
                        <textarea
                            className={`form-control ${errors.description ? 'is-invalid' : ''}`}
                            placeholder="Description "
                            {...register('description', { required: true })}
                            rows={5}
                        />
                        {errors.description && <p className="error-message">{ERROR_MESSAGES.required}</p>}
                    </div>
                    <br />
                    <div>
                        <label htmlFor="productImage">Image of Product</label>
                        <input
                            className={`form-control ${errors.productImage ? 'is-invalid' : ''}`}

                            type="file"
                            id="productImage"
                            {...register('productImage', { required: true })}
                        />
                        {errors.productImage && <p className="error-message">{ERROR_MESSAGES.required}</p>}
                    </div>
                    <br />
                    <div className="col-md-12 text-center">
                        <button type="submit">ADD PRODUCT</button>
                    </div>
                </form>
            </div>
        </div>

    );
};

export default AddProduct;