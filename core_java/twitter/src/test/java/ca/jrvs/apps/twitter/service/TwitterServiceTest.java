package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterDaoTest;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceTest {

    static final Logger logger = LoggerFactory.getLogger(TwitterDaoTest.class);

    TwitterService twitterService;
    Tweet setupTweet;

    @Before
    public void setUp() throws Exception {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.debug(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        CrdDao dao = new TwitterDao(httpHelper);
        twitterService = new TwitterService(dao);

        String hashTag = "#tweet";
        String text = "Setup " + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet tempTweet = TweetUtil.buildTweet(text, lat, lon);
        setupTweet = twitterService.postTweet(tempTweet);
    }

    @After
    public void tearDown() throws Exception {
        try {
            twitterService.deleteTweets(new String[] { setupTweet.getIdStr() });
        } catch (RuntimeException ex) {
            logger.debug("This Tweet does not exist.");
        }
    }

    @Test
    public void postTweet() {
        String hashTag = "#postTweet";
        String text = "Testing the: " + hashTag + " method. " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        twitterService.postTweet(TweetUtil.buildTweet(text, lat, lon));

    }

    @Test
    public void showTweet() {
        String hashTag = "#showTweet";
        String text = "Testing the: " + hashTag + " method. " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        twitterService.postTweet(TweetUtil.buildTweet(text, lat, lon));

        String[] fields = { "id", "id_str", "text"};
        Tweet getTweet = twitterService.showTweet(setupTweet.getIdStr(), fields);
        logger.debug(getTweet.toString());
    }

    @Test
    public void deleteTweets() {
        List<String> idList = new ArrayList<String>();

        for (int idx = 0; idx < 5; idx++) {
            String hashTag = "#showTweet";
            String text = (idx+1) + "Testing the: " + hashTag + " method. " + System.currentTimeMillis();
            Double lat = 1d;
            Double lon = -1d;
            Tweet tweet = twitterService.postTweet(TweetUtil.buildTweet(text, lat, lon));
            idList.add(tweet.getIdStr());
        }

        String[] ids = new String[idList.size()];
        idList.toArray(ids);
        try {
            twitterService.deleteTweets(ids);
        } catch (RuntimeException ex) {
            logger.debug("This Tweet does not exist. deleteTweets method.");
        }
    }
}