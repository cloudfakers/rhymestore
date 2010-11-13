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

import com.rhymestore.twitter.TwitterScheduler;

/**
 * Gets the list of mentions.
 * 
 * @author Ignasi Barrera
 * 
 * @see Twitter
 * @see TwitterScheduler
 */
public class GetMentionsCommand implements TwitterCommand
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(GetMentionsCommand.class);

	/** The queue with the pending commands. */
	private final Queue<TwitterCommand> commandQueue;

	/** The id of the last tweet responded. */
	private long lastTweetId = -1L;

	/**
	 * Creates a new {@link ReplyCommand} for the given status.
	 * 
	 * @param commandQueue The queue with the pending commands.
	 */
	public GetMentionsCommand(final Queue<TwitterCommand> commandQueue)
	{
		super();
		this.commandQueue = commandQueue;
	}

	@Override
	public void execute(final Twitter twitter) throws TwitterException
	{
		// Get last mentions
		Paging paging = lastTweetId < 0 ? new Paging()
				: new Paging(lastTweetId);
		ResponseList<Status> mentions = twitter.getMentions(paging);

		// Reply only if the lastTweetId existed before calling this method
		// (only reply to mentions
		// made since the application is running)
		if (lastTweetId > 0)
		{
			for (Status status : mentions)
			{
				// Enqueue a reply to the mention if it is not from the current
				// Twitter user
				if (!status.getUser().getScreenName()
						.equalsIgnoreCase(twitter.getScreenName()))
				{
					LOGGER.debug("Adding tweet {} from {}", status.getId(),
							status.getUser().getScreenName());

					ReplyCommand reply = new ReplyCommand(status, commandQueue);
					commandQueue.add(reply);
				}
				else
				{
					LOGGER.debug("Ignoring mention from the current user: {}",
							status.getText());
				}
			}

			if (!mentions.isEmpty())
			{
				LOGGER.info("Added {} mentions to the reply queue.",
						mentions.size());
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
	public long getLastTweetId()
	{
		return lastTweetId;
	}

}
