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

package com.rhymestore.twitter.commands;

import java.io.IOException;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.store.RhymeStore;
import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Executes a reply to a user's tweet.
 * 
 * @author Ignasi Barrera
 */
public class ReplyCommand implements TwitterCommand
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ReplyCommand.class);

	/** The queue with the pending commands. */
	private Queue<TwitterCommand> commandQueue;

	/** The status to reply. */
	private Status status;

	/** The Rhyme Store. */
	private RhymeStore rhymeStore;

	/**
	 * Creates a new {@link ReplyCommand} for the given status.
	 * 
	 * @param status The status to reply.
	 * @param commandQueue The queue with the pending commands.
	 */
	public ReplyCommand(final Status status,
			final Queue<TwitterCommand> commandQueue)
	{
		super();
		this.status = status;
		this.commandQueue = commandQueue;
		rhymeStore = RhymeStore.getInstance();
	}

	@Override
	public void execute(final Twitter twitter) throws TwitterException
	{
		String rhyme = null;

		try
		{
			rhyme = rhymeStore.getRhyme(status.getText());
		}
		catch (IOException ex)
		{
			LOGGER.error(
					"An error occured while connecting to the rhyme store. Could not reply to {}",
					status.getUser().getScreenName(), ex);
		}

		try
		{
			String tweet = TwitterUtils.reply(status.getUser().getScreenName(),
					rhyme);

			LOGGER.info("Replying to {} with: {}", status.getUser()
					.getScreenName(), tweet);

			twitter.updateStatus(tweet, status.getId());
		}
		catch (TwitterException ex)
		{
			LOGGER.error("Could not send reply to tweet " + status.getId(), ex);

			// If it is not a duplicate tweet, enqueue the API call again, to
			// retry it later
			if (!TwitterUtils.isDuplicateTweetError(ex))
			{
				LOGGER.debug("Enqueuing the reply to try again later...");

				commandQueue.add(this);
			}
		}
	}
}
