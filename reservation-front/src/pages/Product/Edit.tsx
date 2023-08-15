
import React, { useEffect, useState } from 'react';
import { useForm, SubmitHandler } from 'react-hook-form';
import {Item, Parameter} from '../../model/item.model';
import ItemService from "../../services/item-service/item.service";
import {FormControl, Table} from "react-bootstrap";


interface FormData {
    id: number;
    productName: string;
    description: string;
    productImage: FileList

}


interface EditProductProps {
    product: Item;
    updateProduct: (editedProduct: Item) => void;
}
const EditProduct: React.FC<EditProductProps> = ({ product, updateProduct }) => {

    const [params, setParams] = useState<Parameter[]>(product.params || []);


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

    const [formData, setFormData] = useState({
        id: product.id,
        name: product.name,
        status: product.status,
        category: product.category,
        description: product.description,

    });

    useEffect(() => {
        setFormData({
            id: product.id,
            name: product.name,
            status: product.status,
            category: product.category,
            description: product.description,

        });
        setParams(product.params || []);
        reset({ productName: product.name, description: product.description });
    }, [product, reset]);

    const onSubmit: SubmitHandler<FormData> = async (data) => {

        const item: Item = {
            id: formData.id,
            name: data.productName,
            description: data.description,
            status: 'AVAILABLE',
            //image: data.productImage,
            category: 'APARTMENT',
            params: params,

        };

        ItemService.update(formData.id!, item).then(
            (response)=> {
                alert("Product updated successfully " + response.data);
                updateProduct(response.data);
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
                    <label htmlFor="formFileSm" className="form-label">
                        Category
                    </label>
                    <select className="form-control" defaultValue={formData.category}>
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
                        <button type="submit">EDIT PRODUCT</button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default EditProduct;
