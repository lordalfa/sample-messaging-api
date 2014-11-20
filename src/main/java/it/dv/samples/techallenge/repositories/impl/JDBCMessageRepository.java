/*
 */
package it.dv.samples.techallenge.repositories.impl;

import it.dv.samples.techallenge.model.Message;
import it.dv.samples.techallenge.repositories.MessageRepository;
import it.dv.samples.techallenge.repositories.search.MessageSearchCommand;
import it.dv.samples.techallenge.utils.Defaults;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * a JDBC implementation of message repository
 *
 * @author davidvotino
 */
@Repository
public class JDBCMessageRepository implements MessageRepository {

    public final static Log LOG = LogFactory.getLog(MessageRepository.class);
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Message get(long id) {
        String sql = "select * from messages where id = :id";

        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);

        Message result = null;

        try {
            result = namedParameterJdbcTemplate.queryForObject(sql, namedParameters, new BeanPropertyRowMapper<Message>(Message.class));
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

    @Override
    public Message create(String author, String text) {
        String sql = "insert into messages (author, text, creation_time) values(:author, :text, :creationTime)";

        Map<String, Object> values = new HashMap<String, Object>();
        values.put("author", author);
        values.put("text", text);
        values.put("creationTime", System.currentTimeMillis());
        SqlParameterSource namedParameters = new MapSqlParameterSource(values);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, namedParameters, keyHolder);
        long key = keyHolder.getKey().longValue();
        return get(key);
    }

    @Override
    public Message delete(long id) {
        Message message = get(id);

        if (null != message) {
            String sql = "delete from messages where id = :id";
            SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
            namedParameterJdbcTemplate.update(sql, namedParameters);
        }

        return message;
    }

    @Override
    public List<Message> find(MessageSearchCommand command) {
        StringBuilder sql = new StringBuilder("select * from messages where 1 = 1 ");
        int rowLimit = DEFAULT_ROW_LIMIT;
        boolean orderBydateDescending = true;
        Map<String, Object> values = new HashMap<String, Object>();

        if (null != command) {
            rowLimit = Defaults.or(command.getMaxMessages(), rowLimit);
            orderBydateDescending = command.isOrderByDateDescending();

            String author = command.getAuthor();
            String searchTerm = command.getSearchTerm();

            if (!StringUtils.isEmpty(author)) {
                if (command.isIncludeFollowed()) {
                    sql.append(" and (author = :author or author in (select following from followers where username = :author)) ");
                } else {
                    sql.append(" and author = :author ");
                }

                values.put("author", author);
            }
            if (!StringUtils.isEmpty(searchTerm)) {
                sql.append(" and lower(text) like :term ");
                values.put("term", "%" + searchTerm.trim().toLowerCase() + "%");
            }

        }

        if (orderBydateDescending) {
            sql.append(" order by creation_time desc ");
        }

        if (rowLimit > 0) {
            sql.append(" limit :limit ");
            values.put("limit", rowLimit);
        }

        SqlParameterSource namedParameters = new MapSqlParameterSource(values);

        List<Message> result = Collections.emptyList();

        try {
            result = namedParameterJdbcTemplate.query(sql.toString(), namedParameters, new BeanPropertyRowMapper<Message>(Message.class));
        } catch (EmptyResultDataAccessException e) {
            // nothing
        }

        return result;
    }

}
