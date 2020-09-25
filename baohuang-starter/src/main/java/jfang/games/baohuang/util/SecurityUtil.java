package jfang.games.baohuang.util;

import jfang.games.baohuang.adapter.UserDetailAdapter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;

import java.security.Principal;

/**
 * @author jfang
 */
public class SecurityUtil {

    public static Authentication getCurrentAuth() {
         return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername() {
        Authentication authentication = getCurrentAuth();
        return authentication.getName();
    }

    public static UserDetailAdapter getCurrentUser() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) getCurrentAuth();
        return (UserDetailAdapter) authenticationToken.getPrincipal();
    }

    public static UserDetailAdapter getCurrentUser(Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        return (UserDetailAdapter) authenticationToken.getPrincipal();
    }

    public static UserDetailAdapter getEventUser(AbstractSubProtocolEvent event) {
        UsernamePasswordAuthenticationToken user = (UsernamePasswordAuthenticationToken) event.getUser();
        if (user == null) {
            return null;
        }
        return (UserDetailAdapter) user.getPrincipal();
    }
}
