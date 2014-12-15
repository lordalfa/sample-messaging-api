/*
 */
package it.dv.samples.techallenge.auth;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.dv.samples.techallenge.api.ApiEndpoints;
import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.model.UserToken;
import it.dv.samples.techallenge.services.UserService;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication API
 * <p>
 * This is used to create users, and get API tokens
 * </p>
 *
 * @author davidvotino
 */
@RestController
@RequestMapping(value = ApiEndpoints.ACCESS_BASE_ENDPOINT, produces = {"application/json", "application/xml"})
@Api(position = 0, value = "Authentication API", description = "Unsecured API for managing accounts")
public class AuthApi {

    public final static Log LOG = LogFactory.getLog(AuthApi.class);

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(response = User.class, value = "register", notes = "Tries to create the given user with the given credentials. Also generates an unique API TOKEN")
    @ApiResponses(value = {
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_BAD_REQUEST, message = "BAD_REQUEST  wrong parameters passed."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_CONFLICT, message = "CONFLICT  the given user already exists."),
        @ApiResponse(response = User.class, code = HttpServletResponse.SC_CREATED, message = "CREATED  user created succesfully"),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "INTERNAL_SERVER_ERROR  Internal application problem")})
    @ResponseStatus(HttpStatus.CREATED)
    public User create(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {

        return userService.createUser(username, password);
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ApiOperation(response = UserToken.class, value = "token", notes = "Retrieves the API TOKEN for the given user with the given credentials")
    @ApiResponses(value = {
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_BAD_REQUEST, message = "BAD_REQUEST  wrong parameters passed."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_CONFLICT, message = "CONFLICT  specified the wrong password."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_NOT_FOUND, message = "NOT_FOUND  specified user does not exist"),
        @ApiResponse(response = UserToken.class, code = HttpServletResponse.SC_OK, message = "OK  Here's your token")})
    public UserToken getApiToken(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String password) {

        // checkCredentials the credentials ot throws some speaking exceptions
        User user = userService.checkCredentials(username, password);
        UserToken token = new UserToken();
        token.setUsername(username);
        token.setApiToken(user.getApiToken());
        return token;
    }
}
