/*
 */
package it.dv.samples.techallenge.repositories;

import it.dv.samples.techallenge.model.User;
import java.util.List;

/**
 * User repository interface
 *
 * @author davidvotino
 */
public interface UserRepository {

    /**
     * Retrieves the user given it's API TOKEN
     *
     * @param token
     * @return
     */
    public User getbyToken(String token);

    /**
     * Retrieves a user given it's id
     *
     * @param username
     * @return
     */
    public User get(String username);

    /**
     * Creates a new user
     *
     * @param username
     * @param password
     * @return
     */
    public User create(String username, String password);

    /**
     * Makes source user follow target user.
     *
     * @param sourceUser
     * @param targetUser
     * @return true if the state has been changed, false if it already was that way
     */
    public boolean follow(String sourceUser, String targetUser);

    /**
     * Makes source user unfollow target user.
     *
     * @param sourceUser
     * @param targetUser
     * @return true if the state has been changed, false if it already was that way
     */
    public boolean unfollow(String sourceUser, String targetUser);

    /**
     * Retrieves the usernames of the followers of the given user.
     *
     * @param username
     * @return list of followers or an empty list if user does not exist
     */
    public List<String> getFollowers(String username);

    /**
     * Retrieves the usernames of the users the given user is following
     *
     * @param username
     * @return list of users the given user is followinf or an empty list if user does not exist
     */
    public List<String> getFollowing(String username);

}
