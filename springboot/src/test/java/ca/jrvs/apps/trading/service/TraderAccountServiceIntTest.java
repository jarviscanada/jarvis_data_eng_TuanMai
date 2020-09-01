package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.TestConfig;
import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.TraderDao;
import ca.jrvs.apps.trading.model.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TraderAccountServiceIntTest {

    private TraderAccountView savedView;

    @Autowired
    private TraderAccountService traderAccountService;

    @Autowired
    private TraderDao traderDao;

    @Autowired
    private AccountDao accountDao;

    private TraderAccountView tav;
    private Trader createTrader;

//    @Before
//    public void setUp() throws Exception {
//    }

    @After
    public void tearDown() throws Exception {
        accountDao.deleteAll();
        traderDao.deleteAll();
    }

    @Before
    @Test
    public void T1createTraderAndAccount() {

        createTrader = new Trader();
        createTrader.setId(5);
        createTrader.setFirstName("Tuan");
        createTrader.setLastName("Mai");
        createTrader.setDob(new Date(1990, 1, 2));
        createTrader.setCountry("Canada");
        createTrader.setEmail("tuan@jrvs.com");

        tav = traderAccountService.createTraderAndAccount(createTrader);

        assertEquals(createTrader.getFirstName(), tav.getTrader().getFirstName());
//
//        Account createAccount = new Account();
//        createAccount.setTraderId(createTrader.getId());
//        createAccount.setAmount(1234.56);
//        accountDao.save(createAccount);
    }



    @Test
    public void T2deposit() {

        traderAccountService.deposit(tav.getTrader().getId(), 123.45);
        assertEquals((Double) 123.45, accountDao.findById(createTrader.getId()).get().getAmount());
    }

    @Test
    public void T3withdraw() {
        traderAccountService.deposit(tav.getTrader().getId(), 123.45);
        traderAccountService.withdraw(tav.getTrader().getId(), 123.45);
        assertEquals((Double) 0.0, accountDao.findById(createTrader.getId()).get().getAmount());
    }

    @Test
    public void T4deleteTraderById() {
        try {
            traderAccountService.deleteTraderById(createTrader.getId());
            fail();
        } catch (Exception ex){
            assertTrue(true);
        }
    }
}