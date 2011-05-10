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

package com.rhymestore.twitter.commands;

import static org.testng.Assert.assertTrue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Integration tests for the {@link GetMentionsCommand} class.
 * 
 * @author Ignasi Barrera
 */
public class GetMentionsCommandIT
{
    /** The Twitter API client. */
    private Twitter twitter;

    /** The command queue used by the {@link #getMentionsCommand}. */
    private Queue<TwitterCommand> commandQueue;

    /** The Get Mentions command. */
    private GetMentionsCommand getMentionsCommand;

    @BeforeMethod
    public void setUp()
    {
        twitter = new TwitterFactory().getInstance();
        commandQueue = new LinkedBlockingDeque<TwitterCommand>(); // Thread-safe
        getMentionsCommand = new GetMentionsCommand(commandQueue);
    }

    @AfterMethod
    public void tearDown()
    {
        commandQueue.clear();
        twitter.shutdown();
    }

    @Test
    public void testGetFirstMentions() throws TwitterException
    {
        getMentionsCommand.execute(twitter);
        assertTrue(getMentionsCommand.getLastTweetId() > 0);
        assertTrue(commandQueue.isEmpty());
    }
}
