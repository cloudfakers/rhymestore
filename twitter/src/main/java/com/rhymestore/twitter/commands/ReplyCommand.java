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

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.lang.WordParser;
import com.rhymestore.store.RedisStore;
import com.rhymestore.twitter.scheduler.TwitterScheduler;
import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Executes a reply to a user's tweet.
 * 
 * @author Ignasi Barrera
 * @see Twitter
 * @see TwitterScheduler
 */
public class ReplyCommand extends AbstracTwitterCommand
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ReplyCommand.class);

    /** The status to reply. */
    private final Status status;

    /** The {@link WordParser} used to get the default rhyme if none is found. */
    private final WordParser wordParser;

    /** The Rhyme Store. */
    private final RedisStore rhymeStore;

    public ReplyCommand(final Twitter twitter, final WordParser wordParser,
        final RedisStore rhymeStore, final Status status)
    {
        super(twitter);
        this.rhymeStore = rhymeStore;
        this.wordParser = wordParser;
        this.status = status;
    }

    @Override
    public void execute() throws TwitterException
    {
        String rhyme = null;
        String targetUser = status.getUser().getScreenName();

        try
        {
            rhyme = rhymeStore.getRhyme(status.getText());

            if (rhyme == null)
            {
                // Try to rhyme with the user screen name
                if (wordParser.isWord(targetUser))
                {
                    LOGGER.info("Trying to rhyme with the screen name: {}", targetUser);
                    rhyme = rhymeStore.getRhyme(targetUser);
                }
            }

            if (rhyme == null)
            {
                rhyme = wordParser.getDefaultRhyme();

                LOGGER.info("No rhyme found. Using default rhyme: {}", rhyme);
            }
        }
        catch (IOException ex)
        {
            LOGGER.error(
                "An error occured while connecting to the rhyme store. Could not reply to {}",
                targetUser, ex);
        }

        try
        {
            String tweet = TwitterUtils.reply(targetUser, rhyme);

            LOGGER.info("Replying to {} with: {}", targetUser, tweet);

            // Reply to the user
            StatusUpdate newStatus = new StatusUpdate(tweet);
            newStatus.setInReplyToStatusId(status.getId());
            twitter.updateStatus(newStatus);
        }
        catch (TwitterException ex)
        {
            LOGGER.error("Could not send reply to tweet " + status.getId(), ex);
        }
    }

}
