import React, {useState} from "react";
import Select, {ActionMeta, SingleValue} from "react-select";
import '../../App.css';
import {Card} from "../../components/Card";

const data = [
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "Lorem Ipsum",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "Dolor Sit Amet",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "Consectetur Adipisc",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "Consectetur Adipiscing",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    }
]

const options = [
    { value: 'Meeting room', label: 'Meeting room' },
    { value: 'Kitchen', label: 'Kitchen' },
    { value: 'Equipments', label: 'Equipments' },
];
type SelectedCategory = {
    label: string
    value: string
}

interface SearchParameter {
    keyword: string;
    category: string;
    page: number;
    size: number;
    maxPages: number;
}

const SearchProductPage = () => {
    const [searchParameter, setSearchParameter] = useState<SearchParameter>(
        {
            keyword: "",
            category: "",
            page: 1,
            size: 6,          /*  the number of cards that will display in each page */
            maxPages:10
        }
    );


    const [selectedCategory, setSelectedCategory] = useState<SelectedCategory>(
        {
            label: "",
            value: ""
        }
    );
    const [selectedPage, setSelectedPage] = useState(1);

    function SearchHandleChange(event: React.FormEvent<HTMLInputElement>) {
        setSearchParameter({
            ...searchParameter,
            keyword: event.currentTarget.value,
        });
    }
    function CategoryHandleChange(
        selectedCategory: SingleValue<{ value: string; label: string; }>,
        actionMeta: ActionMeta<{ value: string; label: string; }>
    ) {
        if (selectedCategory) {
            const newValue = selectedCategory.value;
            setSearchParameter({
                ...searchParameter,
                category: newValue,
            });
            setSelectedCategory(selectedCategory as SelectedCategory);
        }
    }

    const PaginationHandleChange = (event: React.MouseEvent<HTMLButtonElement, MouseEvent>): void => {
        const newPage = Number(event.currentTarget.value);
        setSearchParameter((prevSearchParameter) => ({
            ...prevSearchParameter,
            page: newPage,
        }));
        setSelectedPage(newPage);
    };;

    const handlePrevious = () => {
        if (searchParameter.page > 1) {
            setSearchParameter ({
                ...searchParameter,
                page: searchParameter.page - 1,
            });
            setSelectedPage(searchParameter.page - 1);
        }
    };

    const handleNext = () => {
        if (searchParameter.page < 9) {
            setSearchParameter ({
                ...searchParameter,
                page: searchParameter.page + 1,
            });
            setSelectedPage(searchParameter.page + 1);
        }
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
                        {data.filter((item) => item.category.includes(selectedCategory.value))
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
            <nav aria-label="Navigation ">
                <ul className="pagination justify-content-center"
                >
                    <li className="page-item"><button className="page-link"
                                                      onClick={handlePrevious}
                                                      disabled={searchParameter.page === 1}>Previous</button>
                    </li>

                    <li className="page-item"><button className={`page-link ${selectedPage === 1 ? "active" : ""}`} value={1}
                                                      onClick={PaginationHandleChange}>1</button>
                    </li>

                    <li className="page-item"><button className={`page-link ${selectedPage === 2 ? "active" : ""}`} value={2}
                                                      onClick={PaginationHandleChange}>2</button>
                    </li>

                    <li className="page-item"><button className={`page-link ${selectedPage === 3 ? "active" : ""}`} value={3}
                                                      onClick={PaginationHandleChange}>3</button>
                    </li>

                    <li className="page-item"><button className="page-link"
                                                      onClick={handleNext}
                                                      disabled={searchParameter.page === 9}>Next</button>
                    </li>
                </ul>
            </nav>
        </main >
    )

}

export default SearchProductPage;