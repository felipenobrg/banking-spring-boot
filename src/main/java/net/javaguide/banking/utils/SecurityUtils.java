package net.javaguide.banking.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import net.javaguide.banking.security.model.UserDetail;

public class SecurityUtils {

    private SecurityUtils() {
        // Private constructor to prevent instantiation
    }

    public static String getAuthenticatedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetail) {
            return ((UserDetail) principal).getUsername();
        } else if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}
