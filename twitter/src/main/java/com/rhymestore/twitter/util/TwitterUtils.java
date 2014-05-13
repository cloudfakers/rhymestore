/**
 * Copyright (c) 2010 Enric Ruiz, Ignasi Barrera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.rhymestore.twitter.util;

import twitter4j.HttpResponseCode;
import twitter4j.TwitterException;

/**
 * Utility methods to work with the Twitter API.
 * 
 * @author Ignasi Barrera
 */
public class TwitterUtils
{
    /** Maximum length for the tweets. */
    public static final int MAX_TWEET_LENGTH = 140;

    /** The rate limit time window in minutes. */
    public static final int RATE_LIMIT_WINDOW_MINUTES = 15;

    /** Number of APi calls allowed in the rate limit window. */
    public static final int RATE_LIMIT_API_CALLS_IN_WINDOW = 15;

    /** The error produced when attempting to send duplicate tweets. */
    public static final String DUPLICATE_TWEET_ERROR = "Status is a duplicate.";

    /**
     * Returns a valid tweet for the given sentence.
     * 
     * @param sentence The sentence to tweet.
     * @return The valid tweet.
     */
    public static String tweet(final String sentence)
    {
        return sentence.length() > MAX_TWEET_LENGTH ? sentence.substring(0, MAX_TWEET_LENGTH - 3)
            + "..." : sentence;
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
        return tweet("." + user(user) + " " + tweet);
    }

    /**
     * Builds Twitter user name.
     * 
     * @param user The user name.
     * @return The Twitter user name.
     */
    public static String user(final String user)
    {
        return user.startsWith("@") ? user : "@" + user;
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
