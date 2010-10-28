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
        wordParser = new WordParser();
    }

    public RhymeStore(final String host, final int port)
    {
        redis = new Jedis(host, port);
        wordParser = new WordParser();
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
        String token = reverseString(word);
        String tokenId = getUniqueId(indexns, URLEncoder.encode(token, encoding));
        String lastTokenId = indexns.build(tokenId).toString();

        redis.sadd(lastTokenId, sentenceId);
        token = token.substring(0, token.length() - 1);

        while (token.length() > 0)
        {
            tokenId = getUniqueId(indexns, URLEncoder.encode(token, encoding));
            redis.sadd(indexns.build(tokenId).toString(), lastTokenId);

            token = token.substring(0, token.length() - 1);
            lastTokenId = indexns.build(tokenId).toString();
        }

        disconnect();
    }

    // TODO accents! insertar cada token amb un urlencode pero no fer el -1 de l'encoded!
    protected Set<String> search(String search) throws IOException
    {
        Set<String> rhymes = new HashSet<String>();

        String token = reverseString(search);
        String indexId = null;
        boolean found = false;

        while (token.length() > 0 && !found)
        {
            String key = indexns.build(sum(token), "id").toString();

            if (redis.exists(key) == 1)
            {
                String id = redis.get(key);

                if (redis.exists(indexns.build(id).toString()) == 1)
                {
                    indexId = indexns.build(id).toString();
                    found = true;
                    continue;
                }
            }

            token = token.substring(0, token.length() - 1);
        }

        if (indexId == null)
        {
            return rhymes;
        }

        rhymes.addAll(getSentencesFromIndex(indexId));
        return rhymes;
    }

    protected Set<String> getSentencesFromIndex(final String indexKey) throws IOException
    {
        Set<String> rhymes = new HashSet<String>();

        for (String key : redis.smembers(indexKey))
        {
            if (key.startsWith(indexns.toString()))
            {
                rhymes.addAll(getSentencesFromIndex(key));
            }
            else
            {
                // TODO no existeix
                rhymes.add(URLDecoder.decode(redis.get(key), encoding));
            }
        }

        return rhymes;
    }

    protected String getUniqueId(final Keymaker ns, final String token)
    {
        String md = sum(token);
        String id = redis.get(ns.build(md, "id").toString());

        if (id != null)
        {
            return id;
        }

        Integer next = redis.incr(ns.build("next.id").toString());
        id = next.toString();

        if (redis.setnx(ns.build(md, "id").toString(), id) == 0)
        {
            id = redis.get(ns.build(md, "id").toString());
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
        int lastSpace = sentence.lastIndexOf(" ");
        String lastWord = sentence.substring(lastSpace < 0 ? 0 : lastSpace + 1);
        String rhymepart = wordParser.getRhymeText(lastWord);

        LOGGER.debug("Finding rhymes ending with {}", rhymepart);

        Set<String> rhymes = search(rhymepart);
        return rhymes.isEmpty() ? DEFAULT_RHYME : rhymes.iterator().next();
    }
}
