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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import twitter4j.Twitter;
import twitter4j.User;

import com.google.common.base.Supplier;
import com.rhymestore.config.RhymeModule;
import com.rhymestore.config.RhymeStore;
import com.rhymestore.twitter.config.TwitterConfig;
import com.rhymestore.twitter.config.TwitterModule;

/**
 * Twitter API client integration tests.
 * 
 * @author Ignasi Barrera
 */
public class TwitterTest
{
    /** The Twitter API client. */
    private Twitter twitter;

    @BeforeMethod
    public void setUp()
    {
        try
        {
            RhymeStore.create(new RhymeModule(), new TwitterModule());
        }
        catch (Exception ex)
        {
            // Skip this test where the environment is not configured
            throw new SkipException("Twitter environment is not configured");
        }

        twitter = TwitterConfig.getTwitter();
    }

    @Test
    public void testTwitterConnect() throws Exception
    {
        assertNotNull(twitter.verifyCredentials());
    }

    @Test
    public void testSuppliedUser() throws Exception
    {
        Supplier<String> userSupplier = TwitterConfig.getTwitterUser();
        User user = twitter.verifyCredentials();

        assertEquals(userSupplier.get(), user.getScreenName());
    }
}
