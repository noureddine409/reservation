
export const ERROR_MESSAGES = {
    required: 'This field is required.',
    minLength: 'Please enter at least {min} characters.',
    maxLength: 'Please enter at most {max} characters.',
    pattern: 'Invalid input.',
    email: 'Invalid email address.',
    name:'Invalid name',
    username:'Invalid username',
    password: 'Invalid password. Password must contain at least 8 characters, one uppercase letter, one lowercase letter, and one digit.',
    passwordMatch: 'Passwords do not match.',
};
export const VALIDATION_RULES = {
    minLength: (min: number) => (v: string) => v.length >= min,
    maxLength: (max: number) => (v: string) => v.length <= max,
    namePattern: /^[a-zA-Z0-9_]+$/,
    emailPattern: /^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/,
    passwordPattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/,
};

export const data = [
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "1",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "2",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "3",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "4",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "5",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "6",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "7",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "8",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "9",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "10",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "11",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "12",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "1",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "2",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "3",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "4",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "5",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "6",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "7",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "8",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "9",
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "category": "Meeting room",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "10",
        "description": "Sed do eiusmod tempor incididunt",
        "category": "Equipments",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "11",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    },
    {
        "imgSrc": "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg",
        "name": "12",
        "description": "Ut enim ad minim veniam, quis nostrud exercitation uex ea.",
        "category": "Kitchen",
        "status": "Availabale",
    }
]


export const options = [
    { value: 'Meeting room', label: 'Meeting room' },
    { value: 'Kitchen', label: 'Kitchen' },
    { value: 'Equipments', label: 'Equipments' },
];

export const DEFAULT_SEARCH_KEYWORD = ""
export const DEFAULT_SEARCH_CATEGORY = {
    label:"",
    value:""
  }
export const DEFAULT_PAGE_NUMBER = 1
export const DEFAULT_PAGE_SIZE =6


