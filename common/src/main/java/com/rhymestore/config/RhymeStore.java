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

import com.google.common.base.Optional;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.rhymestore.lang.WordParser;
import com.rhymestore.store.RedisStore;
import com.rhymestore.store.RhymeLoader;

/**
 * Entry point to the RhymeStore application.
 * 
 * @author Ignasi Barrera
 */
public class RhymeStore
{
    /** The singleton instance. */
    private static RhymeStore instance;

    /** Access to the IoC container. */
    private Injector injector;

    /**
     * Get the configured {@link WordParser}.
     */
    public static WordParser getWordParser()
    {
        return get(WordParser.class);
    }

    /**
     * Get the configured {@link RedisStore}.
     */
    public static RedisStore getRedisStore()
    {
        return get(RedisStore.class);
    }

    /**
     * Get the configured {@link RhymeLoader}.
     */
    public static RhymeLoader getRhymeLoader()
    {
        return get(RhymeLoader.class);
    }

    /**
     * Get the configured URI for the default rhymes.
     */
    public static Optional<String> getDefaultRhymesUri()
    {
        TypeLiteral<Optional<String>> type = new TypeLiteral<Optional<String>>()
        {
        };

        return get(Key.get(type, Names.named(DEFAULT_RHYMES)));
    }

    /**
     * Get a configured instance for the given class.
     */
    public static <T> T get(final Class<T> clazz)
    {
        return getInstance().injector.getInstance(clazz);
    }

    /**
     * Get a configured instance for the given injection key.
     */
    public static <T> T get(final Key<T> key)
    {
        return getInstance().injector.getInstance(key);
    }

    private RhymeStore(final Module... modules)
    {
        injector = Guice.createInjector(Stage.PRODUCTION, modules);
    }

    private static RhymeStore getInstance()
    {
        if (instance == null)
        {
            throw new IllegalStateException("RhymeStore has not been initialized");
        }
        return instance;
    }

    /**
     * Initialize the injector with the given modules.
     */
    public static RhymeStore create(final Module... modules)
    {
        if (instance == null)
        {
            instance = new RhymeStore(modules);
        }
        return instance;
    }
}
