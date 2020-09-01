package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {

    @Mock
    CrdDao dao;

    @InjectMocks
    TwitterService service;

    @Test
    public void postTweet() {
        when(dao.create(any())).thenReturn(new Tweet());
        service.postTweet(TweetUtil.buildTweet("test", 50.0, 0.0));
    }

    @Test
    public void showTweet() {
        when(dao.findById(any())).thenReturn(new Tweet());
        String[] fields = { "id", "id_str", "text" };
        assertNotNull(service.showTweet("13579", fields));
    }

    @Test
    public void deleteTweets() {
        when(dao.deleteById(any())).thenReturn(new Tweet());
        String[] ids = { "1", "2", "3" };
        List<Tweet> idList = service.deleteTweets(ids);
        assertNotNull(idList);
    }
}