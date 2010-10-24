package com.rhymestore.twitter.commands;

import java.io.IOException;
import java.util.Queue;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.rhymestore.store.RhymeStore;

/**
 * Executes a reply to a user's tweet.
 * 
 * @author Ignasi Barrera
 * 
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
	public ReplyCommand(Status status, Queue<TwitterCommand> commandQueue)
	{
		super();
		this.status = status;
		this.commandQueue = commandQueue;
		this.rhymeStore = RhymeStore.getInstance();
	}

	@Override
	public void execute(Twitter twitter) throws TwitterException
	{
		String rhyme = RhymeStore.DEFAULT_RHYME;

		try
		{
			Set<String> rhymes = rhymeStore.search(status.getText());
			rhyme = rhymes.iterator().next();
		}
		catch (IOException e)
		{
			// Do nothing, return the default rhyme
		}

		// try
		// {
		String tweet = "@" + status.getUser().getScreenName() + " " + rhyme;
		if (tweet.length() > 140)
		{
			tweet = tweet.substring(0, MAX_TWEET_LENGTH);
		}

		LOGGER.debug(tweet);

		// TODO: Uncomment this when authentication error is fixed.
		// String tweet = rhyme.substring(0, MAX_TWEET_LENGTH);
		// twitter.updateStatus(tweet, status.getId());
		// }
		// catch (TwitterException ex)
		// {
		// Enqueue again the reply to retry later
		// commandQueue.add(this);
		// }
	}
}
