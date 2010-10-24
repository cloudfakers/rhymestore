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

/**
 * Schedules Twitter API calls to execute them in order.
 * <p>
 * Twitter only allows 150 calls per hour. This class will enqueue and run all
 * requested API calls when possible.
 * 
 * @author Ignasi Barrera
 * 
 */
public class TwitterScheduler implements Runnable
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(TwitterScheduler.class);

	/**
	 * The call interval between Twitter API calls per hour.
	 * <p>
	 * 150 calls per hour = 2.5 calls per minute => Execute each 30 seconds.
	 */
	private static final long CALL_INTERVAL = 30;

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
	public TwitterScheduler(Twitter twitter)
	{
		super();
		this.twitter = twitter;
		this.commandQueue = new LinkedBlockingDeque<TwitterCommand>(); // Thread-safe
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
		this.scheduler.scheduleAtFixedRate(this, 0, CALL_INTERVAL,
				TimeUnit.SECONDS);
	}

	/**
	 * Shuts down the scheduler.
	 */
	public void shutdown()
	{
		this.scheduler.shutdown();
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

				new GetMentionsCommand(commandQueue).execute(twitter);
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
