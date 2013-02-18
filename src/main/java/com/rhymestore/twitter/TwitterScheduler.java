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

import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.twitter.commands.TwitterCommand;
import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Schedules Twitter API calls to execute them in order.
 * <p>
 * This class will enqueue and run all requested API calls when possible, taking care of not passing
 * the Twitter API rate limit.
 * 
 * @author Ignasi Barrera
 * @see TwitterCommand
 */
public class TwitterScheduler implements Runnable
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterScheduler.class);

    /** The scheduler service. */
    private ScheduledExecutorService scheduler;

    /** The queue with the pending commands. */
    private Queue<TwitterCommand> commandQueue;

    /** The Twitter account where the API calls will be performed. */
    private Twitter twitter;

    /**
     * Creates a new {@link TwitterScheduler}.
     * 
     * @param twitter The Twitter account where the API calls will be performed.
     */
    public TwitterScheduler(final Twitter twitter)
    {
        super();

        this.twitter = twitter;
        commandQueue = new LinkedBlockingDeque<TwitterCommand>(); // Thread-safe
        scheduler = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Starts down the scheduler.
     */
    public void start()
    {
        double callsPerMinute =
            Math.floor(TwitterUtils.RATE_LIMIT_WINDOW_MINUTES
                / TwitterUtils.RATE_LIMIT_API_CALLS_IN_WINDOW);
        long interval = (long) Math.floor(60 / callsPerMinute);

        scheduler.scheduleAtFixedRate(this, 0, interval, TimeUnit.SECONDS);
    }

    /**
     * Shuts down the scheduler.
     */
    public void shutdown()
    {
        scheduler.shutdown();
    }

    /**
     * Adds a command to the command queue.
     * 
     * @param command The command to add to the queue.
     */
    public void addCommand(final TwitterCommand command)
    {
        commandQueue.add(command);
    }

    /**
     * Executes the enqueued Twitter API calls.
     */
    @Override
    public void run()
    {
        try
        {
            if (!commandQueue.isEmpty())
            {
                LOGGER.trace("Running command from queue...");

                TwitterCommand cmd = commandQueue.poll();
                cmd.execute(twitter);
            }
        }
        catch (TwitterException ex)
        {
            LOGGER.error("Could not execute the Twitter API call", ex);
        }
    }
}
