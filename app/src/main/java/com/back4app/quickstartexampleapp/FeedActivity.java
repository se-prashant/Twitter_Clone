package com.back4app.quickstartexampleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setTitle("Your Feed");

        final ListView listView = (ListView) findViewById(R.id.listView);

        final  List<Map<String ,String>> tweetData = new ArrayList<>();

     /*   for(int i=1;i<=5;i++){
            Map<String,String> tweetInfo = new HashMap<>();
            tweetInfo.put("content","Tweet Content"+Integer.toString(i));
            tweetInfo.put("username","User"+Integer.toString(i));
            tweetData.add(tweetInfo);
        }*/
        ParseQuery<ParseObject> query = ParseQuery.getQuery("tweet");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject tweet:objects){
                        Map<String,String> tweetInfo = new HashMap<>();
                        tweetInfo.put("content",tweet.getString("tweet"));
                        tweetInfo.put("username",tweet.getString("username"));
                        tweetData.add(tweetInfo);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(FeedActivity.this,tweetData,android.R.layout.simple_list_item_2,new String[]{"content" ,"username"},new int[]{android.R.id.text1,android.R.id.text2});

                    listView.setAdapter(simpleAdapter);

                }
            }
        });

    }
}
