package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class QuoteDao implements CrudRepository<Quote, String> {

    private static final String TABLE_NAME = "quote";
    private static final String ID_COLUMN_NAME = "ticker";

    private static final Logger logger = LoggerFactory.getLogger(QuoteDao.class);
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public QuoteDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(TABLE_NAME);
    }

    @Override
    public Quote save(Quote quote) {
        if (existsById(quote.getTicker())) {
            int updatedRowNo = updateOne(quote);
            if (updatedRowNo != 1) {
                throw new DataRetrievalFailureException("ERROR: Unable to update quote");
            }
        } else {
            addOne(quote);
        }
        return quote;
    }

    /**
     * Helper function which saves one quote
     */
    private void addOne(Quote quote) {
        SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(quote);
        int row = simpleJdbcInsert.execute(parameterSource);
        if (row != 1) {
            throw new IncorrectResultSizeDataAccessException("ERROR: Failed to insert", 1, row);
        }
    }

    /**
     * Helper function which updates one quote
     */
    private int updateOne(Quote quote) {
        String updateSql = "UPDATE quote SET last_price=?, bid_price=?, bid_size=?, ask_price=?, ask_size=? WHERE ticker=?";
        return jdbcTemplate.update(updateSql, makeUpdateValues(quote));
    }

    private Object[] makeUpdateValues(Quote quote) {
        Object[] updateValues = new Object[6];
        updateValues[0] = quote.getLastPrice();
        updateValues[1] = quote.getBidPrice();
        updateValues[2] = quote.getBidSize();
        updateValues[3] = quote.getAskPrice();
        updateValues[4] = quote.getAskSize();
        updateValues[5] = quote.getTicker();
        return updateValues;
    }

    @Override
    public <S extends Quote> Iterable<S> saveAll(Iterable<S> quotes) {
        quotes.forEach(this::save);
        return quotes;
    }

    @Override
    public Iterable<Quote> findAll() {
        return getAllQuotes();
    }

    private List<Quote> getAllQuotes() {
        String selectSql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(selectSql, BeanPropertyRowMapper.newInstance(Quote.class));
    }

    @Override
    public Optional<Quote> findById(String ticker) {
        try {
            return Optional.of(getQuoteByTicker(ticker, false));
        } catch (DataRetrievalFailureException ex) {
            return Optional.empty();
        }
    }

    private Quote getQuoteByTicker(String ticker, boolean forUpdate) {
        Quote quote = null;
        String selectSql = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";

        if (forUpdate) {
            selectSql += " for update";
        }
        try {
            quote = jdbcTemplate
                    .queryForObject(selectSql,
                            BeanPropertyRowMapper.newInstance(Quote.class), ticker);
        } catch (EmptyResultDataAccessException ex) {
            logger.debug("Can't find quote with ticker: " + ticker, ex);
        }
        if (quote == null) {
            throw new DataRetrievalFailureException("ERROR: Quote not found");
        }
        return quote;
    }


    @Override
    public boolean existsById(String ticker) {
        try {
            getQuoteByTicker(ticker, false);
            return true;
        } catch (DataRetrievalFailureException ex) {
            return false;
        }
    }

    @Override
    public void deleteById(String ticker) {
        deleteQuoteByTicker(ticker);
    }

    private void deleteQuoteByTicker(String ticker) {
        if (ticker == null) {
            throw new IllegalArgumentException("ERROR: Ticker cannot be null");
        }
        String deleteSql = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN_NAME + " =?";
        jdbcTemplate.update(deleteSql, ticker);
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<String> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public long count() {
        String countSql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        return jdbcTemplate.queryForObject(countSql, Integer.class);
    }

    @Override
    public void delete(Quote quote) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends Quote> iterable) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM " + TABLE_NAME;
        jdbcTemplate.update(deleteSql);
    }

}
