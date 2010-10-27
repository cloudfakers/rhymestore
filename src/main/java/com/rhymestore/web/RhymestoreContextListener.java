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

package com.rhymestore.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.rhymestore.twitter.TwitterScheduler;

/**
 * Initializes and shuts down the twitter scheduler.
 * 
 * @author Ignasi Barrera
 */
public class RhymestoreContextListener implements ServletContextListener
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymestoreContextListener.class);

    /** Context attribute name used to store the Twitter user. */
    public static final String TWITTER_USER_NAME = "TWITTER_USER_NAME";

    /** Context parameter name used to enable or disable twitter communication. */
    private static final String TWITTER_ENABLE_PARAM_NAME = "TWITTER_ENABLED";

    /** The Twitter API call scheduler. */
    private TwitterScheduler twitterScheduler;

    /** The Twitter API client. */
    private Twitter twitter;

    @Override
    public void contextInitialized(final ServletContextEvent sce)
    {
        // Connects to Twitter and starts the execution of API calls
        twitter = new TwitterFactory().getInstance();

        // Store the user name in the Servlet Context to make it available to Controllers
        try
        {
            sce.getServletContext().setAttribute(TWITTER_USER_NAME, twitter.getScreenName());
        }
        catch (TwitterException ex)
        {
            LOGGER.error("Could not get the Twitter username", ex);
        }

        // Starts the Twitter scheduler
        if (twitterEnabled(sce))
        {
            LOGGER.info("Starting the Twitter API scheduler");

            twitterScheduler = new TwitterScheduler(twitter);
            twitterScheduler.start();
        }
        else
        {
            LOGGER.info("Twitter communication is disabled");
        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce)
    {
        if (twitterEnabled(sce))
        {
            LOGGER.info("Shutting down the Twitter API scheduler");

            twitterScheduler.shutdown(); // Stop scheduler
        }

        twitter.shutdown(); // Disconnect from Twitter
    }

    /**
     * Checks if Twitter communication is enabled.
     * 
     * @param sce The <code>ServletContextEvent</code>.
     * @return A boolean indicating if Twitter communication is enabled.
     */
    private boolean twitterEnabled(final ServletContextEvent sce)
    {
        String enableTwitter = sce.getServletContext().getInitParameter(TWITTER_ENABLE_PARAM_NAME);
        return enableTwitter == null || enableTwitter.equals("true");
    }

}
