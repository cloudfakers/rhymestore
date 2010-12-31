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

package com.rhymestore.lang;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.config.ConfigurationException;
import com.rhymestore.config.Configuration;

/**
 * Factory class to create the {@link WordParser}.
 * 
 * @author Ignasi Barrera
 * 
 * @see WordParser
 */
public class WordParserFactory
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(WordParserFactory.class);

	/** The {@link WordParser} used in the application. */
	private static WordParser wordParser;

	/**
	 * Gets the {@link WordParser} to be used in the application.
	 * 
	 * @return The <code>WordParser</code> to be used in the application.
	 * @throws ConfigurationException If the <code>WordParser</code> cannot be
	 *             created.
	 */
	@SuppressWarnings("unchecked")
	public static WordParser getWordParser() throws ConfigurationException
	{
		if (wordParser == null)
		{
			ClassLoader classLoader = Thread.currentThread()
					.getContextClassLoader();
			Properties config = new Properties();

			try
			{
				config.load(classLoader
						.getResourceAsStream(Configuration.CONFIG_FILE));
			}
			catch (IOException ex)
			{
				throw new ConfigurationException(
						"Could not read the configuration file", ex);
			}

			String className = config
					.getProperty(Configuration.WORDPARSER_PROPERTY);

			if (className == null)
			{
				throw new ConfigurationException(
						Configuration.WORDPARSER_PROPERTY
								+ " property not defined");
			}

			LOGGER.info("Using WordParser: {}", className);

			try
			{
				Class<? extends WordParser> clazz = (Class<? extends WordParser>) Class
						.forName(className, true, classLoader);
				wordParser = clazz.newInstance();
			}
			catch (Exception ex)
			{
				throw new ConfigurationException(
						"Could not create the WordParser of class: "
								+ className, ex);
			}
		}

		return wordParser;
	}
}
