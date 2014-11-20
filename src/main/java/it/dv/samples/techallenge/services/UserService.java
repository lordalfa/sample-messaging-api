/*
 */
package it.dv.samples.techallenge.services;

import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.model.exception.ItemAlreadyExistsException;
import it.dv.samples.techallenge.model.exception.ItemNotFoundException;
import it.dv.samples.techallenge.repositories.UserRepository;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * Service for retrieving user related information
 *
 * @author davidvotino
 */
@Service()
public class UserService {

    public final static Log LOG = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a user given the API TOKEN
     *
     * @param token
     * @return
     */
    public User getByToken(String token) {
        return userRepository.getbyToken(token);
    }

    /**
     * Retrieves the names of the users following the given one
     *
     * @param username
     * @return
     */
    public List<String> getFollowers(String username) {
        return userRepository.getFollowers(username);
    }

    /**
     * Retrieves a list with the names of the users the given one is following
     *
     * @param username
     * @return
     */
    public List<String> getFollowing(String username) {
        return userRepository.getFollowing(username);
    }

    /**
     * Makes the source user follow the target user
     *
     * @param source not null
     * @param target not null
     */
    @Transactional
    public void follow(String source, String target) throws ItemNotFoundException {

        User sourceUser = userRepository.get(source);
        User targetUser = userRepository.get(target);

        // validates input
        if (null == sourceUser || null == targetUser) {
            String notFoundItem = sourceUser != null ? target : source;
            throw new ItemNotFoundException(notFoundItem, User.class.getSimpleName());
        }

        userRepository.follow(source, target);
    }

    /**
     * Makes the source user unfollow the target user, if it was following it.
     *
     * @param source not null
     * @param target not null
     */
    @Transactional
    public void unfollow(String source, String target) throws ItemNotFoundException {
        User sourceUser = userRepository.get(source);
        User targetUser = userRepository.get(target);

        // validates input
        if (null == sourceUser || null == targetUser) {
            throw new ItemNotFoundException(sourceUser, User.class.getSimpleName());
        }
        userRepository.unfollow(source, target);
    }

    /**
     * Creates a new user in the system if it doesn't already exsist.
     *
     * @param username not null
     * @param password not null
     * @return
     */
    @Transactional
    public User createUser(String username, String password) throws ItemAlreadyExistsException {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("You didn't provide either an username or a password.");
        }

        User user = userRepository.get(username);

        if (null != user) {
            throw new ItemAlreadyExistsException(username, User.class.getSimpleName());
        }

        return userRepository.create(username, password);
    }

    /**
     * Retrieves a User object given it's unique identifier
     *
     * @param username
     * @return
     */
    public User get(String username) {
        return userRepository.get(username);
    }

    /**
     * Check the given credentials and returns the corresponing user
     * <p>
     * Throws exceptions if something is not right
     *
     * @param username
     * @param password
     * @return the checked user
     * @throws ItemNotFoundException
     * @throws BadCredentialsException
     */
    public User checkCredentials(String username, String password) throws ItemNotFoundException, BadCredentialsException {
        // checkCredentials credentials
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new IllegalArgumentException("You didn't provide either an username or a password.");
        }
        User user = get(username);
        if (user == null) {
            throw new ItemNotFoundException(username, User.class.getSimpleName());
        }

        if (!password.equals(user.getPassword())) {
            throw new BadCredentialsException("You providedd a wrong password");
        }

        return user;
    }

}
