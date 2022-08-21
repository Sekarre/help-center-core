package com.sekarre.helpcentercore.security;

import com.sekarre.helpcentercore.domain.Role;
import com.sekarre.helpcentercore.domain.User;
import com.sekarre.helpcentercore.domain.enums.RoleName;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserDetailsHelper {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static String getUserToken() {
        return getCurrentUser().getToken();
    }

    public static String getCurrentUserFullName() {
        return getCurrentUser().getFirstName() + " " + getCurrentUser().getLastName();
    }

    public static boolean checkForRole(RoleName roleName) {
        return getCurrentUser().getRoles().stream()
                .map(Role::getName)
                .anyMatch(rn -> rn.equals(roleName));
    }
}