/*
 */
package it.dv.samples.techallenge.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.model.UserToken;
import it.dv.samples.techallenge.services.UserService;
import it.dv.samples.techallenge.utils.AuthenticationUtils;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * User management Apis
 *
 * @author davidvotino
 */
@RestController
@RequestMapping(ApiEndpoints.API_ENDPOINT_USERS)
@Api(position = 1, value = "User API", description = "Secured API for managing users follow list")
public class UserApi {

    public final static Log LOG = LogFactory.getLog(UserApi.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ApiOperation(response = UserToken.class, value = "whoami", notes = "Tells something about the authenticated user")
    public UserToken me() {
        User user = userService.get(AuthenticationUtils.getCurrentUserName());
        UserToken token = new UserToken();
        token.setUsername(user.getUsername());
        token.setApiToken(user.getApiToken());
        return token;
    }

    @RequestMapping(value = "/{username}/followers", method = RequestMethod.GET)
    @ApiOperation(response = List.class, value = "get-followers", notes = "Retrieves the usernames of the people following the given person")
    public List<String> getFollowers(@PathVariable String username) {
        return userService.getFollowers(username);
    }

    @RequestMapping(value = "/{username}/following", method = RequestMethod.GET)
    @ApiOperation(response = List.class, value = "get-following", notes = "Retrieves the usernames of the people the given user is following")
    public List<String> getFollowing(@PathVariable String username) {
        return userService.getFollowing(username);
    }

    @RequestMapping(value = "/{username}/follow/{target}", method = RequestMethod.POST)
    @ApiOperation(response = Void.class, value = "follow", notes = "Makes the source person follow the target person. Caller must be the source user for consistency.")
    @ApiResponses(value = {
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_NOT_FOUND, message = "NOT FOUND  one or both the given users to link can't be found."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_FORBIDDEN, message = "FORBIDDEN  the source user is not the one calling this API.")
    })
    public void follow(@PathVariable(value = "username") String source, @PathVariable String target) {
        // the user asking for the messages MUST be the same one that is authenticated
        AuthenticationUtils.checkAuthenticatedUser(source);

        userService.follow(source, target);
    }

    @RequestMapping(value = "/{username}/follow/{target}", method = RequestMethod.DELETE)
    @ApiOperation(response = Void.class, value = "unfollow", notes = "Makes the source person unfollow the target person")
    @ApiResponses(value = {
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_NOT_FOUND, message = "NOT FOUND  one or both the given users to link can't be found."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_FORBIDDEN, message = "FORBIDDEN  the source user is not the one calling this API.")
    })
    public void unfollow(@PathVariable(value = "username") String source, @PathVariable String target) {
        // the user asking for the messages MUST be the same one that is authenticated
        AuthenticationUtils.checkAuthenticatedUser(source);

        userService.unfollow(source, target);
    }

}
