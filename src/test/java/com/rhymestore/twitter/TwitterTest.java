package com.rhymestore.twitter;

import junit.framework.Assert;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Twitter API client Unit tests.
 * 
 * @author Ignasi Barrera
 * 
 */
public class TwitterTest
{
	/** The Twitter API client. */
	private Twitter twitter;

	@BeforeMethod
	public void setUp()
	{
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
		Assert.assertEquals("rimamelo", user.getScreenName());
	}

	@Test(enabled = false)
	public void testUpdateStatus() throws Exception
	{
		twitter.updateStatus("Rhymestore tests passed!");
	}
}
