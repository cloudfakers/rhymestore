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

import redis.clients.jedis.Jedis;

public class RhymeStore
{
	public static final String DEFAULT_RHYME = "Patada en los cojones";

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

			String key = generateToken(words.get(words.size() - 1));
			String value = URLEncoder.encode(sentence, encoding);

			redis.connect();

			redis.sadd(namespace.build(key).toString(), value);

			redis.disconnect();
		}
	}

	// TODO don't use the KEYS command! Use a trie instead.
	public Set<String> search(String search) throws IOException
	{
		Set<String> rhyms = new HashSet<String>();

		search = "*".concat(generateToken(search));

		redis.connect();

		for (String key : redis.keys(namespace.build(search).toString()))
		{
			for (String sentence : redis.smembers(key))
			{
				rhyms.add(URLDecoder.decode(sentence, encoding));
			}
		}

		redis.disconnect();

		if (rhyms.isEmpty())
		{
			rhyms.add(DEFAULT_RHYME);
		}

		return rhyms;
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
