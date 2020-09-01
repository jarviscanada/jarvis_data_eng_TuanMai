package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dto.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.annotation.Order;
import org.yaml.snakeyaml.error.Mark;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceUnitTest {

    // Capture parameter when calling securityOrderDao.save
    @Captor
    ArgumentCaptor<SecurityOrder> captorSecurityOrder;

    // Mock all dependencies
    @Mock
    private AccountDao accountDao;

    @Mock
    private SecurityOrderDao securityOrderDao;

    @Mock
    private QuoteDao quoteDao;

    @Mock
    private PositionDao positionDao;

    // Injecting mocked dependencies to the testing class via constructor
    @InjectMocks
    private OrderService orderService;

    @Test
    public void executeMarketOrder() {

        Quote quote = new Quote();
        quote.setAskPrice(10d);
        quote.setAskSize(10);
        quote.setBidPrice(10.2d);
        quote.setBidSize(10);
        quote.setId("AAPL");
        quote.setLastPrice(10.1d);

        Account account = new Account();
        account.setId(1);
        account.setAmount(1000.0);
        when(accountDao.findById(anyInt())).thenReturn(Optional.of(account));

        Position position = new Position();
        position.setAccountId(account.getId());
        position.setPosition(10);

        MarketOrderDto marketOrderDto = new MarketOrderDto();
        marketOrderDto.setAccountId(account.getId());
        marketOrderDto.setTicker("AAPL");
        marketOrderDto.setSize(10);
        SecurityOrder order = orderService.executeMarketOrder(marketOrderDto);
        assertEquals("FILLED", order.getStatus());

    }
}