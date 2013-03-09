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

import java.io.File;
import java.io.InputStream;
import java.util.Map;

import twitter4j.AccountSettings;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.OEmbed;
import twitter4j.OEmbedRequest;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.RateLimitStatusListener;
import twitter4j.RelatedResults;
import twitter4j.Relationship;
import twitter4j.ResponseList;
import twitter4j.SavedSearch;
import twitter4j.SimilarPlaces;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterAPIConfiguration;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.auth.AccessToken;
import twitter4j.auth.Authorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;

/**
 * Mock class to simulate a {@link Twitter} object in tests.
 * 
 * @author Ignasi Barrera
 */
public class MockTwitter implements Twitter
{
    private static final long serialVersionUID = 1L;

    private String lastUpdatedStatus;

    // Methods used in tests

    @Override
    public Status updateStatus(final StatusUpdate latestStatus) throws TwitterException
    {
        lastUpdatedStatus = latestStatus.getStatus();
        return null;
    }

    public String getLastUpdatedStatus()
    {
        return lastUpdatedStatus;
    }

    // Unused methods

    @Override
    public void setOAuthConsumer(final String consumerKey, final String consumerSecret)
    {

    }

    @Override
    public RequestToken getOAuthRequestToken() throws TwitterException
    {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(final String callbackURL) throws TwitterException
    {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(final String callbackURL, final String xAuthAccessType)
        throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken() throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(final String oauthVerifier) throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(final RequestToken requestToken) throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(final RequestToken requestToken,
        final String oauthVerifier) throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(final String screenName, final String password)
        throws TwitterException
    {
        return null;
    }

    @Override
    public void setOAuthAccessToken(final AccessToken accessToken)
    {

    }

    @Override
    public String getScreenName() throws TwitterException, IllegalStateException
    {
        return null;
    }

    @Override
    public long getId() throws TwitterException, IllegalStateException
    {
        return 0;
    }

    @Override
    public void addRateLimitStatusListener(final RateLimitStatusListener listener)
    {

    }

    @Override
    public Authorization getAuthorization()
    {
        return null;
    }

    @Override
    public Configuration getConfiguration()
    {
        return null;
    }

    @Override
    public void shutdown()
    {

    }

    @Override
    public ResponseList<Status> getMentions() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getMentionsTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getMentions(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getMentionsTimeline(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(final String screenName, final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(final long userId, final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweets(final long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public Status showStatus(final long id) throws TwitterException
    {
        return null;
    }

    @Override
    public Status destroyStatus(final long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public Status updateStatus(final String status) throws TwitterException
    {
        return null;
    }

    @Override
    public Status retweetStatus(final long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public OEmbed getOEmbed(final OEmbedRequest req) throws TwitterException
    {
        return null;
    }

    @Override
    public QueryResult search(final Query query) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages(final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages(final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage showDirectMessage(final long id) throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage destroyDirectMessage(final long id) throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(final long userId, final String text)
        throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(final String screenName, final String text)
        throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(final long userId, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(final String screenName, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(final long userId, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(final String screenName, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(final long[] ids) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(final String[] screenNames)
        throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getIncomingFriendships(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getOutgoingFriendships(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(final long userId, final boolean follow) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(final String screenName, final boolean follow)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyFriendship(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyFriendship(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship updateFriendship(final long userId, final boolean enableDeviceNotification,
        final boolean retweets) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship updateFriendship(final String screenName,
        final boolean enableDeviceNotification, final boolean retweets) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship showFriendship(final long sourceId, final long targetId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship showFriendship(final String sourceScreenName, final String targetScreenName)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(final long userId, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsList(final String screenName, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(final long userId, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersList(final String screenName, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public AccountSettings getAccountSettings() throws TwitterException
    {
        return null;
    }

    @Override
    public User verifyCredentials() throws TwitterException
    {
        return null;
    }

    @Override
    public AccountSettings updateAccountSettings(final Integer trendLocationWoeid,
        final Boolean sleepTimeEnabled, final String startSleepTime, final String endSleepTime,
        final String timeZone, final String lang) throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfile(final String name, final String url, final String location,
        final String description) throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(final File image, final boolean tile)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(final InputStream image, final boolean tile)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileColors(final String profileBackgroundColor,
        final String profileTextColor, final String profileLinkColor,
        final String profileSidebarFillColor, final String profileSidebarBorderColor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileImage(final File image) throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileImage(final InputStream image) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getBlocksList() throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getBlocksList(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getBlocksIDs() throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getBlocksIDs(final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public User createBlock(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User createBlock(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyBlock(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyBlock(final String screen_name) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(final long[] ids) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(final String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUser(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUser(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> searchUsers(final String query, final int page)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getContributees(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getContributees(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getContributors(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getContributors(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public void removeProfileBanner() throws TwitterException
    {

    }

    @Override
    public void updateProfileBanner(final File image) throws TwitterException
    {

    }

    @Override
    public void updateProfileBanner(final InputStream image) throws TwitterException
    {

    }

    @Override
    public ResponseList<User> getUserSuggestions(final String categorySlug) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Category> getSuggestedUserCategories() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getMemberSuggestions(final String categorySlug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(final long userId, final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(final String screenName, final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public Status createFavorite(final long id) throws TwitterException
    {
        return null;
    }

    @Override
    public Status destroyFavorite(final long id) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(final String listOwnerScreenName)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<UserList> getUserLists(final long listOwnerUserId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(final int listId, final Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(final long ownerId, final String slug,
        final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(final String ownerScreenName,
        final String slug, final Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListMember(final int listId, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList deleteUserListMember(final int listId, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListMember(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList deleteUserListMember(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListMember(final String ownerScreenName, final String slug,
        final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(final long listMemberId,
        final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(final String listMemberScreenName,
        final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(final String listMemberScreenName,
        final long cursor, final boolean filterToOwnedLists) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(final long listMemberId,
        final long cursor, final boolean filterToOwnedLists) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(final int listId, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(final long ownerId, final String slug,
        final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(final String ownerScreenName,
        final String slug, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListSubscription(final int listId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListSubscription(final long ownerId, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListSubscription(final String ownerScreenName, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListSubscription(final int listId, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListSubscription(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListSubscription(final String ownerScreenName, final String slug,
        final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(final int listId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(final long ownerId, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserListSubscription(final String ownerScreenName, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final int listId, final long[] userIds)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(final int listId, final long[] userIds)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final long ownerId, final String slug,
        final long[] userIds) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(final long ownerId, final String slug, final long[] userIds)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final String ownerScreenName, final String slug,
        final long[] userIds) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final int listId, final String[] screenNames)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(final int listId, final String[] screenNames)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final long ownerId, final String slug,
        final String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(final long ownerId, final String slug,
        final String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMembers(final String ownerScreenName, final String slug,
        final String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListMembership(final int listId, final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListMembership(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User showUserListMembership(final String ownerScreenName, final String slug,
        final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(final int listId, final long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(final long ownerId, final String slug,
        final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(final String ownerScreenName,
        final String slug, final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMember(final int listId, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMember(final int listId, final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMember(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMember(final long ownerId, final String slug, final long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserListMember(final String ownerScreenName, final String slug,
        final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserList(final int listId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserList(final long ownerId, final String slug) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserList(final String ownerScreenName, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList updateUserList(final int listId, final String newListName,
        final boolean isPublicList, final String newDescription) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList updateUserList(final long ownerId, final String slug, final String newListName,
        final boolean isPublicList, final String newDescription) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList updateUserList(final String ownerScreenName, final String slug,
        final String newListName, final boolean isPublicList, final String newDescription)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserList(final String listName, final boolean isPublicList,
        final String description) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList showUserList(final int listId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList showUserList(final long ownerId, final String slug) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList showUserList(final String ownerScreenName, final String slug)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(final String listOwnerScreenName,
        final long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<SavedSearch> getSavedSearches() throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch showSavedSearch(final int id) throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch createSavedSearch(final String query) throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch destroySavedSearch(final int id) throws TwitterException
    {
        return null;
    }

    @Override
    public Place getGeoDetails(final String placeId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Place> reverseGeoCode(final GeoQuery query) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Place> searchPlaces(final GeoQuery query) throws TwitterException
    {
        return null;
    }

    @Override
    public SimilarPlaces getSimilarPlaces(final GeoLocation location, final String name,
        final String containedWithin, final String streetAddress) throws TwitterException
    {
        return null;
    }

    @Override
    public Place createPlace(final String name, final String containedWithin, final String token,
        final GeoLocation location, final String streetAddress) throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getLocationTrends(final int woeid) throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getPlaceTrends(final int woeid) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Location> getAvailableTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Location> getAvailableTrends(final GeoLocation location)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Location> getClosestTrends(final GeoLocation location)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User reportSpam(final long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User reportSpam(final String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public TwitterAPIConfiguration getAPIConfiguration() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Language> getLanguages() throws TwitterException
    {
        return null;
    }

    @Override
    public String getPrivacyPolicy() throws TwitterException
    {
        return null;
    }

    @Override
    public String getTermsOfService() throws TwitterException
    {
        return null;
    }

    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus() throws TwitterException
    {
        return null;
    }

    @Override
    public Map<String, RateLimitStatus> getRateLimitStatus(final String... resources)
        throws TwitterException
    {
        return null;
    }

    @Override
    public RelatedResults getRelatedResults(final long statusId) throws TwitterException
    {
        return null;
    }
}
