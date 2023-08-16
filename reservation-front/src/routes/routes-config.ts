import HomePage from "../pages/Home";
import AuthGuard from "../guards/AuthGuard";
import ShowProduct from "../pages/Product/ShowProduct";
import ContactPage from "../pages/Contact";
import ProfilePage from "../pages/Profile";
import LoginPage from "../pages/Login";
import GuestGuard from "../guards/GuestGuard";
import RegisterPage from "../pages/Register";
import ForgetPassword from "../pages/ForgotPassword";
import SearchProductPage from "../pages/Product/Search";
import ProductDetails from "../pages/Product/ProductDetails";
import ErrorPage from "../pages/Error";


interface RouterConfig {
    path: string,
    component: React.FC,
    guard?: typeof AuthGuard | typeof GuestGuard;
}

export const routesConfig: RouterConfig[] = [
    {path: '/', component: HomePage, guard: AuthGuard},
    {path: '/show-product', component: ShowProduct, guard: AuthGuard},
    {path: '/contact', component: ContactPage, guard: AuthGuard},
    {path: '/profile', component: ProfilePage, guard: AuthGuard},
    {path: '/login', component: LoginPage, guard: GuestGuard},
    {path: '/register', component: RegisterPage, guard: GuestGuard},
    {path: '/forget-password', component: ForgetPassword, guard: GuestGuard},
    {path: '/search-product', component: SearchProductPage, guard: AuthGuard},
    {path: '/product-details', component: ProductDetails, guard: AuthGuard},
    {path: '*', component: ErrorPage},
];
