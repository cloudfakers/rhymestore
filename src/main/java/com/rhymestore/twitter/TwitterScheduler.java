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

import com.rhymestore.twitter.commands.GetMentionsCommand;
import com.rhymestore.twitter.commands.TwitterCommand;
import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Schedules Twitter API calls to execute them in order.
 * <p>
 * Twitter only allows {@link TwitterUtils#MAX_API_CALLS_PER_HOUR} calls per
 * hour. This class will enqueue and run all requested API calls when possible.
 * 
 * @author Ignasi Barrera
 * 
 * @see TwitterCommand
 */
public class TwitterScheduler implements Runnable
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TwitterScheduler.class);

	/** The scheduler service. */
	private ScheduledExecutorService scheduler;

	/** The queue with the pending commands. */
	private Queue<TwitterCommand> commandQueue;

	/** The Twitter account where the API calls will be performed. */
	private Twitter twitter;

	/** The command used to get mentions, */
	private TwitterCommand getMentionsCommand;

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
		getMentionsCommand = new GetMentionsCommand(commandQueue);
		scheduler = Executors.newSingleThreadScheduledExecutor();
	}

	/**
	 * Starts down the scheduler.
	 */
	public void start()
	{
		double callsPerMinute = Math
				.floor(TwitterUtils.MAX_API_CALLS_PER_HOUR / 60);
		long interval = (long) Math.ceil(60 / callsPerMinute);

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
	 * Executes the enqueued Twitter API calls.
	 */
	@Override
	public void run()
	{
		try
		{
			if (commandQueue.isEmpty())
			{
				LOGGER.debug("Running GetMentions API call...");

				getMentionsCommand.execute(twitter);
			}
			else
			{
				LOGGER.debug("Running Reply API call...");

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
