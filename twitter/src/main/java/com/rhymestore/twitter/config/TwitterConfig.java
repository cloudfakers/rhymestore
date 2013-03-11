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

package com.rhymestore.twitter.config;

import twitter4j.Twitter;
import twitter4j.TwitterStream;

import com.google.common.base.Supplier;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.rhymestore.config.RhymeStore;

/**
 * Provides static access to the Twitter configuration.
 * 
 * @author Ignasi Barrera
 */
public class TwitterConfig
{
    public static Twitter getTwitter()
    {
        return RhymeStore.get(Twitter.class);
    }

    public static TwitterStream getTwitterStream()
    {
        return RhymeStore.get(TwitterStream.class);
    }

    public static Supplier<String> getTwitterUser()
    {
        TypeLiteral<Supplier<String>> type = new TypeLiteral<Supplier<String>>()
        {
        };

        return RhymeStore.get(Key.get(type));
    }
}
