package Reddit;

import java.util.ArrayList;
import java.util.List;
import Objects.Post;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by angelachang on 9/17/16.
 */
public class PostsList {
    /**
     * We will be fetching JSON data from the API.
     */
    private final String URL_TEMPLATE=
            "http://www.reddit.com/r/SUBREDDIT_NAME/"
                    +".json"
                    +"?after=AFTER";

    String subreddit;
    String url;
    String after;

    PostsList(String sr){
        subreddit=sr;
        after="";
        generateURL();
    }

    /**
     * Generates the actual URL from the template based on the
     * subreddit name and the 'after' property.
     */
    private void generateURL(){
        url=URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
        url=url.replace("AFTER", after);
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */
    List<Post> fetchPosts(){
        String raw=RemoteData.readContents(url);
        List<Post> list = new ArrayList<Post>();
        try{
            JSONObject data=new JSONObject(raw)
                    .getJSONObject("data");
            JSONArray children=data.getJSONArray("children");

            //Using this property we can fetch the next set of
            //posts from the same subreddit
            after=data.getString("after");

            for(int i=0;i<children.length();i++){
                JSONObject cur=children.getJSONObject(i)
                        .getJSONObject("data");

                Post post = new Post(cur.optString("title"),cur.optString("url"),cur.optInt("score"));

                // some other properties

                /* post.title=cur.optString("title");
                post.url=cur.optString("url");
                post.numComments=cur.optInt("num_comments");
                post.points=cur.optInt("score");
                post.author=cur.optString("author");
                post.subreddit=cur.optString("subreddit");
                post.permalink=cur.optString("permalink");
                post.domain=cur.optString("domain");
                post.id=cur.optString("id"); */

                if(post.getTitle()!=null)
                    list.add(post);
            }
        }catch(Exception e){

        }
        return list;
    }

    /**
     * This is to fetch the next set of posts
     * using the 'after' property
     * @return
     */
    List<Post> fetchMorePosts(){
        generateURL();
        return fetchPosts();
    }
}
