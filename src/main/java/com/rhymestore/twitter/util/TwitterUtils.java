package com.rhymestore.twitter.util;

import twitter4j.TwitterException;
import twitter4j.internal.http.HttpResponseCode;

/**
 * Utility methods to work with the Twitter API.
 * 
 * @author Ignasi Barrera
 */
public class TwitterUtils
{
    /** Maximum length for the tweets. */
    public static final int MAX_TWEET_LENGTH = 140;

    /** Maximum number of API calls that can be performed in an hour. */
    public static final int MAX_API_CALLS_PER_HOUR = 150;

    /** The error produced when attempting to send duplicate tweets. */
    public static final String DUPLICATE_TWEET_ERROR = "Status is a duplicate.";

    /**
     * Returns a valid tweet for the given sentence.
     * 
     * @param sentence The sentence to tweet.
     * @return The valid tweet.
     */
    public static String toTweet(final String sentence)
    {
        return sentence.length() > MAX_TWEET_LENGTH ? sentence.substring(0, MAX_TWEET_LENGTH)
            : sentence;
    }

    /**
     * Builds a reply to the given user.
     * 
     * @param user The user to reply.
     * @param tweet The tweet to send.
     * @return The reply tweet.
     */
    public static String reply(final String user, final String tweet)
    {
        return toTweet("@" + user + " " + tweet);
    }

    /**
     * Checks if the exception cause is a duplicate tweet.
     * 
     * @param ex The exception to check.
     * @return Boolean indicating if the exception cause is a duplicate tweet.
     */
    public static boolean isDuplicateTweetError(final TwitterException ex)
    {
        return ex.getStatusCode() == HttpResponseCode.FORBIDDEN
            && ex.getMessage().contains(DUPLICATE_TWEET_ERROR);
    }
}
