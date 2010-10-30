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

package com.rhymestore.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to load rhymes from a file.
 * 
 * @author Ignasi Barrera
 * 
 * @see RhymeStore
 */
public class RhymeLoader
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RhymeLoader.class);

	/** The backend rhyme store. */
	private RhymeStore store;

	/**
	 * Default constructor.
	 */
	public RhymeLoader()
	{
		store = RhymeStore.getInstance();
	}

	/**
	 * Loads the rhymes in the given file into the {@link #store}.
	 * 
	 * @param file The file with the rhymes to add.
	 * @throws IOException If the rhymes cannot be loaded.
	 */
	public void load(File file) throws IOException
	{
		if (!file.exists())
		{
			throw new IOException("The rhyme file does not exist");
		}

		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int numLines = 0;

		while (line != null)
		{
			store.add(line);
			line = br.readLine();
			numLines++;
		}

		LOGGER.info("Added {} rhymes", numLines);
	}

	/**
	 * Adds the rhymes in the given file to the rhyme store.
	 * 
	 * @param args The absolute path of the file containing the rhymes.
	 */
	public static void main(String... args)
	{
		if (args.length != 1)
		{
			throw new IllegalArgumentException("The file path is required");
		}

		File file = new File(args[0]);
		RhymeLoader loader = new RhymeLoader();

		try
		{
			loader.load(file);
		}
		catch (IOException ex)
		{
			LOGGER.error("Could not load rhymes: " + ex.getMessage(), ex);
		}
	}
}
