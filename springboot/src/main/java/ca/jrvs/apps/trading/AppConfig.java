package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.model.config.MarketDataConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class AppConfig {

    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    @Bean
    public MarketDataConfig marketDataConfig() {
        MarketDataConfig marketDataConfig = new MarketDataConfig();
        marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
        marketDataConfig.setToken(System.getenv("IEX_PUB_TOKEN"));
        return marketDataConfig;
    }

    @Bean
    public HttpClientConnectionManager httpClientConnectionManager() {
        return new PoolingHttpClientConnectionManager();
    }

    @Bean
    public DataSource dataSource() {
        String url = System.getenv("PSQL_URL");
        String user = System.getenv("PSQL_USER");
        String password = System.getenv("PSQL_PASSWORD");
        //Never log your credentials/secrets. Use debugger instead
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(url);
        basicDataSource.setUsername(user);
        basicDataSource.setPassword(password);
        return basicDataSource;
    }
}
