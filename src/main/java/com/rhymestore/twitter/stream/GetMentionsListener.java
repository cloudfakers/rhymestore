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

package com.rhymestore.twitter.stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.UserMentionEntity;
import twitter4j.UserStreamAdapter;

import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordParserFactory;
import com.rhymestore.lang.WordUtils;
import com.rhymestore.twitter.TwitterScheduler;
import com.rhymestore.twitter.commands.ReplyCommand;

/**
 * Read the mentions from the stream API and enqueue the replies.
 * 
 * @author Ignasi Barrera
 * @see TwitterScheduler
 */
public class GetMentionsListener extends UserStreamAdapter
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMentionsListener.class);

    /** The {@link WordParser} to use to check if the mention is well written. */
    private final WordParser wordParser;

    /** The Twitter sync api. */
    private final Twitter twitter;

    /** The command scheduler. */
    private final TwitterScheduler scheduler;

    public GetMentionsListener(final Twitter twitter, final TwitterScheduler scheduler)
    {
        super();
        this.wordParser = WordParserFactory.getWordParser();
        this.twitter = twitter;
        this.scheduler = scheduler;
    }

    @Override
    public void onStatus(final Status status)
    {
        UserMentionEntity[] mentions = status.getUserMentionEntities();
        if (mentions != null)
        {
            for (UserMentionEntity mention : mentions)
            {
                try
                {
                    // Check if there is any mention to us
                    if (isCurrentUser(twitter, mention.getScreenName()))
                    {
                        // Only reply if it is a valid mention
                        if (isValidMention(status)
                            && !isCurrentUser(twitter, status.getUser().getScreenName()))
                        {
                            LOGGER.debug("Adding tweet {} from {}", status.getId(), status
                                .getUser().getScreenName());

                            ReplyCommand reply = new ReplyCommand(status, scheduler);
                            scheduler.addCommand(reply);
                        }
                        else
                        {
                            LOGGER.debug("Ignoring mention: {}", status.getText());
                        }

                        // Do not process the same tweet more than once
                        break;
                    }
                }
                catch (TwitterException ex)
                {
                    LOGGER.error("Could not process status: {}", status.getText());
                }
            }
        }
    }

    /**
     * Checks if the given mention is a valid mention according to the {@link WordParser} rules.
     * 
     * @param mention The mention to check.
     * @return Boolean indicating if the given mention is a valid mention.
     */
    private boolean isValidMention(final Status mention)
    {
        String lastWord = WordUtils.getLastWord(mention.getText());
        return wordParser.isWord(lastWord);
    }

    /**
     * Checks if the given user is the current Twitter user.
     * 
     * @param twitter The current Twitter connection data.
     * @param user The user to check.
     * @return Boolean indicating if the given user is the current Twitter user.
     * @throws TwitterException If an error occurs while checking user name.
     */
    private boolean isCurrentUser(final Twitter twitter, final String user) throws TwitterException
    {
        return user.equalsIgnoreCase(twitter.getScreenName());
    }
}
