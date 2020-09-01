package ca.jrvs.apps.trading.service;

import ca.jrvs.apps.trading.dao.MarketDataDao;
import ca.jrvs.apps.trading.dao.QuoteDao;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class QuoteService {
    private static final Logger logger = LoggerFactory.getLogger(QuoteService.class);

    private QuoteDao quoteDao;
    private MarketDataDao marketDataDao;

    @Autowired
    public QuoteService(QuoteDao quoteDao, MarketDataDao marketDataDao) {
        this.quoteDao = quoteDao;
        this.marketDataDao = marketDataDao;
    }

    /**
     * Update quote table against IEX source
     * - get all quotes from the db
     * - foreach ticker get IEXQuote
     * - conver iexquote to quote entity
     * persist quote to db
     *
     * @throws ca.jrvs.apps.trading.dao.ResourceNotFoundException if ticker is not found from IEX
     * @throws org.springframework.dao.DataAccessException if unable to retrieve data
     * @throws IllegalArgumentException for invalid input
     */
    public void updateMarketData() {
        List<Quote> quoteList = quoteDao.findAll();
        quoteList.forEach(q -> {
            IexQuote iexQuote = marketDataDao.findById(q.getTicker()).get();
            Quote quote = buildQuoteFromIexQuote(iexQuote);
            quoteDao.save(quote);
        });
    }

    protected static Quote buildQuoteFromIexQuote(IexQuote iexQuote){
        Quote quote = new Quote();
        quote.setTicker(iexQuote.getSymbol());
        quote.setAskSize((iexQuote.getIexAskSize() != null) ? iexQuote.getIexAskSize().intValue() : -1);
        quote.setAskPrice((iexQuote.getIexAskPrice() != null) ? iexQuote.getIexAskPrice() : -1);
        quote.setLastPrice((iexQuote.getLatestPrice() != null) ? iexQuote.getLatestPrice() : -1);
        quote.setBidPrice((iexQuote.getIexBidPrice() != null) ? iexQuote.getIexBidPrice() : -1);
        quote.setBidSize((iexQuote.getIexBidSize()!= null) ? (int) iexQuote.getIexBidSize().intValue() : -1);
        return quote;
    }

    public List<Quote> saveQuotes(List<String> tickers) {
        List<Quote> quoteList = new ArrayList<>();

        for(String ticker : tickers) {
            try {
                quoteList.add(saveQuote(ticker));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("ERROR: The ticker is invalid");
            }
        }
        return quoteList;
    }

    public Quote saveQuote(String ticker) {
        IexQuote iexQuote = findIexQuoteByTicker(ticker);
        Quote quote = buildQuoteFromIexQuote(iexQuote);
        return saveQuote(quote);
    }

    public Quote saveQuote(Quote quote) {
        return quoteDao.save(quote);
    }

    public IexQuote findIexQuoteByTicker(String ticker) {
        return marketDataDao.findById(ticker).orElseThrow(() -> new IllegalArgumentException(ticker + " is invalid."));
    }

    public List<Quote> findAllQuotes() {
        return quoteDao.findAll();
    }
}
