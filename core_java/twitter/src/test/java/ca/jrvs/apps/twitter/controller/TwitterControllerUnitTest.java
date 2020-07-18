package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {

    @Mock
    TwitterService twitterService;

    @InjectMocks
    TwitterController twitterController;

    @Test
    public void postTweet() {
        when(twitterService.postTweet(any())).thenReturn(new Tweet());
        Tweet postTweet = twitterController.postTweet(new String[] { "post", "JUnit postTweet Test", "-1.0:1.0"});
        assertNotNull(postTweet);
    }

    @Test
    public void showTweet() {
        when(twitterService.showTweet(anyString(), any())).thenReturn(new Tweet());
        Tweet showTweet = twitterController.showTweet(new String[] { "show", "123"});
        assertNotNull(showTweet);
    }

    @Test
    public void deleteTweet() {
        when(twitterService.deleteTweets(any())).thenReturn(Arrays.asList(new Tweet()));
        List<Tweet> deleteTweet = twitterController.deleteTweet(new String[] { "delete", "123"});
        assertNotNull(deleteTweet);
    }
}