import React from "react";
import Select, {SingleValue} from "react-select";
import {SelectedCategory} from "../model/reservation.model";
import {CATEGORY_OPTIONS} from "../common/constants";


interface SelectCategoryProps {
    onChange: (selectedCategory: SingleValue<SelectedCategory>) => void;
}

const SelectCategory: React.FC<SelectCategoryProps> = ({ onChange }) => {
    const handleCategoryChange = (selectedCategory: SingleValue<SelectedCategory>) => {
        onChange(selectedCategory);
    };

    return (
        <div className="p-1">
            <Select
                options={CATEGORY_OPTIONS}
                placeholder="Select une categorie"
                aria-label="Select une categorie"
                onChange={handleCategoryChange}
            />
        </div>
    );
};

export default SelectCategory;
