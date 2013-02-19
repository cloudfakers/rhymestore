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

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.store.RhymeStore;
import com.rhymestore.store.TestRhymeStore;
import com.rhymestore.twitter.mock.MockStatus;
import com.rhymestore.twitter.mock.MockTwitter;

/**
 * Unit tests for the {@link ReplyCommand} class.
 * 
 * @author Ignasi Barrera
 */
public class ReplyCommandTest
{
    /** The mocked Twitter API client. */
    private MockTwitter twitter;

    /** The store with the rhymes. */
    private RhymeStore store;

    @BeforeMethod
    public void setUp() throws IOException
    {
        // Add the rhymes
        store = new TestRhymeStore();
        store.add("Rima por defecto");
        store.add("Esta rima es infame"); // Rhymes with the mock screen name

        // Create the classes to test
        twitter = new MockTwitter();
    }

    @AfterMethod
    public void tearDown() throws IOException
    {
        ((TestRhymeStore) store).cleanDB();
    }

    @Test
    public void testExecuteWithExistingRhyme() throws TwitterException
    {
        ReplyCommand replyCommand = createReplyCommand(twitter, "Este test es casi perfecto");
        replyCommand.execute();

        assertTrue(twitter.getLastUpdatedStatus().contains("Rima por defecto"));
    }

    @Test
    public void testExecuteWithScreenNameRhyme() throws TwitterException
    {
        ReplyCommand replyCommand = createReplyCommand(twitter, "Rima esto con el usuario");
        replyCommand.execute();

        assertTrue(twitter.getLastUpdatedStatus().contains("Esta rima es infame"));
    }

    private ReplyCommand createReplyCommand(final Twitter twitter, final String status)
    {
        ReplyCommand replyCommand = new ReplyCommand(twitter, new MockStatus(status));
        replyCommand.rhymeStore = store;
        return replyCommand;
    }
}
