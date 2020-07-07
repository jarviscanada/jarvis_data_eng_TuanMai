package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.springframework.util.StringUtils;

import java.util.List;

public class TwitterController implements Controller {

    private static final String COORD_SEP = ":";
    private static final String COMMA = ",";

    private Service service;

    public TwitterController(Service service) {
        this.service = service;
    }

    /**
     * Parse user argument and post a tweet by calling service classes
     *
     * @param args
     * @return a posted tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet postTweet(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("ERROR: This requires 3 inputs. Usage: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        String tweetText = args[1];
        String coord = args[2];
        String[] coordArray = coord.split(COORD_SEP);
        if (coordArray.length != 2 || StringUtils.isEmpty(tweetText)) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"");
        }

        Double lat = null;
        Double lon = null;

        try {
            lat = Double.parseDouble(coordArray[0]);
            lon = Double.parseDouble(coordArray[1]);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid location format\nUSAGE: TwitterCLIApp post \"tweet_text\" \"latitude:longitude\"", ex);
        }

        Tweet postTweet = TweetUtil.buildTweet(tweetText, lat, lon);
        return service.postTweet(postTweet);
    }

    /**
     * Parse user argument and search a tweet by calling service classes
     *
     * @param args
     * @return a tweet
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public Tweet showTweet(String[] args) {

        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp show tweet_id");
        }

        String tweetId = args[1];
        return service.showTweet(tweetId, new String[]{});
    }

    /**
     * Parse user argument and delete tweets by calling service classes
     *
     * @param args
     * @return a list of deleted tweets
     * @throws IllegalArgumentException if args are invalid
     */
    @Override
    public List<Tweet> deleteTweet(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("USAGE: TwitterCLIApp delete [id1,id2,..,idn]");
        }

        String ids = args[1];
        String[] idList = ids.split(COMMA);

        if (StringUtils.isEmpty(idList)){
            throw new IllegalArgumentException("Please provide the tweet id(s) to be deleted. USAGE: TwitterCLIApp delete [id1,id2,..,idn]");
        }

        return service.deleteTweets(idList);
    }
}
