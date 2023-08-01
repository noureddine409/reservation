import { Card } from "../../components/Card";
import React, { useState, useEffect } from "react";
import Select, { SingleValue } from "react-select";
import '../../App.css';
import {
    DEFAULT_PAGE_NUMBER,
    DEFAULT_PAGE_SIZE,
    DEFAULT_SEARCH_CATEGORY,
    DEFAULT_SEARCH_KEYWORD,
    DEFAULT_TOTAL_PAGES,
    options
} from "../../common/constants";
import axios from "axios";


interface SelectedCategory {
    label: string
    value: string|null
}

interface SearchParameter {
    keyword: string;
    category: SelectedCategory;
    page: number;
    size: number;
    totalPages: number
}

interface SearchDto {
    keyword: string;
    category: string | null;
    page: number;
    size: number;
}



const SearchProductPage = () => {
    const [items,setItems]=useState([])
    const [searchParameter, setSearchParameter] = useState<SearchParameter>(
        {
            keyword: DEFAULT_SEARCH_KEYWORD,
            category: DEFAULT_SEARCH_CATEGORY,
            page: DEFAULT_PAGE_NUMBER,
            size: DEFAULT_PAGE_SIZE,
            totalPages:DEFAULT_TOTAL_PAGES
        }
    );

    useEffect(() => {
        const reqBody = {
            keyword: searchParameter.keyword,
            category: searchParameter.category.value,
            page: searchParameter.page - 1,
            size: searchParameter.size
        }
        handleSearch(reqBody);
      }, []);

    const handleSearch = async (reqBody: SearchDto) => {
        try {
            const response = await axios.post('http://localhost:8080/api/v1/items/search',reqBody , {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            setItems(response.data);
            setSearchParameter({
                ...searchParameter,
                totalPages: response.headers["x-total-pages"]
            })
        } catch (error) {
            console.error('Error fetching items:', error);
        }
    };
    const SearchHandleChange = (event: React.FormEvent<HTMLInputElement>) => {
        const keyword = event.currentTarget.value;
        setSearchParameter({
            ...searchParameter,
            keyword: keyword,
        });
    };


    const CategoryHandleChange = (
        selectedCategory: SingleValue<SelectedCategory>,
    ) => {
        if (selectedCategory) {
            setSearchParameter({
                ...searchParameter,
                category: selectedCategory
            });
        }
    }

    const lastIndex = searchParameter.page * searchParameter.size;
    const firstIndex = lastIndex - searchParameter.size;

    function previousPage() {
        if (searchParameter.page > 1) {
            const page = searchParameter.page - 1
            setSearchParameter({
                ...searchParameter,
                page: searchParameter.page - 1,
            });
            const reqBody = {
                keyword: searchParameter.keyword,
                category: searchParameter.category.value,
                page: page ,
                size: searchParameter.size
            }
            handleSearch(reqBody)
        }
    }

    const changeCurrentPage = (pageNumber: number) => {
        setSearchParameter({
            ...searchParameter,
            page: pageNumber,
        });
    }
    const nextPage =() => {

        if (searchParameter.page < searchParameter.totalPages) {
            setSearchParameter({
                ...searchParameter,
                page: searchParameter.page + 1,
            });
        }

    }
    const getPageNumbers = () => {
        const currentPage = searchParameter.page;
        const totalPages = searchParameter.totalPages;
        // Calculate the start and end page numbers to be displayed
        let startPage = Math.max(currentPage - 1, 1);
        let endPage = Math.min(startPage + 2, totalPages);

        // Adjust the start and end page numbers if they are not within bounds
        if (currentPage === totalPages) {
            startPage = Math.max(currentPage - 2, 1);
            endPage = totalPages;
        } else if (currentPage === 1) {
            endPage = Math.min(currentPage + 2, totalPages);
        }

        // Create an array of page numbers using Array.from and the map function
        const pageNumbers = Array.from({ length: endPage - startPage + 1 }, (_, index) => startPage + index);

        return pageNumbers;
    };


    return (
        <main id="main" className="main" >
            <div className="container px-3">
                <div className="row">
                    <div className="col-sm">
                        <div className="p-1">
                            <input type="search" className="form-control rounded"
                                   placeholder="Search" aria-label="Search"
                                   aria-describedby="search-addon"
                                   onChange={SearchHandleChange}

                            />
                            <span className="input-group-text border-0" id="search-addon">
                <i className="fas fa-search"></i>
              </span>
                        </div>
                    </div>
                    <div className="col-sm">
                        <div className="row">
                            <div className="p-1">
                                <Select
                                    options={options}
                                    placeholder="Select une categorie" aria-label="Select une categorie"
                                    onChange={CategoryHandleChange}
                                />
                                <button  className="btn btn-primary">Search</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
            <section className="section">
                <div className="container">
                    <div className="row">
                        {/* Use filteredData instead of data in the mapping */}
                        {items
                            .slice(firstIndex, lastIndex)
                            .map((item, index) => (
                                <div key={index} className="col-md-4">
                                    {/* Custom card styles to ensure fixed size and text overflow */}
                                    <div className="card-container">
                                        <Card item={item} />
                                    </div>
                                </div>
                            ))}
                    </div>
                </div>
            </section>
            <nav>
                <ul className="pagination">
                    <li className="page-item">
                        <a className="page-link" onClick={previousPage}>
                            Previous
                        </a>
                    </li>
                    {getPageNumbers().map((pageNumber) => (
                        <li
                            className={`page-item ${searchParameter.page === pageNumber ? "active" : ""}`}
                            key={pageNumber}
                        >
                            <a className="page-link" onClick={() => changeCurrentPage(pageNumber)}>
                                {pageNumber}
                            </a>
                        </li>
                    ))}
                    <li className="page-item">
                        <a className="page-link" onClick={nextPage}>
                            Next
                        </a>
                    </li>
                </ul>
            </nav>

        </main >
    )

}

export default SearchProductPage;