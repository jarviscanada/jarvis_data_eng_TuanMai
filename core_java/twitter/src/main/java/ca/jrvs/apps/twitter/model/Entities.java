package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.catalina.User;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "hashtags",
        "user_mentions"
})

public class Entities {
    @JsonProperty("hashtags")
    private List<Hashtag> hashtags;
    @JsonProperty("user_mentions")
    private List<UserMention> userMentions;

    @JsonProperty("hashtags")
    public List<Hashtag> getHashtag() { return hashtags; }

    @JsonProperty("hashtags")
    public void setHashtag(List<Hashtag> hashtag) { hashtags = hashtag; }

    @JsonProperty("user_mentions")
    public List<UserMention> getUserMentions() { return userMentions; }

    @JsonProperty("user_mentions")
    public void setUserMentions(List<UserMention> userMention) { userMentions = userMention; }
}
