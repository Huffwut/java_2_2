package com.company;


import net.aksingh.owmjapis.api.APIException;
import twitter4j.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadNotification implements Runnable {
    private TwitterFactory factory = new TwitterFactory();
    private Twitter twitter = factory.getInstance();
    private int totalCount = 20;
    private Paging paging = new Paging(1, totalCount);
    private List<Status> tweets;
    private List<Status> ownTweet;
    private FLineReader reader;
    private WriteInterface writ;
    private TweetWeather tweetWeather = new TweetWeather();
    private ArrayList<String> twat;

    public ReadNotification(WriteInterface writ, FLineReader reader){
        this.reader = reader;
        this.writ = writ;

    }
        @Override
    public void run(){
        /*
        * I need to get the mentions list every time, so i know if ooi got any new mentions
        * */
            Pattern pattern = Pattern.compile("[#]\\w+");
            Matcher matcher = pattern.matcher("");

            System.out.println("yoopeee");
        try {
            tweets = twitter.getMentionsTimeline(paging);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        /*
        * checks wherever reader has next line or it contains the tweet id
        * if it doesn't contain tweet id, you can tweet to it and add the id to the file
        * */
        for (Status status : tweets) {
            String id = Objects.toString(status.getId(), null);
            if((!reader.hasNextLine()) || (!reader.getNextLine().contains(id))) { //check if it contains the ID, possibly with while reader has nextline
                //reader.close();
                if (status.getText().contains("weather")) {
                    matcher.reset(status.getText());
                    while(matcher.find()) {
                        try {
                            if (status.getText().contains(pattern.pattern())) ;
                            {
                                String statf = matcher.group();
                                String[] dick = statf.split("[#]");
                                System.out.println(dick[1]);
                                try {
                                    tweetWeather.tweetWeathe(status.getUser().getScreenName(), dick[1]);
                                } catch (APIException e) {
                                    e.printStackTrace();
                                }
                            }

                        } catch (TwitterException e) {
                            e.printStackTrace();
                        }
                    }
                }
                try {
                    writ.writeID(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
            reader.close();
            writ.close();
    }

}
