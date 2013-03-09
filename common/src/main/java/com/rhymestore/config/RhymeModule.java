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

package com.rhymestore.config;

import static com.rhymestore.config.Configuration.DEFAULT_RHYMES;
import static com.rhymestore.config.Configuration.REDIS_HOST;
import static com.rhymestore.config.Configuration.REDIS_PORT;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import redis.clients.jedis.Jedis;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.es.SpanishWordParser;
import com.rhymestore.store.Keymaker;

/**
 * Configures the rhyme parsing and redis store access.
 * 
 * @author Ignasi Barrera
 */
public class RhymeModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bindProperties();
        bindWordParser();
        bindRedisNamespaces();
    }

    protected void bindProperties()
    {
        bind(String.class).annotatedWith(Names.named(REDIS_HOST)).toInstance(
            Configuration.getRequiredConfigValue(Configuration.REDIS_HOST));
        bind(Integer.class).annotatedWith(Names.named(REDIS_PORT)).toInstance(
            Integer.valueOf(Configuration.getRequiredConfigValue(Configuration.REDIS_PORT)));
    }

    protected void bindWordParser()
    {
        bind(WordParser.class).to(SpanishWordParser.class).in(Singleton.class);
    }

    private void bindRedisNamespaces()
    {
        bind(Keymaker.class).annotatedWith(Names.named("sentence")).toInstance(
            new Keymaker("sentence"));
        bind(Keymaker.class).annotatedWith(Names.named("index")).toInstance(new Keymaker("index"));
    }

    @Provides
    @Singleton
    @Named(DEFAULT_RHYMES)
    public List<String> provideDefaultRhymes()
    {
        List<String> defaultRhymes = new ArrayList<String>();

        for (Object prop : Configuration.getConfiguration().keySet())
        {
            String propertyName = (String) prop;
            if (propertyName.startsWith(DEFAULT_RHYMES))
            {
                defaultRhymes.add(Configuration.getRequiredConfigValue(propertyName));
            }
        }

        return defaultRhymes;
    }

    @Provides
    @Singleton
    public Jedis provideJedis(@Named(REDIS_HOST) final String redisHost,
        @Named(REDIS_PORT) final Integer redisPort)
    {
        return new Jedis(redisHost, redisPort);
    }
}
