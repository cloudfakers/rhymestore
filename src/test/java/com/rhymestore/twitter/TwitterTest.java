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
