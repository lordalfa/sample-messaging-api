/*
 */
package it.dv.samples.techallenge.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

/**
 * A token extractor looking for the token in a request header<br/>
 * Header name check is case insensitive
 *
 * @author davidvotino
 */
public class RequestHeaderTokenExtractor implements TokenExctractor {

    private final static String DEFAULT_HEADER_NAME = "X-API-TOKEN";
    private final String headerName;

    public RequestHeaderTokenExtractor(String headerName) {
        Assert.hasText(headerName, "Must provide an header name to look at");
        this.headerName = headerName;
    }

    public RequestHeaderTokenExtractor() {
        this(DEFAULT_HEADER_NAME);
    }

    @Override
    public String extract(HttpServletRequest request) {
        return request.getHeader(headerName);
    }

}
