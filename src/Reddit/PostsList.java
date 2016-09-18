package Reddit;

import java.util.ArrayList;
import java.util.List;
import Objects.Post;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by angelachang on 9/17/16.
 */
public class PostsList{
    /**
     * We will be fetching JSON data from the API.
     */

    private final String URL_TEMPLATE=
            "https://www.reddit.com/r/SUBREDDIT_NAME/top/"
                    +".json"
                    +"?after=AFTER";
    private int NUM_POSTS = 200;
    private int POSTS_PER_PAGE = 25;

    String subreddit;
    String url;
    String after;
    int end = 0;

    public PostsList(String sr){
        subreddit=sr;
        after="";
    }

    private void generateURL(){
    	if(end == 1){
    		return;
    	}
    	if (subreddit.equals("frontpage"))
    	{
    		String TEMPLATE = "https://www.reddit.com/top/"
                    +".json"
                    +"?after=AFTER";
    		//url = "https://www.reddit.com/top/"+".json"+"?after=AFTER";
    		url=TEMPLATE.replace("AFTER", after);
    	}
    	else
    	{
    		url=URL_TEMPLATE.replace("SUBREDDIT_NAME", subreddit);
    		url=url.replace("AFTER", after);
    	}
    }

    /**
     * Returns a list of Post objects after fetching data from
     * Reddit using the JSON API.
     *
     * @return
     */

    public ArrayList<Post> fetch(){
        ArrayList<Post> list = new ArrayList<Post>();

        for(int n = 0; n<NUM_POSTS/POSTS_PER_PAGE;++n) {
            generateURL();
            System.out.println(url);
            
            String raw = RemoteData.readContents(url);
            try {
                JSONObject data = new JSONObject(raw)
                        .getJSONObject("data");
                JSONArray children = data.getJSONArray("children");

                //Using this property we can fetch the next set of
                //posts from the same subreddit
                for (int i = 0; i < children.length(); i++) {
                    JSONObject current = children.getJSONObject(i)
                            .getJSONObject("data");
                    String title = current.optString("title");
                    title = title.replace(' ', '_');
                    
                    Post post = new Post(title, current.optString("url"), current.optInt("score"));

                    if (post.getTitle() != null)
                        list.add(post);
                }
                
                if(end == 1 || data.getString("after").equals("null")){
                }else{
                	after = data.getString("after");
                }
            } catch (Exception e) {
                System.out.printf("Subreddit does not exist.");
                url = url + "/";
                n--;
                end = 1;
                
            }
        }
        return list;
    }

}
