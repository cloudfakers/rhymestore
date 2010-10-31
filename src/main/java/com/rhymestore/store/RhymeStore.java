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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.rhymestore.lang.StressType;
import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordParserFactory;

/**
 * Manages the Redis database to store and search rhymes.
 * 
 * @author Enric Ruiz
 * 
 * @see Keymaker
 * @see Jedis
 * @see WordParser
 */
public class RhymeStore
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RhymeStore.class);

	/** The default rhyme to use if none is found. */
	public static final String DEFAULT_RHYME = "Patada en los cojones";

	/** The Redis database API. */
	private final Jedis redis;

	/** Redis namespace for sentences. */
	private final Keymaker sentencens = new Keymaker("sentence");

	/** Redis namespace for index. */
	private final Keymaker indexns = new Keymaker("index");

	/** The character encoding to use. */
	private final String encoding = "UTF-8";

	/** The singleton instance of the store. */
	public static RhymeStore instance;

	/** Parses the words to get the part used to rhyme. */
	private WordParser wordParser;

	/**
	 * Gets the singleton instance of the store.
	 * 
	 * @return The singleton instance of the store.
	 */
	public static RhymeStore getInstance()
	{
		if (instance == null)
		{
			instance = new RhymeStore();
		}

		return instance;
	}

	/**
	 * Creates a new <code>RhymeStore</code> connecting to
	 * <code>localhost</code> and the default Redis port.
	 */
	private RhymeStore()
	{
		redis = new Jedis("localhost", 6379);
		wordParser = WordParserFactory.getWordParser();
	}

	/**
	 * Creates a new <code>RhymeStore</code> connecting to the given host and
	 * port.
	 * 
	 * @param host The Redis host.
	 * @param port The Redis listening port.
	 */
	public RhymeStore(final String host, final int port)
	{
		redis = new Jedis(host, port);
		wordParser = WordParserFactory.getWordParser();
	}

	/**
	 * Adds the given rhyme to the Redis database.
	 * 
	 * @param sentence The rhyme to add.
	 * @throws IOException If an error occurs while adding the rhyme.
	 */
	public void add(final String sentence) throws IOException
	{
		String word = getLastWord(sentence);

		if (word.isEmpty())
		{
			return;
		}

		// Get the rhyme and type (and check that the word is valid before
		// adding)
		String rhyme = normalizeString(wordParser.phoneticRhymePart(word));
		StressType type = wordParser.stressType(word);

		connect();

		String sentenceId = getUniqueId(sentencens, normalizeString(sentence));
		sentenceId = sentencens.build(sentenceId).toString();

		if (redis.exists(sentenceId) == 1)
		{
			disconnect();
			return;
		}

		// Insert sentence
		redis.set(sentenceId, URLEncoder.encode(sentence, encoding));

		// Index sentence
		String indexId = getUniqueId(indexns, buildUniqueToken(rhyme, type));
		indexId = indexns.build(indexId).toString();

		redis.sadd(indexId, sentenceId);

		disconnect();
	}

	/**
	 * Search for rhymes for the given sentence.
	 * 
	 * @param rhyme The rhyme to search.
	 * @param type The <code>StressType</code> of the rhyme to search.
	 * @return A <code>Set</code> of rhymes for the given sentence.
	 * @throws IOException If an error occurs while searching for the rhymes.
	 */
	protected Set<String> search(final String rhyme, final StressType type)
			throws IOException
	{
		Set<String> rhymes = new HashSet<String>();
		String norm = normalizeString(rhyme);

		String uniqueId = getUniqueIdKey(indexns, buildUniqueToken(norm, type));

		if (redis.exists(uniqueId) == 1)
		{
			String indexId = redis.get(uniqueId);
			indexId = indexns.build(indexId).toString();

			if (redis.exists(indexId) == 1)
			{
				for (String id : redis.smembers(indexId))
				{
					if (redis.exists(id) == 1)
					{
						rhymes.add(URLDecoder.decode(redis.get(id), encoding));
					}
				}
			}
		}

		return rhymes;
	}

	protected String buildUniqueToken(final String rhyme, final StressType type)
	{
		return sum(type.name().concat(rhyme));
	}

	protected String getUniqueIdKey(final Keymaker ns, final String token)
	{
		String md = sum(token);
		return ns.build(md, "id").toString();
	}

	protected String getUniqueId(final Keymaker ns, final String token)
	{
		String key = getUniqueIdKey(ns, token);
		String id = redis.get(key);

		if (id != null)
		{
			return id;
		}

		Integer next = redis.incr(ns.build("next.id").toString());
		id = next.toString();

		if (redis.setnx(key, id) == 0)
		{
			id = redis.get(key);
		}

		return id;
	}

	protected String getLastId(final Keymaker ns)
	{
		return redis.get(ns.build("next.id").toString());
	}

	/**
	 * Makes a md5 sum of the given text.
	 * 
	 * @param value The text to sum.
	 * @return The md5 sum of the given text.
	 */
	protected String sum(final String value)
	{
		return DigestUtils.md5Hex(value.getBytes());
	}

	/**
	 * Gets the last word of the given sentence.
	 * 
	 * @param sentence The sentence to parse.
	 * @return The last word of the given sentence.
	 */
	protected String getLastWord(final String sentence)
	{
		String word = "";

		if (sentence != null)
		{
			List<String> words = Arrays.asList(sentence.split(" "));

			if (words.size() > 0)
			{
				word = words.get(words.size() - 1);
			}
		}

		return word;
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

	protected String normalizeString(final String value)
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

	/**
	 * Gets all the stored rhymes.
	 * 
	 * @return A <code>Set</code> with all the stored rhymes.
	 * @throws IOException If the rhymes cannot be obtained.
	 */
	public Set<String> findAll() throws IOException
	{
		Set<String> rhymes = new HashSet<String>();

		redis.connect();

		String lastId = getLastId(sentencens);

		if (lastId != null)
		{
			Integer n = Integer.parseInt(getLastId(sentencens));

			for (int i = 1; i <= n; i++)
			{
				String id = sentencens.build(String.valueOf(i)).toString();

				if (redis.exists(id) == 1)
				{
					rhymes.add(URLDecoder.decode(redis.get(id), encoding));
				}
			}
		}

		redis.disconnect();

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
		String lastWord = getLastWord(sentence);

		String rhymepart = wordParser.phoneticRhymePart(lastWord);
		StressType type = wordParser.stressType(lastWord);

		LOGGER.debug("Finding rhymes for {}", sentence);

		redis.connect();

		Set<String> rhymes = search(rhymepart, type);

		redis.disconnect();

		if (rhymes.isEmpty())
		{
			// If no rhyme is found, return the default rhyme
			return DEFAULT_RHYME;
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
}
