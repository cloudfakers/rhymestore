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

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordParserFactory;
import com.rhymestore.lang.WordUtils;
import com.rhymestore.twitter.TwitterScheduler;

/**
 * Gets the list of mentions.
 * 
 * @author Ignasi Barrera
 * @see Twitter
 * @see TwitterScheduler
 */
public class GetMentionsCommand implements TwitterCommand
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMentionsCommand.class);

    /** The queue with the pending commands. */
    private final Queue<TwitterCommand> commandQueue;

    /** The id of the last tweet responded. */
    private long lastTweetId = -1L;

    /** The {@link WordParser} to use to check if the mention is well written. */
    private final WordParser wordParser;

    /**
     * Creates a new {@link ReplyCommand} for the given status.
     * 
     * @param commandQueue The queue with the pending commands.
     */
    public GetMentionsCommand(final Queue<TwitterCommand> commandQueue)
    {
        super();
        this.commandQueue = commandQueue;
        this.wordParser = WordParserFactory.getWordParser();
    }

    @Override
    public void execute(final Twitter twitter) throws TwitterException
    {
        // Get last mentions
        Paging paging = lastTweetId < 0 ? new Paging() : new Paging(lastTweetId);
        ResponseList<Status> mentions = twitter.getMentions(paging);

        // Reply only if the lastTweetId existed before calling this method
        // (only reply to mentions made since the application is running)
        if (lastTweetId > 0)
        {
            for (Status mention : mentions)
            {
                // Enqueue a reply to the mention only if it is a valid mention and
                // it is not from the current Twitter user
                if (isValidMention(mention) && !isCurrentUser(twitter, mention.getUser()))
                {
                    LOGGER.debug("Adding tweet {} from {}", mention.getId(), mention.getUser()
                        .getScreenName());

                    ReplyCommand reply = new ReplyCommand(mention, commandQueue);
                    commandQueue.add(reply);
                }
                else
                {
                    LOGGER.debug("Ignoring mention: {}", mention.getText());
                }
            }

            if (!mentions.isEmpty())
            {
                LOGGER.info("Added {} mentions to the reply queue.", mentions.size());
            }
        }

        // Update the lastTweetId to avoid reply duplication
        if (!mentions.isEmpty())
        {
            lastTweetId = mentions.get(0).getId();

            LOGGER.debug("Setting last tweet to {}", lastTweetId);
        }
    }

    /**
     * Gets the Id of the last processed tweet.
     * 
     * @return The Id of the last processed tweet.
     */
    protected long getLastTweetId()
    {
        return lastTweetId;
    }

    /**
     * Checks if the given user is the current Twitter user.
     * 
     * @param twitter The current Twitter connection data.
     * @param user The user to check.
     * @return Boolean indicating if the given user is the current Twitter user.
     * @throws TwitterException If an error occurs while checking user name.
     */
    private boolean isCurrentUser(Twitter twitter, User user) throws TwitterException
    {
        return user.getScreenName().equalsIgnoreCase(twitter.getScreenName());
    }

    /**
     * Checks if the given mention is a valid mention according to the {@link WordParser} rules.
     * 
     * @param mention The mention to check.
     * @return Boolean indicating if the given mention is a valid mention.
     */
    private boolean isValidMention(Status mention)
    {
        String lastWord = WordUtils.getLastWord(mention.getText());
        return wordParser.isWord(lastWord);
    }

}
