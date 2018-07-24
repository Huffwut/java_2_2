package com.company;

import net.aksingh.owmjapis.api.APIException;
import net.aksingh.owmjapis.model.CurrentWeather;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class TweetWeather implements LogIn {
    private Twitter twitter = new TwitterFactory().getInstance();
    public void tweetWeathe(String name, String City) throws TwitterException, APIException {
        try {
            CurrentWeather cwd = owm.currentWeatherByCityName(City); //try and catch
            twitter.updateStatus("@" + name + " " + (cwd.getMainData().getTempMax().intValue()-273) +" in " + City + " [" + Main.tweetCount++ +"]");

        }
        catch (Exception iox){
            twitter.updateStatus("@" + name + " Something went wrong. Maybe the city name is incorrect?" + " [" + Main.tweetCount++ +"]");
            throw iox;
            }
    }
}
