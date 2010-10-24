package com.rhymestore.twitter;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Set;

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
	/** The queue with the pending commands. */
	private LinkedList<TwitterCommand> commandQueue;

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
	public ReplyCommand(Status status, LinkedList<TwitterCommand> commandQueue)
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
		} catch (IOException e)
		{
			// Do nothing, return the default rhyme
		}

		try
		{
			twitter.updateStatus(rhyme, status.getId());
		} catch (TwitterException ex)
		{
			// Enqueue again the reply to retry later
			commandQueue.add(this);
		}
	}
}
