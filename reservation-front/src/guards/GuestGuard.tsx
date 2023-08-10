import {Navigate} from "react-router-dom";
import {useAuth} from "../context/authContext";

interface GuestGuardProps {
    children: React.ReactNode;
}

const GuestGuard: React.FC<GuestGuardProps> = ({ children }) => {
    const { authenticatedUser } = useAuth();
    if (authenticatedUser) {
        return <Navigate to="/error-404" />;
    }
    return <>{children}</>;
};

export default GuestGuard;
