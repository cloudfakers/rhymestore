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

package com.rhymestore.lang;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.config.ConfigurationException;
import com.rhymestore.config.RhymestoreConfig;

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
						.getResourceAsStream(RhymestoreConfig.CONFIG_FILE));
			}
			catch (IOException ex)
			{
				throw new ConfigurationException(
						"Could not read the configuration file", ex);
			}

			String className = config
					.getProperty(RhymestoreConfig.WORDPARSER_PROPERTY);

			if (className == null)
			{
				throw new ConfigurationException(
						RhymestoreConfig.WORDPARSER_PROPERTY
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
