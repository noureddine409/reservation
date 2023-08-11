

import React from "react";
import AuthGuard from "./AuthGuard";
import GuestGuard from "./GuestGuard";

interface RouteGuardWrapperProps {
    guard?: typeof AuthGuard | typeof GuestGuard;
    component: React.ComponentType;
}

const RouteGuardWrapper: React.FC<RouteGuardWrapperProps> = ({ guard: Guard, component: Component, ...props }) => {
    if (Guard) {
        return <Guard><Component {...props} /></Guard>;
    } else {
        return <Component {...props} />;
    }
};

export default RouteGuardWrapper;
