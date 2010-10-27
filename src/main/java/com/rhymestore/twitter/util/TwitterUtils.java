/**
 * The Rhymestore project.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

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
    public static String tweet(final String sentence)
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
        return tweet(user(user) + " " + tweet);
    }

    /**
     * Builds Twitter user name.
     * 
     * @param user The user name.
     * @return The Twitter user name.
     */
    public static String user(final String user)
    {
        return "@" + user;
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
