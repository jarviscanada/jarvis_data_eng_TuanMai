package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.model.domain.Trader;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class SecurityOrderDaoIntTest {

    @Autowired
    private SecurityOrderDao securityOrderDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private QuoteDao quoteDao;

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
    public void findAllBySecurityOrderId() {
        List<SecurityOrder> testList = Lists.newArrayList(securityOrderDao.findAllById(Arrays.asList(savedSecurityOrder.getId(), -1)));
        assertEquals(1, testList.size());
        assertEquals(savedSecurityOrder.getNotes(), testList.get(0).getNotes());
    }
}