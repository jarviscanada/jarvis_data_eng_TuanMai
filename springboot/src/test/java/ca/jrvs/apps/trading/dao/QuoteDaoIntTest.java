package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteDaoIntTest {

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void insertOne() {
        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
    }

    @After
    public void deleteOne() {
        quoteDao.deleteById(savedQuote.getId());
    }

    @Test
    public void save() {
        Quote testQuote = new Quote();
        testQuote.setAskPrice(20d);
        testQuote.setAskSize(20);
        testQuote.setBidPrice(20.2d);
        testQuote.setBidSize(20);
        testQuote.setId("FB");
        testQuote.setLastPrice(20.1d);
        quoteDao.save(testQuote);
        Quote quote = quoteDao.findById(testQuote.getTicker()).get();
        assertEquals(testQuote, quote);
    }


    @Test
    public void findById() {
        Quote quote = quoteDao.findById(savedQuote.getTicker()).get();
        assertEquals(savedQuote, quote);
    }

    @Test
    public void existsById() {
        assertTrue(quoteDao.existsById(savedQuote.getId()));
    }

    @Test
    public void deleteById() {
        quoteDao.deleteById(savedQuote.getId());
        assertFalse(quoteDao.existsById(savedQuote.getId()));
    }

    @Test
    public void findAllById() {
        List<Quote> quotes = (List) quoteDao.findAll();
        assertEquals(savedQuote, quotes.get(0));
    }

    @Test
    public void testDeleteAll() {
        quoteDao.deleteAll();
        assertFalse(quoteDao.existsById(savedQuote.getId()));
    }
}