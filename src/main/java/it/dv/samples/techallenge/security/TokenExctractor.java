/*
 */
package it.dv.samples.techallenge.security;

import javax.servlet.http.HttpServletRequest;

/**
 * Request Token extractor interface
 *
 * @author davidvotino
 */
public interface TokenExctractor {

    /**
     * Extracts an API TOKEN from the given request
     *
     * @param request
     * @return the API TOKEN if present, null otherwise
     */
    String extract(HttpServletRequest request);

}
