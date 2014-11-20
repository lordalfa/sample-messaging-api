/*
 */
package it.dv.samples.techallenge.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import it.dv.samples.techallenge.model.Message;
import it.dv.samples.techallenge.services.MessageService;
import it.dv.samples.techallenge.utils.AuthenticationUtils;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Message REST APIs
 *
 * @author davidvotino
 */
@RestController
@RequestMapping(ApiEndpoints.API_ENDPOINT_MESSAGES)
@Api(position = 2, value = "Message API", description = "Secured API for managing user messages")
public class MessageApi {

    public final static Log LOG = LogFactory.getLog(MessageApi.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    @ApiOperation(response = Message.class, value = "post-message", notes = "Creates a message for the given user with the given text. Must be the same user as the authenticated user.")
    @ApiResponses(value = {
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_BAD_REQUEST, message = "BAD_REQUEST  wrong parameters passed."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_NOT_FOUND, message = "NOT_FOUND  the user trying to post doesn't exist."),
        @ApiResponse(response = Message.class, code = HttpServletResponse.SC_CREATED, message = "CREATED  posted succesfully"),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_FORBIDDEN, message = "FORBIDDEN  the source user is not the one calling this API."),
        @ApiResponse(response = String.class, code = HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message = "INTERNAL_SERVER_ERROR  Internal application problem")})
    @ResponseStatus(HttpStatus.CREATED)
    public Message post(@PathVariable String username, @RequestBody String text) {

        // the user asking for the messages MUST be the same one that is authenticated
        AuthenticationUtils.checkAuthenticatedUser(username);

        return messageService.post(username, text);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    @ApiOperation(response = List.class, value = "get-messages", notes = "Retrieves the messages by a given person, optionally filtering by text content, and optionally retrieving also the messages from followed people")
    public List<Message> getMessages(@PathVariable String username,
            @RequestParam(required = false) String searchTerm,
            @RequestParam(required = false) Boolean includeFollowed) {

        // the user asking for the messages MUST be the same one that is authenticated
        AuthenticationUtils.checkAuthenticatedUser(username);

        boolean includeFollowedPeopleMessages = includeFollowed != null ? includeFollowed : Boolean.FALSE;

        return messageService.getUserMessages(username, searchTerm, includeFollowedPeopleMessages);
    }
}
