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

package com.rhymestore.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.store.RhymeStore;
import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Controller to manage stored rhymes.
 * 
 * @author Ignasi Barrera
 */
public class RhymeController extends MethodInvokingController
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RhymeController.class);

	/** The Rhyme store. */
	private RhymeStore store;

	/**
	 * Default constructor
	 */
	public RhymeController()
	{
		store = RhymeStore.getInstance();
	}

	/**
	 * Lists all rhymes in the the store.
	 * 
	 * @param request The request.
	 * @param response The response.
	 * @throws ControllerException If the rhyme cannot be added.
	 */
	public void list(final HttpServletRequest request,
			final HttpServletResponse response) throws ControllerException
	{
		// Add the rhyme if present
		String rhyme = request.getParameter("rhyme");

		if (rhyme != null && rhyme.length() > 0)
		{
			try
			{
				if (rhyme.length() > TwitterUtils.MAX_TWEET_LENGTH)
				{
					error("Rhymes must have less than or "
							+ TwitterUtils.MAX_TWEET_LENGTH + " characters");
				}

				String twitterUser = getTwitterUser(request, response);
				if (rhyme.contains(TwitterUtils.user(twitterUser)))
				{
					error("Cannot add a rhyme that contains the Twitter user name");
				}

				if (!errors())
				{
					String capitalized = capitalize(rhyme);
					store.add(capitalized);

					LOGGER.info("Added rhyme: {}", capitalized);
				}
			}
			catch (Exception ex)
			{
				error("Could not add rhyme: " + ex.getMessage(), ex);
			}
		}

		// List all rhymes
		try
		{
			Set<String> rhymes = store.findAll();

			List<String> sortedRhymes = new ArrayList<String>(rhymes);
			Collections.sort(sortedRhymes, String.CASE_INSENSITIVE_ORDER);

			request.setAttribute("rhymes", sortedRhymes);
		}
		catch (IOException ex)
		{
			error("Could not get rhymes: " + ex.getMessage(), ex);
		}
	}

	/**
	 * Capitalizes the given String.
	 * 
	 * @param str The String to capitalize.
	 * @return The capitalized String.
	 */
	private static String capitalize(final String str)
	{
		switch (str.length()) {
		case 0:
			return str;
		case 1:
			return str.toUpperCase();
		default:
			return str.substring(0, 1).toUpperCase() + str.substring(1);
		}
	}
}
