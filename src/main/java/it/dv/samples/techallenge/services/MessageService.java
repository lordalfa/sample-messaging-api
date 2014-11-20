/*
 */
package it.dv.samples.techallenge.services;

import it.dv.samples.techallenge.model.Message;
import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.model.exception.ItemNotFoundException;
import it.dv.samples.techallenge.model.exception.MessageTooLongException;
import it.dv.samples.techallenge.repositories.MessageRepository;
import it.dv.samples.techallenge.repositories.UserRepository;
import it.dv.samples.techallenge.repositories.search.MessageSearchCommand;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service for dealing with user messages
 *
 * @author davidvotino
 */
@Service()
public class MessageService {

    public final static Log LOG = LogFactory.getLog(MessageService.class);

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves user messages, also including messages from user he follows if needed
     *
     * @param username the user for which to retrieve messages
     * @param searchterm optional search term to filter messages
     * @param includeFollowed wether to include also messages from people this user follows
     * @return
     */
    public List<Message> getUserMessages(String username, String searchterm, boolean includeFollowed) {
        MessageSearchCommand command = new MessageSearchCommand();
        command.setAuthor(username);
        command.setSearchTerm(searchterm);
        command.setIncludeFollowed(includeFollowed);
        return messageRepository.find(command);

    }

    /**
     * POst a message text for the given user
     *
     * @param username the author
     * @param text the text
     * @return
     */
    @Transactional
    public Message post(String username, String text) throws ItemNotFoundException, MessageTooLongException {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(text)) {
            throw new IllegalArgumentException("Must provide a valid author and a valid text");
        }

        User sourceUser = userRepository.get(username);
        // validates input
        if (null == sourceUser) {
            throw new ItemNotFoundException(username, User.class.getSimpleName());
        }

        if (text.length() > Message.MAX_LENGTH) {
            throw new MessageTooLongException(text.length(), Message.MAX_LENGTH);
        }

        return messageRepository.create(username, text.trim());
    }

    /**
     * Removes the given message if it exists
     *
     * @param messageId
     * @return the removed message data
     */
    public Message delete(long messageId) {
        return messageRepository.delete(messageId);
    }

}
