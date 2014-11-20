package it.dv.samples.techallenge.utils;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

/**
 * Utilities for accessing authentication context variables in an agnostic way
 *
 * @author davidvotino
 */
public abstract class AuthenticationUtils {

    /**
     * Retrieves current user name from the authentication context
     *
     * @return
     */
    public static String getCurrentUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uname = null;
        if (null != authentication) {
            uname = (String) authentication.getPrincipal();
        }

        return uname;
    }

    /**
     * Checks wether the given user is the one that is also logged in, otherwise throws an exception
     *
     * @param username the user to check
     * @throws BadCredentialsException if the user to check is not the one that is logged in, or there's no logged user at all
     */
    public static void checkAuthenticatedUser(String username) throws BadCredentialsException {

        String authUser = getCurrentUserName();
        boolean shouldRaiseException
                = StringUtils.isEmpty(username) // no user specified
                || StringUtils.isEmpty(authUser) // no user authenticated (somehow bypassed security)
                || !username.equals(authUser); // not the authenticated user!
        if (shouldRaiseException) {
            throw new BadCredentialsException("You're trying to access content you shouldn't access!");
        }

    }
}
