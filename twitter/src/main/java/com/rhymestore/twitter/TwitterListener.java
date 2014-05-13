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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.TwitterException;

import com.rhymestore.config.RhymeModule;
import com.rhymestore.config.RhymeStore;
import com.rhymestore.twitter.config.TwitterConfig;
import com.rhymestore.twitter.config.TwitterModule;

/**
 * Main Twitter listener process.
 * 
 * @author Ignasi Barrera
 */
public class TwitterListener
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterListener.class);

    /**
     * Start listening to tweets.
     */
    public void start() throws IllegalStateException, TwitterException
    {
        RhymeStore.create(new RhymeModule(), new TwitterModule());

        LOGGER.info("Connected to Twitter as: {}", TwitterConfig.getTwitterUser().get());
        LOGGER.info("Starting the Twitter stream listener");

        TwitterConfig.getTwitterStream().user(); // Start reading to user stream
    }

    /**
     * Shutdown hook to close the connection to Twitter.
     */
    private static class TwitterShutdown extends Thread
    {
        @Override
        public void run()
        {
            LOGGER.info("Disconnecting from the Twitter streaming API");
            TwitterConfig.getTwitterStream().shutdown();
        }
    }

    public static void main(final String[] args) throws Exception
    {
        // Start the Twitter listener
        TwitterListener listener = new TwitterListener();
        listener.start();

        // Register the shutdown hook to close the connection properly
        Runtime.getRuntime().addShutdownHook(new TwitterShutdown());
    }
}
