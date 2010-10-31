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
					error("Rhymes should have maximum "
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
