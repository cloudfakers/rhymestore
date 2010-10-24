package com.rhymestore.twitter;

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

	/**
	 * Executes a Twitter API call in the given account.
	 * 
	 * @param twitter The Twitter account to use.
	 * @throws If the API call returns an error.
	 */
	public void execute(Twitter twitter) throws TwitterException;
}
