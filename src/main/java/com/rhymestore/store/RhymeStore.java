package com.rhymestore.store;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

public class RhymeStore
{
    private final Jedis redis;

    private final Keymaker namespace = new Keymaker("rhyme");

    private final String encoding = "UTF-8";

    public static RhymeStore instance;

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

            String key = generateToken(words.get(words.size() - 1), true);
            String value = URLEncoder.encode(sentence, encoding);

            redis.connect();

            redis.sadd(namespace.build(key).toString(), value);

            redis.disconnect();
        }
    }

    public Set<String> search(String search) throws IOException
    {
        Set<String> rhyms = new HashSet<String>();

        search = generateToken(search, false);

        redis.connect();

        for (String key : redis.keys(namespace.build(search).toString()))
        {
            for (String sentence : redis.smembers(key))
            {
                rhyms.add(URLDecoder.decode(sentence, encoding));
            }
        }

        redis.disconnect();

        return rhyms;
    }

    private String generateToken(final String value, final boolean removeWildcards)
    {
        // To lower case
        String token = value.toLowerCase();

        // Replace accent letters
        token = token.replaceAll("[����]", "a");
        token = token.replaceAll("[����]", "e");
        token = token.replaceAll("[����]", "i");
        token = token.replaceAll("[����]", "o");
        token = token.replaceAll("[����]", "u");

        // Remove non alphanumeric characters
        if (removeWildcards)
        {
            token = token.replaceAll("[^a-zA-Z0-9]", "");
        }
        else
        {
            token = token.replaceAll("[^a-zA-Z0-9\\[\\]\\*\\?]", "");
        }

        return token;
    }
}
