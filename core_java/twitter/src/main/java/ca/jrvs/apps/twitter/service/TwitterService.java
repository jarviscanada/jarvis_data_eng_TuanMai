package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service {

    private CrdDao dao;

    @Autowired
    public TwitterService(CrdDao dao) {
        this.dao = dao;
    }

    /**
     * Validate and post a user input Tweet
     *
     * @param tweet tweet to be created
     * @return created tweet
     * @throws IllegalArgumentException if text exceed max number of allowed characters or lat/long out of range
     */
    @Override
    public Tweet postTweet(Tweet tweet) {
        validatePostTweet(tweet);

        return (Tweet) dao.create(tweet);
    }

    private void validatePostTweet(Tweet tweet) {
        if (tweet.getText().length() > 140){
            throw new IllegalArgumentException("The tweet cannot exceed 140 characters.");
        } else if (tweet.getCoordinates().getCoordinates().get(0) < -180 || tweet.getCoordinates().getCoordinates().get(0) > 180) {
            throw new IllegalArgumentException("The valid ranges for longitude are -180.0 to +180.0 (East is positive) inclusive.");
        } else if (tweet.getCoordinates().getCoordinates().get(1) < -90 || tweet.getCoordinates().getCoordinates().get(1) > 90) {
            throw new IllegalArgumentException("The valid ranges for latitude are -90.0 to +90.0 (North is positive) inclusive.");
        }
    }

    /**
     * Search a tweet by ID
     *
     * @param id     tweet id
     * @param fields set fields not in the list to null
     * @return Tweet object which is returned by the Twitter API
     * @throws IllegalArgumentException if id or fields param is invalid
     */
    @Override
    public Tweet showTweet(String id, String[] fields) {
        validateId(id);
        return (Tweet) dao.findById(id);
    }

    private void validateId(String id) {
        try {
            Long.parseLong(id);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("The ID: " + id + " is invalid");
        }
    }

    /**
     * Delete Tweet(s) by id(s).
     *
     * @param ids tweet IDs which will be deleted
     * @return A list of Tweets
     * @throws IllegalArgumentException if one of the IDs is invalid.
     */
    @Override
    public List<Tweet> deleteTweets(String[] ids) {
        Arrays.stream(ids).forEach(id -> validateId(id));
        List<Tweet> deletedTweets = new ArrayList<>();
        Arrays.stream(ids).forEach(id -> deletedTweets.add((Tweet) dao.deleteById(id)));
        return deletedTweets;
    }
}
