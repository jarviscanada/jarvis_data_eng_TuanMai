package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.*;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class PositionDaoIntTest {

    @Autowired
    private PositionDao positionDao;

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

    private Position savedPosition;
    private SecurityOrder savedSecurityOrder;
    private Account savedAccount;
    private Trader savedTrader;
    private Quote savedQuote;

    @Before
    public void setUp() throws Exception {

        savedQuote = new Quote();
        savedQuote.setAskPrice(10d);
        savedQuote.setAskSize(10);
        savedQuote.setBidPrice(10.2d);
        savedQuote.setBidSize(10);
        savedQuote.setId("AAPL");
        savedQuote.setLastPrice(10.1d);
        quoteDao.save(savedQuote);

        savedTrader = new Trader();
        savedTrader.setFirstName("Tuan");
        savedTrader.setLastName("Mai");
        savedTrader.setDob(new Date(1990, 1, 2));
        savedTrader.setCountry("Canada");
        savedTrader.setEmail("tuan@jrvs.com");
        savedTrader = traderDao.save(savedTrader);

        savedAccount = new Account();
        savedAccount.setTraderId(savedTrader.getId());
        savedAccount.setAmount(1234.56);
        savedAccount = accountDao.save(savedAccount);

        savedSecurityOrder = new SecurityOrder();
        savedSecurityOrder.setAccountId(savedAccount.getId());
        savedSecurityOrder.setStatus("SUCCESS");
        savedSecurityOrder.setPrice(111.11);
        savedSecurityOrder.setSize(10);
        savedSecurityOrder.setTicker(savedQuote.getTicker());
        savedSecurityOrder.setNotes("Purchased");
        savedSecurityOrder = securityOrderDao.save(savedSecurityOrder);

    }

    @After
    public void tearDown() throws Exception {
        securityOrderDao.deleteAll();
        accountDao.deleteAll();
        traderDao.deleteAll();
        quoteDao.deleteAll();
    }

    @Test
    public void getPosition() {
        List<Position> testList = positionDao.getPosition(savedSecurityOrder.getAccountId());
        assertEquals(savedPosition.getPosition(), testList.get(0).getPosition());
    }
}