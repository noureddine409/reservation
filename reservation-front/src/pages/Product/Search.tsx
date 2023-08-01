import {Card} from "../../components/Card";
import React, {useEffect, useState} from "react";
import Select, {SingleValue} from "react-select";
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

// interface SearchDto {
//     keyword: string;
//     category: string | null;
//     page: number;
//     size: number;
// }

interface Item {
    id?: number
    name: string,
    description: string,
    category: string,
    status: string
}



const SearchProductPage = () => {
    const [items,setItems]=useState<Item[]>([])
    const [searchParameter, setSearchParameter] = useState<SearchParameter>(
        {
            keyword: DEFAULT_SEARCH_KEYWORD,
            category: DEFAULT_SEARCH_CATEGORY,
            page: DEFAULT_PAGE_NUMBER,
            size: DEFAULT_PAGE_SIZE,
            totalPages:DEFAULT_TOTAL_PAGES
        }
    );

    const search = async () => {
        try {
            const reqBody = {
                keyword: searchParameter.keyword,
                category: searchParameter.category?.value,
                page: searchParameter.page - 1,
                size: searchParameter.size,
            };
            const response = await axios.post('http://localhost:8080/api/v1/items/search', reqBody, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            // Return the response data
            return response;
        } catch (error) {
            console.error('Error fetching items:', error);
            throw error; // Rethrow the error so that the calling code can handle it
        }
    };

    useEffect(() => {
        // Call the API whenever there's a change in the search parameters
        search()
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
    }, [searchParameter.page, searchParameter.keyword, searchParameter.category]);
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


    function previousPage() {
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
        return Array.from({length: endPage - startPage + 1}, (_, index) => startPage + index);
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
                            .map((item) => (
                                <div key={item.id!} className="col-md-4">
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
                        <a href="#/" className="page-link" onClick={previousPage}>
                            Previous
                        </a>
                    </li>
                    {getPageNumbers().map((pageNumber) => (
                        <li
                            className={`page-item ${searchParameter.page === pageNumber ? "active" : ""}`}
                            key={pageNumber}
                        >
                            <a href="#/" className="page-link" onClick={() => changeCurrentPage(pageNumber)}>
                                {pageNumber}
                            </a>
                        </li>
                    ))}
                    <li className="page-item">
                        <a href="#/" className="page-link" onClick={nextPage}>
                            Next
                        </a>
                    </li>
                </ul>
            </nav>

        </main >
    )

}

export default SearchProductPage;
