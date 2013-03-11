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

import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;

import com.google.common.base.Optional;
import com.rhymestore.lang.WordParser;

/**
 * Store that uses an alternate database to run the tests.
 * 
 * @author Ignasi Barrera
 */
@Singleton
public class TestRhymeStore extends RedisStore
{
    /** The Redis test database. */
    public static final int TEST_DATABASE = 1;

    @Inject
    public TestRhymeStore(@Named("sentence") final Keymaker sentencens,
        @Named("index") final Keymaker indexns, final WordParser wordParser, final Jedis redis,
        final Optional<String> redisPassword, final Charset encoding)
    {
        super(sentencens, indexns, wordParser, redis, redisPassword, encoding);
    }

    @Override
    protected void connect() throws UnknownHostException, IOException
    {
        super.connect();
        redis.select(TEST_DATABASE);
    }

    /**
     * Cleans the selected database.
     * 
     * @throws IOException If the database cannot be cleaned.
     */
    public void cleanDB() throws IOException
    {
        connect();
        redis.flushDB();
        disconnect();
    }
}
