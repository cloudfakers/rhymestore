package com.rhymestore.twitter.commands;

import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Executes Twitter API calls.
 * 
 * @author Ignasi Barrera
 * 
 */
public interface TwitterCommand
{
	/** Maximum length for the tweets. */
	public static final int MAX_TWEET_LENGTH = 140;

	/**
	 * Executes a Twitter API call in the given account.
	 * 
	 * @param twitter The Twitter account to use.
	 * @throws If the API call returns an error.
	 */
	public void execute(Twitter twitter) throws TwitterException;
}
