/*
 */
package it.dv.samples.techallenge.repositories.impl;

import it.dv.samples.techallenge.model.User;
import it.dv.samples.techallenge.repositories.UserRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * A JDBC implementation of the user repository
 *
 * @author davidvotino
 */
@Repository
public class JDBCUserRepository implements UserRepository {

    public final static Log LOG = LogFactory.getLog(UserRepository.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public User getbyToken(String token) {
        String sql = "select * from users where api_token = :api_token";

        SqlParameterSource namedParameters = new MapSqlParameterSource("api_token", token);

        User result = null;

        try {
            result = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<User>(User.class));
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

    @Override
    public User get(String username) {
        String sql = "select * from users where username = :username";

        SqlParameterSource namedParameters = new MapSqlParameterSource("username", username);

        User result = null;

        try {
            result = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<User>(User.class));
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

    @Override
    public User create(String username, String password) {
        String sql = "insert into users (username, password, api_token) values(:username, :password, :api_token)";

        Map<String, String> values = new HashMap<String, String>();
        values.put("username", username);
        values.put("password", password);
        values.put("api_token", generateUniqueApiToken());
        SqlParameterSource namedParameters = new MapSqlParameterSource(values);

        namedParameterJdbcTemplate.update(sql, namedParameters);

        return get(username);
    }

    @Override
    public boolean follow(String sourceUser, String targetUser) {
        boolean result = false;
        String sql = "insert into followers (username, following) values(:sourceUser, :targetUser)";

        Map<String, String> values = new HashMap<String, String>();
        values.put("targetUser", targetUser);
        values.put("sourceUser", sourceUser);
        SqlParameterSource namedParameters = new MapSqlParameterSource(values);

        try {
            int insert = namedParameterJdbcTemplate.update(sql, namedParameters);
            result = insert > 0;
        } catch (DataIntegrityViolationException dive) {
            // the link already exists, just skip it (returns false later)
        }
        return result;
    }

    @Override
    public boolean unfollow(String sourceUser, String targetUser) {
        String sql = "delete from followers where username = :sourceUser and following = :targetUser";

        Map<String, String> values = new HashMap<String, String>();
        values.put("targetUser", targetUser);
        values.put("sourceUser", sourceUser);
        SqlParameterSource namedParameters = new MapSqlParameterSource(values);

        int insert = namedParameterJdbcTemplate.update(sql, namedParameters);

        return insert > 0;
    }

    @Override
    public List<String> getFollowers(String username) {
        String sql = "select username from followers where following = :username";

        SqlParameterSource namedParameters = new MapSqlParameterSource("username", username);

        List<String> result = Collections.emptyList();

        try {
            result = namedParameterJdbcTemplate.queryForList(sql, namedParameters, String.class);
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

    @Override
    public List<String> getFollowing(String username) {
        String sql = "select following from followers where username = :username";

        SqlParameterSource namedParameters = new MapSqlParameterSource("username", username);

        List<String> result = Collections.emptyList();

        try {
            result = namedParameterJdbcTemplate.queryForList(sql, namedParameters, String.class);
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

    private String generateUniqueApiToken() {
        return UUID.randomUUID().toString();
    }

}
