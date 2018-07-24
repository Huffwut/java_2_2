package com.company;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Tweet {
    private Twitter twitter = new TwitterFactory().getInstance();

    public void tweetonTwitter(String msg) throws TwitterException {
        twitter.updateStatus(msg);
    }
}
