package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PositionDao {

    private static final Logger logger = LoggerFactory.getLogger(PositionDao.class);

    private final String TABLE_NAME = "position";
    private final String ID_COLUMN = "id";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PositionDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public List<Position> getPosition(Integer id) {
        String selectSql = "SELECT account_id, ticker, sum(size) AS position FROM " + TABLE_NAME + " WHERE account_id=?";
        List<Position> positions = jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Position.class), id);
        return positions;
    }
}
