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

package com.rhymestore.twitter.config;

import javax.inject.Singleton;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.rhymestore.twitter.stream.GetMentionsListener;

/**
 * Configures the Twitter access.
 * 
 * @author Ignasi Barrera
 */
public class TwitterModule extends AbstractModule
{
    /** The Twitter user. */
    public static final String TWITTER_USER = "rimamelo";

    @Override
    protected void configure()
    {
        System.setProperty("twitter4j.oauth.consumerKey", System.getenv("TWITTER_CONSUMERKEY"));
        System.setProperty("twitter4j.oauth.consumerSecret",
            System.getenv("TWITTER_CONSUMERSECRET"));
        System.setProperty("twitter4j.oauth.accessToken", System.getenv("TWITTER_ACCESSTOKEN"));
        System.setProperty("twitter4j.oauth.accessTokenSecret",
            System.getenv("TWITTER_ACCESSTOKENSECRET"));
    }

    @Provides
    @Singleton
    public Twitter provideTwitter()
    {
        return new TwitterFactory().getInstance();
    }

    @Provides
    @Singleton
    public TwitterStream provideTwitterStream(final GetMentionsListener mentionListener)
    {
        TwitterStream stream = new TwitterStreamFactory().getInstance();
        stream.addListener(mentionListener);
        return stream;
    }

}
