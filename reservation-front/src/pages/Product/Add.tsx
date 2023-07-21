import { useForm, SubmitHandler } from 'react-hook-form';
import React from 'react';
import {ERROR_MESSAGES} from "../../common/constants";

interface FormData {
    productName: string;
    description: string;
    productImage: FileList;
}
const AddProduct= () => {
    const {
        register,
        handleSubmit,
        formState: { errors },
        reset,
    } = useForm<FormData>();

    const onSubmit: SubmitHandler<FormData> = (data) => {
        // Handle form submission logic here
        console.log(data);
        reset();
    };
    return (
        <main id="main" className="main">
            <div className="pagetitle">
                <h1>Add product</h1>
            </div>
            <div className="col-xl">
                <div className="card p-4">
                    <form onSubmit={handleSubmit(onSubmit)}>
                        <div>
                            <label htmlFor="formFileSm" className="form-label">
                                Product Name
                            </label>
                            <input
                                className="form-control"
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
                                className="form-control"
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
                                className="form-control"
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
        </main>
    );
};

export default AddProduct;
