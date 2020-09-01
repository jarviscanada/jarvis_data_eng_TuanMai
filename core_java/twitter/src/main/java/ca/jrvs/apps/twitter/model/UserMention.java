package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "id_str",
        "indices",
        "name",
        "screen_name"
})
public class UserMention {

    @JsonProperty("id")
    private long id;
    @JsonProperty("id_str")
    private String idStr;
    @JsonProperty("indices")
    private int[] indices;
    @JsonProperty("name")
    private String name;
    @JsonProperty("screen_name")
    private String screenName;

    @JsonProperty("id")
    public long getId() { return id; }
    @JsonProperty("id")
    public void setId(long id) { this.id = id; }

    @JsonProperty("id_str")
    public String getIdStr() { return idStr; }
    @JsonProperty("id_str")
    public void setIdStr(String str) { idStr = str; }

    @JsonProperty("indices")
    public int[] getIndices() { return indices; }
    @JsonProperty("indices")
    public void setIndices(int[] indices) { this.indices = indices; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String str) { name = str; }

    @JsonProperty("screen_name")
    public String getScreenName() { return screenName; }
    @JsonProperty("screen_name")
    public void setScreenName(String str) { screenName = str; }
}
