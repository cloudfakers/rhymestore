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
import java.net.UnknownHostException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

import com.rhymestore.lang.SpanishWordParser;
import com.rhymestore.lang.StressType;
import com.rhymestore.lang.WordParser;

public class RhymeStore
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymeStore.class);

    public static final String DEFAULT_RHYME = "Patada en los cojones";

    private final Jedis redis;

    /** redis namespace for sentences. */
    private final Keymaker sentencens = new Keymaker("sentence");

    /** redis namespace for index. */
    private final Keymaker indexns = new Keymaker("index");

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
        wordParser = new SpanishWordParser();
    }

    public void add(final String sentence) throws IOException
    {
        String word = getLastWord(sentence);

        if (word.isEmpty())
        {
            return;
        }

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
        String rhyme = normalizeString(wordParser.phoneticRhymePart(word));
        StressType type = wordParser.stressType(word);

        String indexId = getUniqueId(indexns, buildUniqueToken(rhyme, type));
        indexId = indexns.build(indexId).toString();

        redis.sadd(indexId, sentenceId);

        disconnect();
    }

    protected Set<String> search(final String rhyme, final StressType type) throws IOException
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

    protected String sum(final String value)
    {
        return DigestUtils.md5Hex(value.getBytes());
    }

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

    public static String reverseString(final String value)
    {
        int i, len = value.length();
        StringBuffer dest = new StringBuffer(len);

        for (i = (len - 1); i >= 0; i--)
        {
            dest.append(value.charAt(i));
        }

        return dest.toString();
    }

    protected void connect() throws UnknownHostException, IOException
    {
        if (!redis.isConnected())
        {
            redis.connect();
        }
    }

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

    public Set<String> findAll() throws IOException
    {
        Set<String> rhymes = new HashSet<String>();

        redis.connect();

        Integer n = Integer.parseInt(getLastId(sentencens));

        for (int i = 1; i <= n; i++)
        {
            String id = sentencens.build(String.valueOf(i)).toString();

            if (redis.exists(id) == 1)
            {
                rhymes.add(URLDecoder.decode(redis.get(id), encoding));
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

        return rhymes.isEmpty() ? DEFAULT_RHYME : rhymes.iterator().next();
    }
}
