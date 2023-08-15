import { useForm, SubmitHandler } from 'react-hook-form';
import React, {useState} from 'react';
import {Item, Parameter} from "../../model/item.model";
import {ERROR_MESSAGES} from "../../common/constants";
import ItemService from "../../services/item-service/item.service";
import {FormControl, Table} from "react-bootstrap";

interface AddProductProps {
    updateProductList: (newProduct: Item) => void;
}

interface FormData {
    productName: string;
    description: string;
    productImage: FileList;


}



const AddProduct: React.FC<AddProductProps> = ({ updateProductList }) => {
    const [params, setParams] = useState<Parameter[]>([]);

    const handleAdd = () => {
        const newParam: Parameter = { key: '', value: '' };
        setParams([...params, newParam]);
    };

    const handleDelete = (index: number) => {
        const updatedParams = params.filter((_, i) => i !== index);
        setParams(updatedParams);
    };






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
                params: params
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
            setParams([]);
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
                    <label htmlFor="formFileSm" className="form-label">
                        Category
                    </label>
                    <select className="form-control">
                        <option selected>Choose...</option>
                        <option value="1">APARTMENT</option>
                        <option value="2">VEHICULE</option>
                    </select> <br/>

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
                    <button type="button" className="btn btn-link" onClick={handleAdd}>Add Parameters</button><br/>
                    <Table striped bordered hover>
                        <thead>
                        <tr>
                            <th>Key</th>
                            <th>Value</th>
                            <th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {params.map((param, index) => (
                            <tr key={index}>
                                <td>
                                    <FormControl
                                        aria-label="Parameter Key"
                                        value={param.key}
                                        onChange={(e) => {
                                            const updatedParams = [...params];
                                            updatedParams[index].key = e.target.value;
                                            setParams(updatedParams);
                                        }}
                                    />
                                </td>
                                <td>
                                    <FormControl
                                        aria-label="Parameter Value"
                                        value={param.value}
                                        onChange={(e) => {
                                            const updatedParams = [...params];
                                            updatedParams[index].value = e.target.value;
                                            setParams(updatedParams);
                                        }}
                                    />
                                </td>
                                <td>
                                    <button type="button" className="btn btn-link" onClick={handleAdd}>
                                        <span className='bi bi-plus-circle'></span>
                                    </button>
                                    <button
                                        type="button"
                                        className="btn btn-link"
                                        onClick={() => handleDelete(index)}
                                    >
                                        <span className="bi bi-trash"></span>
                                    </button>

                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>



                    <div className="col-md-12 text-center">
                        <button type="submit">ADD PRODUCT</button>
                    </div>






                </form>
            </div>
        </div>

    );
};

export default AddProduct;