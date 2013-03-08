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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.rhymestore.lang.StressType;
import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordUtils;

/**
 * Manages the Redis database to store and search rhymes.
 * 
 * @author Enric Ruiz
 * @see Keymaker
 * @see Jedis
 * @see WordParser
 */
@Singleton
public class RedisStore
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisStore.class);

    /** The key used to store the next id value. */
    private static final String NEXT_ID_KEY = "next.id";

    /** The character encoding to use. */
    private static final String ENCODING = "UTF-8";

    /** Redis namespace for sentences. */
    private final Keymaker sentencens;

    /** Redis namespace for index. */
    private final Keymaker indexns;

    /** Parses the words to get the part used to rhyme. */
    private final WordParser wordParser;

    /** The Redis database API. */
    protected final Jedis redis;

    private final String redisPassword;

    @Inject
    public RedisStore(@Named("sentence") final Keymaker sentencens,
        @Named("index") final Keymaker indexns, final WordParser wordParser, final Jedis redis)
    {
        super();
        this.sentencens = sentencens;
        this.indexns = indexns;
        this.wordParser = wordParser;
        this.redis = redis;
        this.redisPassword = System.getenv("REDISPASS");
    }

    /**
     * Adds the given rhyme to the Redis database.
     * 
     * @param sentence The rhyme to add.
     * @throws IOException If an error occurs while adding the rhyme.
     */
    public void add(final String sentence) throws IOException
    {
        String word = WordUtils.getLastWord(sentence);

        if (word.isEmpty())
        {
            return;
        }

        // Get the rhyme and type (and check that the word is valid before
        // adding)
        String rhyme = normalizeString(wordParser.phoneticRhymePart(word));
        StressType type = wordParser.stressType(word);

        connect();

        String sentenceKey = getUniqueId(sentencens, normalizeString(sentence));
        sentenceKey = sentencens.build(sentenceKey).toString();

        if (redis.exists(sentenceKey) == 1)
        {
            disconnect();
            return;
        }

        // Insert sentence
        redis.set(sentenceKey, URLEncoder.encode(sentence, ENCODING));

        // Index sentence
        String indexKey = getUniqueId(indexns, buildUniqueToken(rhyme, type));
        indexKey = indexns.build(indexKey).toString();

        redis.sadd(indexKey, sentenceKey);

        disconnect();

        LOGGER.info("Added rhyme: {}", sentence);
    }

    /**
     * Deletes the given rhyme from the Redis database.
     * 
     * @param sentence The rhyme to delete.
     * @throws IOException If an error occurs while deleting the rhyme.
     */
    public void delete(final String sentence) throws IOException
    {
        String word = WordUtils.getLastWord(sentence);

        if (word.isEmpty())
        {
            return;
        }

        String rhyme = normalizeString(wordParser.phoneticRhymePart(word));
        StressType type = wordParser.stressType(word);

        connect();

        String sentenceKey = getUniqueIdKey(sentencens, normalizeString(sentence));

        if (redis.exists(sentenceKey) == 0)
        {
            disconnect();
            throw new IOException("The element to remove does not exist.");
        }

        String indexKey = getUniqueIdKey(indexns, buildUniqueToken(rhyme, type));
        String sentenceId = redis.get(sentenceKey);
        sentenceId = sentencens.build(sentenceId).toString();

        // Remove the index
        if (redis.exists(indexKey) == 1)
        {
            String indexId = redis.get(indexKey);
            indexId = indexns.build(indexId).toString();

            // Remove the sentence from the index
            if (redis.exists(indexId) == 1)
            {
                redis.srem(indexId, sentenceId);
            }

            // Remove the index if empty
            if (redis.smembers(indexId).isEmpty())
            {
                redis.del(indexId, indexKey);
            }
        }

        // Remove the key
        redis.del(sentenceId, sentenceKey);

        disconnect();

        LOGGER.info("Deleted rhyme: {}", sentence);
    }

    /**
     * Gets all the stored rhymes.
     * 
     * @return A <code>Set</code> with all the stored rhymes.
     * @throws IOException If the rhymes cannot be obtained.
     */
    public Set<String> findAll() throws IOException
    {
        Set<String> rhymes = new HashSet<String>();

        connect();

        String lastId = getLastId(sentencens);

        if (lastId != null)
        {
            Integer n = Integer.parseInt(getLastId(sentencens));

            for (int i = 1; i <= n; i++)
            {
                String id = sentencens.build(String.valueOf(i)).toString();

                if (redis.exists(id) == 1)
                {
                    rhymes.add(URLDecoder.decode(redis.get(id), ENCODING));
                }
            }
        }

        disconnect();

        return rhymes;
    }

    /**
     * Gets a rhyme for the given sentence.
     * 
     * @param sentence The sentence to rhyme.
     * @return The rhyme.
     */
    public String getRhyme(final String sentence) throws IOException
    {
        String lastWord = WordUtils.getLastWord(sentence);

        String rhymepart = wordParser.phoneticRhymePart(lastWord);
        StressType type = wordParser.stressType(lastWord);

        LOGGER.debug("Finding rhymes for {}", sentence);

        connect();

        Set<String> rhymes = search(rhymepart, type);

        disconnect();

        if (rhymes.isEmpty())
        {
            // If no rhyme is found, return null
            return null;
        }
        else
        {
            // Otherwise, return a random rhyme
            List<String> rhymeList = new ArrayList<String>(rhymes);

            Random random = new Random(System.currentTimeMillis());
            int index = random.nextInt(rhymeList.size());

            return rhymeList.get(index);
        }
    }

    /**
     * Connects to the Redis database.
     * 
     * @throws UnknownHostException If the target host does not respond.
     * @throws IOException If an error occurs while connecting.
     */
    protected void connect() throws UnknownHostException, IOException
    {
        if (!redis.isConnected())
        {
            redis.connect();

            if (redisPassword != null)
            {
                redis.auth(redisPassword);
            }
        }
    }

    /**
     * Disconnects from the Redis database.
     * 
     * @throws IOException If an error occurs while disconnecting.
     */
    protected void disconnect() throws IOException
    {
        if (redis.isConnected())
        {
            redis.disconnect();
        }
    }

    /**
     * Search for rhymes for the given sentence.
     * 
     * @param rhyme The rhyme to search.
     * @param type The <code>StressType</code> of the rhyme to search.
     * @return A <code>Set</code> of rhymes for the given sentence.
     * @throws IOException If an error occurs while searching for the rhymes.
     */
    private Set<String> search(final String rhyme, final StressType type) throws IOException
    {
        Set<String> rhymes = new HashSet<String>();
        String norm = normalizeString(rhyme);

        String indexKey = getUniqueIdKey(indexns, buildUniqueToken(norm, type));

        if (redis.exists(indexKey) == 1)
        {
            String indexId = redis.get(indexKey);
            indexId = indexns.build(indexId).toString();

            if (redis.exists(indexId) == 1)
            {
                for (String sentenceKey : redis.smembers(indexId))
                {
                    if (redis.exists(sentenceKey) == 1)
                    {
                        rhymes.add(URLDecoder.decode(redis.get(sentenceKey), ENCODING));
                    }
                }
            }
        }

        return rhymes;
    }

    /**
     * Build a unique token for the given rhyme to be used to index it.
     * 
     * @param rhyme The rhyme part of the sentence.
     * @param type The stress type of the rhyme.
     * @return The unique token for the rhyme.
     */
    private String buildUniqueToken(final String rhyme, final StressType type)
    {
        return sum(type.name().concat(rhyme));
    }

    /**
     * Get the key of the id for the given token.
     * 
     * @param ns The namespace of the key.
     * @param token The token which key is requested.
     * @return The key for the given token.
     */
    private String getUniqueIdKey(final Keymaker ns, final String token)
    {
        String md = sum(token);
        return ns.build(md, "id").toString();
    }

    /**
     * Get a unique id id for the given token.
     * 
     * @param ns The namespace of the id.
     * @param token The token which id is requested.
     * @return The id for the given token.
     */
    private String getUniqueId(final Keymaker ns, final String token)
    {
        String key = getUniqueIdKey(ns, token);
        String id = redis.get(key);

        if (id != null)
        {
            return id;
        }

        Integer next = redis.incr(ns.build(NEXT_ID_KEY).toString());
        id = next.toString();

        if (redis.setnx(key, id) == 0)
        {
            id = redis.get(key);
        }

        return id;
    }

    /**
     * Get the last used id in the given namespace.
     * 
     * @param ns The namespace.
     * @return The last used id in the given namespace.
     */
    private String getLastId(final Keymaker ns)
    {
        return redis.get(ns.build(NEXT_ID_KEY).toString());
    }

    /**
     * Makes a md5 sum of the given text.
     * 
     * @param value The text to sum.
     * @return The md5 sum of the given text.
     */
    private String sum(final String value)
    {
        return DigestUtils.md5Hex(value.getBytes());
    }

    /**
     * Normalizes the given string.
     * 
     * @param value The string to be normalized.
     * @return The normalized string.
     */
    private String normalizeString(final String value)
    {
        // To lower case
        String token = value.toLowerCase();

        // Remove diacritics
        token = Normalizer.normalize(token, Form.NFD);
        token = token.replaceAll("[^\\p{ASCII}]", "");

        // Remove non alphanumeric characters
        token = token.replaceAll("[^a-zA-Z0-9]", "");

        return token;
    }

}
