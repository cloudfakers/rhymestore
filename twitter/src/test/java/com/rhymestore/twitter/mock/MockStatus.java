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

package com.rhymestore.twitter.mock;

import java.util.Date;

import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/**
 * Mock class to simulate a {@link Status} object in tests.
 * 
 * @author Ignasi Barrera
 */
public class MockStatus implements Status
{
    private static final long serialVersionUID = 1L;

    /** The status text. */
    private final String text;

    // Methods used in tests

    public MockStatus(final String text)
    {
        super();
        this.text = text;
    }

    @Override
    public User getUser()
    {
        return new MockUser();
    }

    @Override
    public String getText()
    {
        return text;
    }

    @Override
    public long getId()
    {
        return 0;
    }

    // Unused methods

    @Override
    public Date getCreatedAt()
    {
        return null;
    }

    @Override
    public GeoLocation getGeoLocation()
    {
        return null;
    }

    @Override
    public HashtagEntity[] getHashtagEntities()
    {
        return null;
    }

    @Override
    public String getInReplyToScreenName()
    {
        return null;
    }

    @Override
    public long getInReplyToStatusId()
    {
        return 0;
    }

    @Override
    public long getInReplyToUserId()
    {
        return 0;
    }

    @Override
    public Place getPlace()
    {
        return null;
    }

    @Override
    public Status getRetweetedStatus()
    {
        return null;
    }

    @Override
    public String getSource()
    {
        return null;
    }

    @Override
    public URLEntity[] getURLEntities()
    {
        return null;
    }

    @Override
    public UserMentionEntity[] getUserMentionEntities()
    {
        return null;
    }

    @Override
    public boolean isFavorited()
    {
        return false;
    }

    @Override
    public boolean isRetweet()
    {
        return false;
    }

    @Override
    public boolean isRetweetedByMe()
    {
        return false;
    }

    @Override
    public boolean isTruncated()
    {
        return false;
    }

    @Override
    public int compareTo(final Status arg0)
    {
        return 0;
    }

    @Override
    public RateLimitStatus getRateLimitStatus()
    {
        return null;
    }

    @Override
    public int getAccessLevel()
    {
        return 0;
    }

    @Override
    public MediaEntity[] getMediaEntities()
    {
        return null;
    }

    @Override
    public long[] getContributors()
    {
        return null;
    }

    @Override
    public long getCurrentUserRetweetId()
    {
        return 0;
    }

    @Override
    public boolean isPossiblySensitive()
    {
        return false;
    }

    @Override
    public SymbolEntity[] getSymbolEntities()
    {
        return null;
    }

    @Override
    public boolean isRetweeted()
    {
        return false;
    }

    @Override
    public int getFavoriteCount()
    {
        return 0;
    }

    @Override
    public int getRetweetCount()
    {
        return 0;
    }

    @Override
    public String getLang()
    {
        return null;
    }

    @Override
    public Scopes getScopes()
    {
        return null;
    }

}
