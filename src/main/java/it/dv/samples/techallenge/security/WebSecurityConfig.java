/*
 */
package it.dv.samples.techallenge.security;

import it.dv.samples.techallenge.api.ApiEndpoints;
import it.dv.samples.techallenge.services.UserService;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration for Web authentication trough API TOKEN
 *
 * @author davidvotino
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String API_SECURED_URL_PATTERN = ApiEndpoints.API_BASE_ENDPOINT + "**";

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.exceptionHandling().authenticationEntryPoint(new Http403ForbiddenEntryPoint());
        http.addFilterAfter(tokenBasedAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .anonymous().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .x509().disable()
                .jee().disable()
                .csrf().disable()
                .logout().disable()
                .rememberMe().disable();
        // @formatter:on
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // @formatter:off
        // NOTE: disable in production
        web.debug(false);
        // @formatter:on
    }

    private Filter tokenBasedAuthenticationFilter() throws Exception {
        return new ApiTokenAuthenticationFilter(userService, new AntPathRequestMatcher(API_SECURED_URL_PATTERN));
    }

}
