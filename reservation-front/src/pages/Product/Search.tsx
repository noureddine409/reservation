import { Card } from "../../components/Card";
import React, { useState } from "react";
import Select, { SingleValue } from "react-select";
import '../../App.css';
import { DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE, DEFAULT_SEARCH_CATEGORY, DEFAULT_SEARCH_KEYWORD, data, options } from "../../common/constants";



interface SelectedCategory {
  label: string
  value: string
}

interface SearchParameter {
  keyword: string;
  category: SelectedCategory;
  page: number;
  size: number;
}

const SearchProductPage = () => {
  const [searchParameter, setSearchParameter] = useState<SearchParameter>(
    {
      keyword: DEFAULT_SEARCH_KEYWORD,
      category: DEFAULT_SEARCH_CATEGORY,
      page: DEFAULT_PAGE_NUMBER,
      size: DEFAULT_PAGE_SIZE
    }
  );


  const SearchHandleChange = (event: React.FormEvent<HTMLInputElement>) => {
    setSearchParameter({
      ...searchParameter,
      keyword: event.currentTarget.value,
    });
  }


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
  const filteredData = data.filter((item) => item.category.includes(searchParameter.category.value));
  const lastIndex = searchParameter.page * searchParameter.size;
  const firstIndex = lastIndex - searchParameter.size;

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
  const getTotalPages = () => Math.ceil(filteredData.length / searchParameter.size);
  const nextPage =() => {
  
    if (searchParameter.page < getTotalPages()) {
      setSearchParameter({
        ...searchParameter,
        page: searchParameter.page + 1,
      });
    }
  }
  const getPageNumbers = () => {
    const currentPage = searchParameter.page;
    const totalPages = getTotalPages();
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
              </div>
            </div>

          </div>
        </div>
      </div>
      <section className="section">
        <div className="container">
          <div className="row">
            {/* Use filteredData instead of data in the mapping */}
            {filteredData
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