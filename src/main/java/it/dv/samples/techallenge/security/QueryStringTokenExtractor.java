/*
 */
package it.dv.samples.techallenge.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.Assert;

/**
 * A token extractor looking for the token in the query string parameters.
 *
 * @author davidvotino
 */
public class QueryStringTokenExtractor implements TokenExctractor {

    private final static String DEFAULT_QUERY_STRING_PARAMETER_NAME = "api_key";
    private final String queryStringParameterName;

    public QueryStringTokenExtractor(String queryStringParameterName) {
        Assert.hasText(queryStringParameterName, "Must provide a query string parameter name to look at");
        this.queryStringParameterName = queryStringParameterName;
    }

    public QueryStringTokenExtractor() {
        this(DEFAULT_QUERY_STRING_PARAMETER_NAME);
    }

    @Override
    public String extract(HttpServletRequest request) {
        return request.getParameter(queryStringParameterName);
    }

}
