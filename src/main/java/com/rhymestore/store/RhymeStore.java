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

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.SpanishWordParser;

public class RhymeStore
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymeStore.class);

    public static final String DEFAULT_RHYME = "Patada en los cojones";

    private final Jedis redis;

    private final Keymaker namespace = new Keymaker("rhyme");

    private final String encoding = "UTF-8";

    public static RhymeStore instance;

    /** Parses the words to get the part used to rhyme. */
    private WordParser wordParser;

    public static RhymeStore getInstance()
    {
        if (instance == null)
        {
            instance = new RhymeStore();
        }
        return instance;
    }

    private RhymeStore()
    {
        redis = new Jedis("localhost", 6379);
        wordParser = new SpanishWordParser();
    }

    public RhymeStore(final String host, final int port)
    {
        redis = new Jedis(host, port);
    }

    public void add(final String sentence) throws IOException
    {
        if (sentence != null && sentence.length() > 0)
        {
            List<String> words = Arrays.asList(sentence.split(" "));

            String key = generateToken(words.get(words.size() - 1));
            String value = URLEncoder.encode(sentence, encoding);

            redis.connect();

            redis.sadd(namespace.build(key).toString(), value);

            redis.disconnect();
        }
    }

    public Set<String> findAll() throws IOException
    {
        Set<String> rhymes = new HashSet<String>();
        redis.connect();

        for (String key : redis.keys(namespace.build("*").toString()))
        {
            for (String sentence : redis.smembers(key))
            {
                rhymes.add(URLDecoder.decode(sentence, encoding));
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
        int lastSpace = sentence.lastIndexOf(" ");
        String lastWord = sentence.substring(lastSpace < 0 ? 0 : lastSpace + 1);
        String rhymepart = wordParser.rhymePart(lastWord);

        LOGGER.debug("Finding rhymes ending with {}", rhymepart);

        Set<String> rhymes = search(rhymepart);
        return rhymes.isEmpty() ? DEFAULT_RHYME : rhymes.iterator().next();
    }

    // TODO don't use the KEYS command! Use a trie instead.
    protected Set<String> search(String search) throws IOException
    {
        Set<String> rhymes = new HashSet<String>();

        search = "*".concat(generateToken(search));

        redis.connect();

        for (String key : redis.keys(namespace.build(search).toString()))
        {
            for (String sentence : redis.smembers(key))
            {
                rhymes.add(URLDecoder.decode(sentence, encoding));
            }
        }

        redis.disconnect();

        return rhymes;
    }

    private String generateToken(final String value)
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
