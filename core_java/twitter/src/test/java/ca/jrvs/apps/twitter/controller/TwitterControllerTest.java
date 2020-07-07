package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterDaoTest;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerTest {
    static final Logger logger = LoggerFactory.getLogger(TwitterControllerTest.class);

    TwitterService twitterService;
    TwitterController twitterController;

    @Before
    public void setup() {
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");

        logger.debug(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);
        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao dao = new TwitterDao(httpHelper);
        twitterService = new TwitterService(dao);
        twitterController = new TwitterController(twitterService);
    }

    @Test
    public void postTweet() {
        String[] tweetBuilder = { "post", "TwitterController postTweet test", "-1.0:1.0" };
        twitterController.postTweet(tweetBuilder);
    }

    @Test
    public void showTweet() {
        String[] tweetBuilder = { "post", "TwitterController showTweet test tweet", "-1.0:1.0" };
        Tweet showTweet = twitterController.postTweet(tweetBuilder);
        String tweetId = showTweet.getIdStr();

        String[] showTweetBuilder = { "show", tweetId };
        twitterController.showTweet(showTweetBuilder);

    }

    @Test
    public void deleteTweet() {
        List<String> idList = new ArrayList<>();

        for (int idx = 0; idx < 5; idx++){
            String[] tweetBuilder = { "post", "TwitterController deleteTweet test tweet " + (idx + 1), "-1.0:1.0" };
            Tweet tempTweet = twitterController.postTweet(tweetBuilder);
            String tweetId = tempTweet.getIdStr();
            idList.add(tweetId);
        }

        String[] deleteTweetsBuilder = { "delete" , String.join(",", idList)};
        twitterController.deleteTweet(deleteTweetsBuilder);
    }
}