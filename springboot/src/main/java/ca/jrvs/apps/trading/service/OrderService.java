package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.AccountDao;
import ca.jrvs.apps.trading.dao.PositionDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.dao.SecurityOrderDao;
import ca.jrvs.apps.trading.dto.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.Account;
import ca.jrvs.apps.trading.model.domain.Position;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private AccountDao accountDao;
    private SecurityOrderDao securityOrderDao;
    private QuoteDao quoteDao;
    private PositionDao positionDao;

    public OrderService(AccountDao accountDao, SecurityOrderDao securityOrderDao, QuoteDao quoteDao, PositionDao positionDao) {
        this.accountDao = accountDao;
        this.securityOrderDao = securityOrderDao;
        this.quoteDao = quoteDao;
        this.positionDao = positionDao;
    }

    /**
     * Execute a market order
     *
     * - validate the order (e.g. size, and ticker)
     * - Create a securityOrder (for security_order table)
     * - Handle buy or sell order
     *    - buy order : check account balance (calls helper method)
     *    - sell order : check position for the ticker / symbol (calls helper method)
     *    - (please dont' forget to update securityOrder.status)
     * - Save and return securtiyOrder
     *
     * NOTE: you will need to make some helper methods (protected or private)
     *
     * @param orderDto market order
     * @return SecurityOrder from security_order table
     * @throws org.springframework.dao.DataAccessException if unable to get data from DAO
     * @throws IllegalArgumentException for invalid inputs
     */
    public SecurityOrder executeMarketOrder(MarketOrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("ERROR: The order cannot be null");
        }

        Account account;
        try {
            account = accountDao.findById(orderDto.getAccountId()).get();
        } catch (Exception ex) {
            throw new IllegalArgumentException("ERROR: The account could not be retrieved.");
        }

        SecurityOrder securityOrder = new SecurityOrder();
        securityOrder.setAccountId(orderDto.getAccountId());
        securityOrder.setTicker(orderDto.getTicker());
        securityOrder.setStatus("PENDING");
        securityOrder.setNotes("Executing a market order");
        securityOrder.setSize(orderDto.getSize());
        securityOrder.setPrice(0.0);

        if (orderDto.getSize() > 0) {
            handleBuyMarketOrder(orderDto, securityOrder, account);
        } else if (orderDto.getSize() < 0) {
            handleSellMarketOrder(orderDto, securityOrder, account);
        } else {
            throw new IllegalArgumentException("ERROR: Invalid order size");
        }

        return securityOrder;
    }

    /**
     * Helper method that execute a buy order
     * @param marketOrderDto user order
     * @param securityOrder to be saved in data database
     * @param account account
     */
    protected void handleBuyMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {

        if (account.getAmount() >= securityOrder.getPrice() * marketOrderDto.getSize()) {
            securityOrder.setStatus("FILLED");
        } else {
            securityOrder.setStatus("CANCELLED");
        }

        securityOrderDao.save(securityOrder);
    }

    /**
     * Helper method that execute a sell order
     * @param marketOrderDto user order
     * @param securityOrder to be saved in data database
     * @patam account account
     */
    protected void handleSellMarketOrder(MarketOrderDto marketOrderDto, SecurityOrder securityOrder, Account account) {

        List<Position> positionList = positionDao.getPosition(account.getId());

        for (Position position : positionList) {
            if (position.getTicker() == marketOrderDto.getTicker() && position.getPosition() >= Math.abs(marketOrderDto.getSize())) {
                securityOrder.setStatus("FILLED");
            } else {
                securityOrder.setStatus("CANCELLED");
            }
        }
        securityOrderDao.save(securityOrder);
    }

}
