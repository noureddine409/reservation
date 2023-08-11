import {Navigate} from "react-router-dom";
import {useAuth} from "../context/authContext";

interface AuthGuardProps {
    children: React.ReactNode;
}

const AuthGuard: React.FC<AuthGuardProps> = ({ children }) => {
    const { authenticatedUser } = useAuth();
    if (!authenticatedUser) {
        return <Navigate to="/login" />;
    }
    return <>{children}</>;
};

export default AuthGuard;
