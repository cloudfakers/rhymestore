package com.rhymestore.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;

import com.rhymestore.twitter.TwitterScheduler;

/**
 * Initializes and shuts down the twitter scheduler.
 * 
 * @author Ignasi Barrera
 * 
 */
public class StartupContextListener implements ServletContextListener
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(StartupContextListener.class);

	/** The Twitter API call scheduler. */
	private TwitterScheduler twitterScheduler;

	/** The Twitter API client. */
	private Twitter twitter;

	@Override
	public void contextInitialized(ServletContextEvent sce)
	{
		LOGGER.info("Starting the Twitter API scheduler");

		// Connects to Twitter and starts the execution of API calls
		twitter = new TwitterFactory().getInstance();
		twitterScheduler = new TwitterScheduler(twitter);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{
		LOGGER.info("Shutting down the Twitter API scheduler");

		twitterScheduler.shutdown(); // Stop scheduler
		twitter.shutdown(); // Disconnect from Twitter
	}

}
