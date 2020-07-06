package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

public class TwitterDao implements CrdDao<Tweet, String> {

    static final Logger logger = LoggerFactory.getLogger(TwitterDao.class);

    // URI Constants
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    // URI Symbols
    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    // Response code
    private static final int HTTP_OK = 200;

    private HttpHelper httpHelper;

    public TwitterDao(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    /**
     * Create an entity(Tweet) to the underlying storage
     *
     * @param tweet tweet to be created
     * @return created entity
     */
    @Override
    public Tweet create(Tweet tweet) {

        URI uri;
        PercentEscaper percentEscaper = new PercentEscaper("", false);

        try {
            uri = new URI(API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL + percentEscaper.escape(tweet.getText()) + AMPERSAND + "long" + EQUAL + tweet.getCoordinates().getCoordinates().get(0) + AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(1));
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("ERROR: Could not create new URI.", ex);
        }

        HttpResponse response = httpHelper.httpPost(uri);
        return parseResponseBody(response, HTTP_OK);
    }

    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode) {
        Tweet tweet = null;

        // Check response status
        int status = response.getStatusLine().getStatusCode();
        if (status != expectedStatusCode) {
            try {
                logger.error(EntityUtils.toString(response.getEntity()));
            } catch (IOException ex) {
                logger.error("Response has no entity");
            }
            throw new RuntimeException("Unexpected HTTP status: " + status);
        }

        if (response.getEntity() == null) {
            throw new RuntimeException("Empty response body");
        }

        // Convert Response Entity to str
        String jsonStr;
        try {
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException ex) {
            throw new RuntimeException("Failed to convert entity to String", ex);
        }

        // Convert JSON string to Tweet object
        try {
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to convert JSON string to object", ex);
        }

        return tweet;
    }

    /**
     * Find an entity(Tweet) by its id
     *
     * @param entityId entity id
     * @return Tweet entity
     */
    @Override
    public Tweet findById(String entityId) {

        URI uri;

        try {
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id" + EQUAL + entityId);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("ERROR: Invalid entity id.", ex);
        }

        HttpResponse response = httpHelper.httpGet(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    /**
     * Delete an entity(Tweet) by its ID
     *
     * @param entityId of the entity to be deleted
     * @return deleted entity
     */
    @Override
    public Tweet deleteById(String entityId) {

        URI uri;

        try {
            uri = new URI(API_BASE_URI + DELETE_PATH + "/" + entityId + ".json");
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Invalid id input", ex);
        }

        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }
}
