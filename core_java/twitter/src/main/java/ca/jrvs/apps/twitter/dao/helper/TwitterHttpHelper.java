package ca.jrvs.apps.twitter.dao.helper;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import javax.validation.groups.Default;
import java.io.IOException;
import java.net.URI;

public class TwitterHttpHelper implements HttpHelper {

    static final Logger logger = LoggerFactory.getLogger(TwitterHttpHelper.class);

    private OAuthConsumer consumer;
    private HttpClient httpClient;

    public TwitterHttpHelper (String consumerKey, String consumerSecret, String accessToken, String tokenSecret) {
        consumer = new CommonsHttpOAuthConsumer(consumerKey, consumerSecret);
        consumer.setTokenWithSecret(accessToken, tokenSecret);
        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * Execute a HTTP Post call
     *
     * @param uri
     * @return
     */
    @Override
    public HttpResponse httpPost(URI uri) {

        try {
            return executeHttpRequest(HttpMethod.POST, uri, null);
        } catch (OAuthException | IOException ex) {
            throw new RuntimeException("ERROR: Failed to execute POST operations.", ex);
        }
    }

    /**
     * Execute a HTTP Get call
     *
     * @param uri
     * @return
     */
    @Override
    public HttpResponse httpGet(URI uri) {

        try {
            return executeHttpRequest(HttpMethod.GET, uri, null);
        } catch (OAuthException | IOException ex) {
            throw new RuntimeException("ERROR: Failed to execute GET operations.", ex);
        }
    }

    private HttpResponse executeHttpRequest(HttpMethod method, URI uri, StringEntity entity) throws OAuthException, IOException {

        if (method == HttpMethod.GET) {
            HttpGet request = new HttpGet(uri);
            consumer.sign(request);
            return httpClient.execute(request);
        }
        else if (method == HttpMethod.POST) {
            HttpPost request = new HttpPost(uri);
            if (entity != null) {
                request.setEntity(entity);
            }
            consumer.sign(request);
            return httpClient.execute(request);
        } else {
            throw new IllegalArgumentException("Unknown HTTP method: " + method.name());
        }

    }

    public static void main(String[] args) throws Exception{

        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.debug(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        HttpResponse response = httpHelper.httpPost(new URI("https://api.twitter.com/1.1/statuses/update.json?status=HelloWorld!"));
        logger.debug(EntityUtils.toString(response.getEntity()));
    }
}
