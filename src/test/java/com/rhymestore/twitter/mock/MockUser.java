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

import java.net.URL;
import java.util.Date;

import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

/**
 * Mock class to simulate a {@link User} object in tests.
 * 
 * @author Ignasi Barrera
 */
public class MockUser implements User
{
    private static final long serialVersionUID = 1L;

    // Methods used in tests

    @Override
    public String getScreenName()
    {
        return "MockName";
    }

    // Unused methods

    @Override
    public Date getCreatedAt()
    {
        return null;
    }

    @Override
    public String getDescription()
    {
        return null;
    }

    @Override
    public int getFavouritesCount()
    {
        return 0;
    }

    @Override
    public int getFollowersCount()
    {
        return 0;
    }

    @Override
    public int getFriendsCount()
    {
        return 0;
    }

    @Override
    public long getId()
    {
        return 0;
    }

    @Override
    public String getLang()
    {
        return null;
    }

    @Override
    public int getListedCount()
    {
        return 0;
    }

    @Override
    public String getLocation()
    {
        return null;
    }

    @Override
    public String getName()
    {
        return null;
    }

    @Override
    public String getProfileBackgroundColor()
    {
        return null;
    }

    @Override
    public String getProfileBackgroundImageUrl()
    {
        return null;
    }

    @Override
    public String getProfileLinkColor()
    {
        return null;
    }

    @Override
    public String getProfileSidebarBorderColor()
    {
        return null;
    }

    @Override
    public String getProfileSidebarFillColor()
    {
        return null;
    }

    @Override
    public String getProfileTextColor()
    {
        return null;
    }

    @Override
    public Status getStatus()
    {
        return null;
    }

    @Override
    public int getStatusesCount()
    {
        return 0;
    }

    @Override
    public String getTimeZone()
    {
        return null;
    }

    @Override
    public int getUtcOffset()
    {
        return 0;
    }

    @Override
    public boolean isContributorsEnabled()
    {
        return false;
    }

    @Override
    public boolean isFollowRequestSent()
    {
        return false;
    }

    @Override
    public boolean isGeoEnabled()
    {
        return false;
    }

    @Override
    public boolean isProfileBackgroundTiled()
    {
        return false;
    }

    @Override
    public boolean isProfileUseBackgroundImage()
    {
        return false;
    }

    @Override
    public boolean isProtected()
    {
        return false;
    }

    @Override
    public boolean isShowAllInlineMedia()
    {
        return false;
    }

    @Override
    public boolean isTranslator()
    {
        return false;
    }

    @Override
    public boolean isVerified()
    {
        return false;
    }

    @Override
    public int compareTo(final User arg0)
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
    public String getProfileImageURL()
    {
        return null;
    }

    @Override
    public String getBiggerProfileImageURL()
    {
        return null;
    }

    @Override
    public String getMiniProfileImageURL()
    {
        return null;
    }

    @Override
    public String getOriginalProfileImageURL()
    {
        return null;
    }

    @Override
    public URL getProfileImageUrlHttps()
    {
        return null;
    }

    @Override
    public String getProfileImageURLHttps()
    {
        return null;
    }

    @Override
    public String getBiggerProfileImageURLHttps()
    {
        return null;
    }

    @Override
    public String getMiniProfileImageURLHttps()
    {
        return null;
    }

    @Override
    public String getOriginalProfileImageURLHttps()
    {
        return null;
    }

    @Override
    public String getURL()
    {
        return null;
    }

    @Override
    public String getProfileBackgroundImageURL()
    {
        return null;
    }

    @Override
    public String getProfileBackgroundImageUrlHttps()
    {
        return null;
    }

    @Override
    public String getProfileBannerURL()
    {
        return null;
    }

    @Override
    public String getProfileBannerRetinaURL()
    {
        return null;
    }

    @Override
    public String getProfileBannerIPadURL()
    {
        return null;
    }

    @Override
    public String getProfileBannerIPadRetinaURL()
    {
        return null;
    }

    @Override
    public String getProfileBannerMobileURL()
    {
        return null;
    }

    @Override
    public String getProfileBannerMobileRetinaURL()
    {
        return null;
    }

    @Override
    public URLEntity[] getDescriptionURLEntities()
    {
        return null;
    }

    @Override
    public URLEntity getURLEntity()
    {
        return null;
    }

}
