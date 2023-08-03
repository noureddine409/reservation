import React, { useEffect, useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import { Item } from '../../model/item.model';
import ItemService from "../../services/item-service/item.service";

interface FormData {
    id: number;
    productName: string;
    description: string;
    productImage: FileList;
}

interface EditProductProps {
    product: Item;
}

const EditProduct: React.FC<EditProductProps> = ({ product }) => {
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
    } = useForm<FormData>();

    const [formData, setFormData] = useState({
        id: product.id,
        name: product.name,
        status: product.status,
        category: product.category,
        description: product.description,
        // Add other form fields if required
    });

    // This useEffect will update the state whenever the `product` prop changes
    useEffect(() => {
        setFormData({
            id: product.id,
            name: product.name,
            status: product.status,
            category: product.category,
            description: product.description,
            // Update other form fields if required
        });
        // We also use the `reset` function provided by react-hook-form to reset the form
        reset({ productName: product.name, description: product.description });
    }, [product, reset]);

    const onSubmit: SubmitHandler<FormData> = async (data) => {

        const item: Item = {
            id: formData.id,
            name: data.productName,
            description: data.description,
            status: 'AVAILABLE',
            // image: data.productImage,
            category: 'APARTMENT',
        };
        ItemService.update(formData.id!, item).then(
            (response)=> {
                alert("product updated successfully " + response.data);
            }
        ).catch(
            (error) => console.error(error)
        )
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
                            defaultValue={formData.name}
                        />
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
                            defaultValue={formData.description}
                        />
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
                    </div>
                    <br />
                    <div className="col-md-12 text-center">
                        <button type="submit">EDIT PRODUCT</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditProduct;
