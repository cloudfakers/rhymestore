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

package com.rhymestore.store;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to load rhymes from a file.
 * 
 * @author Ignasi Barrera
 * @see RedisStore
 */
@Singleton
public class RhymeLoader
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymeLoader.class);

    /** The backend rhyme store. */
    private final RedisStore store;

    @Inject
    public RhymeLoader(final RedisStore store)
    {
        super();
        this.store = store;
    }

    /**
     * Loads the rhymes in the given file into the {@link #store}.
     * 
     * @param file The file with the rhymes to add.
     * @throws IOException If the rhymes cannot be loaded.
     */
    public void load(final File file) throws IOException
    {
        if (!file.exists())
        {
            throw new IOException("The rhyme file does not exist");
        }

        load(new FileInputStream(file));
    }

    /**
     * Loads the rhymes in the given {@link InputStream} into the {@link #store}.
     * 
     * @param in The stream with the rhymes to add.
     * @throws IOException If the rhymes cannot be loaded.
     */
    public void load(final InputStream in) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();
        int numLines = 0;

        while (line != null)
        {
            store.add(line);
            line = br.readLine();
            numLines++;
        }

        LOGGER.info("Loaded {} rhymes", numLines);
    }
}
