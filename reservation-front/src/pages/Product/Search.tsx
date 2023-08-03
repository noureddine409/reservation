import React, {useEffect, useState} from "react";
import {SingleValue} from "react-select";
import {
    DEFAULT_PAGE_NUMBER,
    DEFAULT_PAGE_SIZE,
    DEFAULT_SEARCH_CATEGORY,
    DEFAULT_SEARCH_KEYWORD,
    DEFAULT_TOTAL_PAGES
} from "../../common/constants";
import {Item, SearchItemDto, SearchParameter} from "../../model/item.model";
import ItemService from "../../services/item-service/item.service";
import {SelectedCategory} from "../../model/reservation.model";
import Pagination from "../../components/Pagination";
import SearchInput from "../../components/SearchInput";
import SelectCategory from "../../components/SelectCategory";
import ItemList from "../../components/ItemList";


const SearchProductPage = () => {
    // states
    const [items, setItems] = useState<Item[]>([])
    const [searchParameter, setSearchParameter] = useState<SearchParameter>(
        {
            keyword: DEFAULT_SEARCH_KEYWORD,
            category: DEFAULT_SEARCH_CATEGORY,
            page: DEFAULT_PAGE_NUMBER,
            size: DEFAULT_PAGE_SIZE,
            totalPages: DEFAULT_TOTAL_PAGES
        }
    );

    useEffect(() => {
        const reqBody: SearchItemDto = {
            keyword: searchParameter.keyword,
            category: searchParameter.category?.value,
            page: searchParameter.page - 1,
            size: searchParameter.size,
        };
        ItemService.search(reqBody)
            .then((response) => {
                // Execute these updates after receiving the response
                setItems(response.data);
                setSearchParameter((prevSearchParam) => ({
                    ...prevSearchParam,
                    totalPages: response.headers["x-total-pages"],
                }));
            })
            .catch((error) => {
                console.log(error);
            });
    }, [searchParameter.page, searchParameter.keyword, searchParameter.category, searchParameter.size]);
    const SearchHandleChange = (event: React.FormEvent<HTMLInputElement>) => {
        const keyword = event.currentTarget.value;
        setSearchParameter({
            ...searchParameter,
            keyword: keyword,
        });
    };


    const CategoryHandleChange = (selectedCategory: SingleValue<SelectedCategory>) => {
        if (selectedCategory) {
            setSearchParameter({
                ...searchParameter,
                category: selectedCategory
            });
        }
    }


    const nextPage = () => {

        if (searchParameter.page < searchParameter.totalPages) {
            setSearchParameter({
                ...searchParameter,
                page: searchParameter.page + 1,
            });
        }
    }
    const previousPage = () => {
        if (searchParameter.page > 1) {
            setSearchParameter({
                ...searchParameter,
                page: searchParameter.page - 1,
            });
        }
    }
    const changeCurrentPage = (pageNumber: number) => {
        setSearchParameter({
            ...searchParameter,
            page: pageNumber,
        });
    }

    return (
        <main id="main" className="main">
            <div className="container px-3">
                <div className="row">
                    <div className="col-sm">
                        <SearchInput onChange={SearchHandleChange}/>
                    </div>
                    <div className="col-sm">
                        <SelectCategory onChange={CategoryHandleChange}/>
                    </div>
                </div>
            </div>
            <ItemList items={items} />
            <Pagination
                currentPage={searchParameter.page}
                totalPages={searchParameter.totalPages}
                previousPage={previousPage}
                nextPage={nextPage}
                changeCurrentPage={changeCurrentPage}
            />

        </main>
    )

}

export default SearchProductPage;
