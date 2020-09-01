package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.TestConfig;
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

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestConfig.class})
@Sql({"classpath:schema.sql"})
public class TraderDaoIntTest {

    @Autowired
    private TraderDao traderDao;

    private Trader savedTrader;

    @Before
    public void setUp() throws Exception {
        savedTrader = new Trader();
        savedTrader.setFirstName("Tuan");
        savedTrader.setLastName("Mai");
        savedTrader.setDob(new Date(1990, 1, 2));
        savedTrader.setCountry("Canada");
        savedTrader.setEmail("tuan@jrvs.com");
        savedTrader = traderDao.save(savedTrader);

    }

    @After
    public void tearDown() throws Exception {
        traderDao.deleteAll();
    }

    @Test
    public void findAllById() {
        List<Trader> traders = Lists.newArrayList(traderDao.findAllById(Arrays.asList(savedTrader.getId(), 1)));

        assertEquals(2, traders.size());
        assertEquals(savedTrader.getCountry(), traders.get(0).getCountry());
    }
}