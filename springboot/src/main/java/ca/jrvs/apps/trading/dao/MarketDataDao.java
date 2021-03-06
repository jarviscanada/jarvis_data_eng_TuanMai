package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class MarketDataDao implements CrudRepository<IexQuote, String> {

    private static final String IEX_BATCH_PATH = "/stock/market/batch?symbols=%s&types=quote&token=";
    private final String IEX_BATCH_URL;

    private final Logger logger = LoggerFactory.getLogger(MarketDataDao.class);
    private final HttpClientConnectionManager httpClientConnectionManager;

    @Autowired
    public MarketDataDao(HttpClientConnectionManager httpClientConnectionManager,
                         MarketDataConfig marketDataConfig) {
        this.httpClientConnectionManager = httpClientConnectionManager;
        IEX_BATCH_URL = marketDataConfig.getHost() + IEX_BATCH_PATH + marketDataConfig.getToken();
    }

    @Override
    public Optional<IexQuote> findById(String ticker) {
        Optional<IexQuote> iexQuote;
        List<IexQuote> quotes = findAllById(Collections.singletonList(ticker));

        if (quotes.size() == 0) {
            return Optional.empty();
        } else if (quotes.size() == 1) {
            iexQuote = Optional.of(quotes.get(0));
        } else {
            throw new DataRetrievalFailureException("ERROR: Unexpected number of quotes");
        }
        return iexQuote;
    }

    @Override
    public List<IexQuote> findAllById(Iterable<String> tickers) {
        if (tickers == null) {
            throw new IllegalArgumentException("ERROR: Tickers does not contain anything.");
        }

        String url = String.format(IEX_BATCH_URL, String.join(",", tickers));
        Optional<String> response = executeHttpGet(url);

        List<IexQuote> quotes = new ArrayList<>();
        JSONObject jsonObj = null;
        jsonObj = new JSONObject(response.get());

        for (String ticker : tickers) {
            if (!jsonObj.has(ticker)) {
                throw new IllegalArgumentException("ERROR: Ticker is invalid.");
            }
            try {
                quotes.add(JsonUtil.toObjectFromJson(jsonObj.getJSONObject(ticker).getJSONObject("quote").toString(), IexQuote.class));
            } catch (IOException ex) {
                logger.error("ERROR: Could not convert JSON to object", ex);
            }
        }
        return quotes;
    }

    private Optional<String> executeHttpGet(String url) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpUriRequest getRequest = new HttpGet(url);

        try {
            HttpResponse response = httpClient.execute(getRequest);
            if (response.getStatusLine().getStatusCode() == 404) {
                return Optional.empty();
            } else if (response.getStatusLine().getStatusCode() == 200) {
                return Optional.of(EntityUtils.toString(response.getEntity()));
            } else {
                throw new DataRetrievalFailureException("ERROR: Unexpected response code.");
            }
        } catch (IOException ex) {
            throw new DataRetrievalFailureException("ERROR: HTTP request failed.");
        }
    }

    private CloseableHttpClient getHttpClient() {
        return HttpClients.custom()
                .setConnectionManager(httpClientConnectionManager)
                .setConnectionManagerShared(true)
                .build();
    }

    @Override
    public boolean existsById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Iterable<IexQuote> findAll() {
        throw new UnsupportedOperationException("Not implemented");
    }


    @Override
    public long count() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteById(String s) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void delete(IexQuote entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll(Iterable<? extends IexQuote> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> S save(S entity) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public <S extends IexQuote> Iterable<S> saveAll(Iterable<S> entities) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
