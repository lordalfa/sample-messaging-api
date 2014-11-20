/*
 */
package it.dv.samples.techallenge.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * A token extractor that cycles through a list of token extractor implementations until it finds a valid token, or there are no more extractors to
 * check
 * <p>
 * Extractors are checked in the order you provided when building this composite extractor
 * </p>
 *
 * @author davidvotino
 */
public class CompositeTokenExtractor implements TokenExctractor {

    private final TokenExctractor[] extractors;

    public CompositeTokenExtractor(TokenExctractor... extractors) {
        this.extractors = extractors;
    }

    @Override
    public String extract(HttpServletRequest request) {
        String token = null;

        for (TokenExctractor exctractor : extractors) {
            token = exctractor.extract(request);
            if (!StringUtils.isEmpty(token)) {
                break;
            }
        }

        return token;
    }

}
