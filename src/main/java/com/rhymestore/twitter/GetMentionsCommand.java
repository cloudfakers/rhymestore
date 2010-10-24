package com.rhymestore.twitter;

import java.util.LinkedList;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Gets the list of mentions
 * 
 * @author Ignasi Barrera
 * 
 */
public class GetMentionsCommand implements TwitterCommand
{
	/** The queue with the pending commands. */
	private LinkedList<TwitterCommand> commandQueue;

	/**
	 * Creates a new {@link ReplyCommand} for the given status.
	 * 
	 * @param commandQueue The queue with the pending commands.
	 */
	public GetMentionsCommand(LinkedList<TwitterCommand> commandQueue)
	{
		super();
		this.commandQueue = commandQueue;
	}

	@Override
	public void execute(Twitter twitter) throws TwitterException
	{
		// TODO: Get mentions since last GetMentions call
		ResponseList<Status> mentions = twitter.getMentions();

		// Enqueue a reply to each mention
		for (Status status : mentions)
		{
			ReplyCommand reply = new ReplyCommand(status, commandQueue);
			commandQueue.add(reply);
		}
	}
}
