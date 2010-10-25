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

import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Gets the list of mentions.
 * 
 * @author Ignasi Barrera
 */
public class GetMentionsCommand implements TwitterCommand
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMentionsCommand.class);

    /** The queue with the pending commands. */
    private Queue<TwitterCommand> commandQueue;

    /** The id of the last tweet redponded. */
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
        Paging paging = lastTweetId < 0 ? new Paging() : new Paging(lastTweetId);
        ResponseList<Status> mentions = twitter.getMentions(paging);

        // Reply only if the lastTweetId existed before calling this method
        if (lastTweetId > 0)
        {
            // Enqueue a reply to each mention
            for (Status status : mentions)
            {
                LOGGER.debug("Adding tweet {} from {}", status.getId(), status.getUser()
                    .getScreenName());

                ReplyCommand reply = new ReplyCommand(status, commandQueue);
                commandQueue.add(reply);
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
}
