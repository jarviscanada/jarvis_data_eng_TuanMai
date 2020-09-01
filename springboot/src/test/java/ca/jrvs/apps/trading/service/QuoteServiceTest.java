package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class QuoteServiceTest {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteDao quoteDao;

    private Quote savedQuote;

    @Before
    public void setUp() {
        quoteDao.deleteAll();

        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);
    }

    @Test
    public void updateMarketData() {
        quoteService.updateMarketData();

        Quote testQuote = quoteDao.findById(savedQuote.getTicker()).get();
        assertEquals(savedQuote.getTicker(), testQuote.getTicker());
    }

    @Test
    public void saveQuotes() {
        Quote quote1 = new Quote();
        quote1.setAskPrice(30d);
        quote1.setAskSize(30);
        quote1.setBidPrice(30.2d);
        quote1.setBidSize(30);
        quote1.setId("AAPL");
        quote1.setLastPrice(30.1d);

        Quote quote2 = new Quote();
        quote2.setAskPrice(40d);
        quote2.setAskSize(40);
        quote2.setBidPrice(40.2d);
        quote2.setBidSize(40);
        quote2.setId("FB");
        quote2.setLastPrice(40.1d);

        List<Quote> quoteData = new ArrayList<>();
        quoteData.add(quote1);
        quoteData.add(quote2);

        List<Quote> quotes = (List<Quote>) quoteDao.saveAll(quoteData);
        assertEquals(2, quotes.size());
        assertEquals("FB", quotes.get(1).getTicker());
    }

    @Test
    public void saveQuote() {
        Quote testQuote = new Quote();
        testQuote.setAskPrice(20d);
        testQuote.setAskSize(20);
        testQuote.setBidPrice(20.2d);
        testQuote.setBidSize(20);
        testQuote.setId("FB");
        testQuote.setLastPrice(20.1d);
        quoteDao.save(testQuote);

        Quote quote = quoteDao.findById(testQuote.getTicker()).get();
        assertEquals(testQuote.getTicker(), quote.getTicker());
    }

    @Test
    public void findIexQuoteByTicker() {
        IexQuote iexQuote = quoteService.findIexQuoteByTicker("GOOGL");
        assertEquals("GOOGL", iexQuote.getSymbol());
        try {
            quoteService.findIexQuoteByTicker("In a hole there lived a hobbit");
            fail();
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test
    public void findAllQuotes() {
        List<Quote> retrievedQuotes = quoteService.findAllQuotes();
        assertEquals(savedQuote.getId(), retrievedQuotes.get(0).getId());
    }
}