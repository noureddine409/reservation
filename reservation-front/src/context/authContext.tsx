import React, {createContext, ReactNode, useContext, useState} from 'react';
import {User} from '../model/user.model';
import {AUTHENTICATED_USER_KEY} from "../common/constants";

interface AuthContextType {
    authenticatedUser: User | null;
    setAuthenticatedUser: React.Dispatch<React.SetStateAction<User | null>>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};

interface AuthProviderProps {
    children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({children}) => {
    const [authenticatedUser, setAuthenticatedUser] = useState<User | null>(() => {
        const storedUser = localStorage.getItem(AUTHENTICATED_USER_KEY);
        return storedUser ? JSON.parse(storedUser) : null;
    });

    const safeSetAuthenticatedUser: AuthContextType['setAuthenticatedUser'] = (user) => {
        if (user === null || typeof user === 'object') {
            setAuthenticatedUser(user);
        } else {
            console.error('Invalid user object:', user);
        }
    };

    return (
        <AuthContext.Provider value={{authenticatedUser, setAuthenticatedUser: safeSetAuthenticatedUser}}>
            {children}
        </AuthContext.Provider>
    );
};
