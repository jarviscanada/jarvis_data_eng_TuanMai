package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelperTest;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import net.minidev.json.JSONUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class TwitterDaoTest {

    static final Logger logger = LoggerFactory.getLogger(TwitterDaoTest.class);

    TwitterDao dao;
    Tweet setupTweet;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.debug(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        dao = new TwitterDao(httpHelper);

        String hashTag = "#tweet";
        String text = "Setup " + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        setupTweet = dao.create(TweetUtil.buildTweet(text, lat, lon));
    }

    @After
    public void tearDown() throws Exception {
        try {
            dao.deleteById(setupTweet.getIdStr());
        } catch (RuntimeException ex) {
            logger.debug("There is no Tweet with that ID.");
        }
    }

    @Test
    public void create() {
        String hashTag = "#create";
        String text = "Testing the: " + hashTag + " method. " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lat, lon);

        Tweet tweet = dao.create(postTweet);
        assertEquals(text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));

        assertTrue(hashTag.contains(tweet.getEntities().getHashtag().get(0).getText()));
    }

    @Test
    public void findById() {
        Tweet getTweet = dao.findById(setupTweet.getIdStr());

        assertEquals(setupTweet.getIdStr(), getTweet.getIdStr());
    }

    @Test
    public void deleteById() {
        Tweet tweetToDelete = dao.deleteById(setupTweet.getIdStr());

        assertEquals(setupTweet.getIdStr(), tweetToDelete.getIdStr());

        try {
            dao.deleteById(setupTweet.getIdStr());
        } catch (RuntimeException ex) {
            logger.debug("There is no Tweet with that ID. deleteByID method");
        }
    }
}