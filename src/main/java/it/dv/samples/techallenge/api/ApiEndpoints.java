package it.dv.samples.techallenge.api;

/**
 * API Endpoint constants
 *
 * @author davidvotino
 */
public interface ApiEndpoints {

    public final static String SERVICES_BASE_ENDPOINT = "/rest/";
    public final static String API_BASE_ENDPOINT = SERVICES_BASE_ENDPOINT + "api/";
    public final static String ACCESS_BASE_ENDPOINT = SERVICES_BASE_ENDPOINT + "access";
    public final static String API_ENDPOINT_USERS = API_BASE_ENDPOINT + "users";
    public final static String API_ENDPOINT_MESSAGES = API_BASE_ENDPOINT + "messages";

}
