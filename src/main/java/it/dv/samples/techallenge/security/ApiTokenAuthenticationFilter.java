/*
 */
package it.dv.samples.techallenge.security;

import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.services.UserService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

/**
 * A security filter for API TOKEN authentication
 *
 * @author davidvotino
 */
public class ApiTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public final Log LOG = LogFactory.getLog(ApiTokenAuthenticationFilter.class);

    private final UserService userService;
    private final TokenExctractor tokenExctractor;

    public ApiTokenAuthenticationFilter(final UserService userService, RequestMatcher requestMatcher) {
        super(requestMatcher);
        this.userService = userService;
        tokenExctractor = new CompositeTokenExtractor(
                new QueryStringTokenExtractor(), // checks on the query string for 'api_key' param
                new RequestHeaderTokenExtractor()); // checks for an header: X-API-TOKEN
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws org.springframework.security.core.AuthenticationException {

        // first of all try to get the token:
        String token = tokenExctractor.extract(request);

        // no token present means credential not found
        if (StringUtils.isEmpty(token)) {
            throw new AuthenticationCredentialsNotFoundException("An API Token could not be found");
        }

        User userInfo = userService.getByToken(token);

        // no user bound to the given token means bad credentials
        if (null == userInfo) {
            throw new BadCredentialsException("The API Token provided is not a valid token");
        }

        // DEFAULT ROLE USER. There's no Role based filtering anyway
        List<? extends GrantedAuthority> authorities = Collections.unmodifiableList(Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        UsernamePasswordAuthenticationToken authResult = new UsernamePasswordAuthenticationToken(userInfo.getUsername(), null, authorities);
        authResult.setDetails(authenticationDetailsSource.buildDetails(request));

        return authResult;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult)
            throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("Authentication success. Updating SecurityContextHolder to contain: " + authResult);
        }

        SecurityContextHolder.getContext().setAuthentication(authResult);

        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }

        chain.doFilter(request, response);
    }

}
