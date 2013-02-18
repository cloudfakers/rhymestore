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

package com.rhymestore.twitter;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamAdapter;

/**
 * Twitter API client integration tests.
 * 
 * @author Ignasi Barrera
 */
public class TwitterIT
{
    /** The twitter user name. */
    private static final String TWITTER_USER_NAME = "rimamelo";

    /** The Twitter API client. */
    private Twitter twitter;

    @BeforeMethod
    public void setUp()
    {
        System.setProperty("twitter4j.oauth.consumerKey", "Ydm91SFxOTFeY3kz18xZmg");
        System.setProperty("twitter4j.oauth.consumerSecret",
            "DNDjhjNYj7L9ZFPwTp8NLpFOWdYZ6TlrMbhCHrh3Ac");
        System.setProperty("twitter4j.oauth.accessToken",
            "204948207-d5VzMLO1VX6vwNef7ysCsSaRgxXEYp3hLstE1Jo");
        System.setProperty("twitter4j.oauth.accessTokenSecret",
            "IqeT05mvaJpa1n2q8ctkbNpRBrE6KLaVrvJf7T6cQ");

        twitter = new TwitterFactory().getInstance();
    }

    @AfterMethod
    public void tearDown()
    {
        twitter.shutdown();
    }

    @Test
    public void testTwitterConnect() throws Exception
    {
        User user = twitter.verifyCredentials();
        Assert.assertEquals(TWITTER_USER_NAME, user.getScreenName());
    }

    public static void main(final String[] args)
    {
        System.setProperty("twitter4j.oauth.consumerKey", "Ydm91SFxOTFeY3kz18xZmg");
        System.setProperty("twitter4j.oauth.consumerSecret",
            "DNDjhjNYj7L9ZFPwTp8NLpFOWdYZ6TlrMbhCHrh3Ac");
        System.setProperty("twitter4j.oauth.accessToken",
            "204948207-d5VzMLO1VX6vwNef7ysCsSaRgxXEYp3hLstE1Jo");
        System.setProperty("twitter4j.oauth.accessTokenSecret",
            "IqeT05mvaJpa1n2q8ctkbNpRBrE6KLaVrvJf7T6cQ");

        final TwitterStream stream = new TwitterStreamFactory().getInstance();

        UserStreamAdapter listener = new UserStreamAdapter()
        {
            @Override
            public void onStatus(final Status status)
            {
                UserMentionEntity[] mentions = status.getUserMentionEntities();
                System.out.println(status.getText());
                if (mentions != null)
                {
                    for (UserMentionEntity mention : mentions)
                    {
                        System.out.println("  " + mention.getScreenName());
                        stream.shutdown();
                    }
                }
            }

        };

        stream.addListener(listener);
        stream.sample();
    }
}
