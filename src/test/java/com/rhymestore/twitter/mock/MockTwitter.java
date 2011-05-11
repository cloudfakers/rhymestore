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
import java.util.Date;
import java.util.List;

import twitter4j.AccountSettings;
import twitter4j.AccountTotals;
import twitter4j.Category;
import twitter4j.DirectMessage;
import twitter4j.Friendship;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.IDs;
import twitter4j.Location;
import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Place;
import twitter4j.ProfileImage;
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
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.ProfileImage.ImageSize;
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
    public Status updateStatus(StatusUpdate latestStatus) throws TwitterException
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
    public AccessToken getOAuthAccessToken() throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String oauthVerifier) throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken) throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(RequestToken requestToken, String oauthVerifier)
        throws TwitterException
    {
        return null;
    }

    @Override
    public AccessToken getOAuthAccessToken(String screenName, String password)
        throws TwitterException
    {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken() throws TwitterException
    {
        return null;
    }

    @Override
    public RequestToken getOAuthRequestToken(String callbackURL) throws TwitterException
    {
        return null;
    }

    @Override
    public void setOAuthAccessToken(AccessToken accessToken)
    {

    }

    @Override
    public void setOAuthConsumer(String consumerKey, String consumerSecret)
    {

    }

    @Override
    public void addRateLimitStatusListener(RateLimitStatusListener listener)
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
    public long getId() throws TwitterException, IllegalStateException
    {
        return 0;
    }

    @Override
    public String getScreenName() throws TwitterException, IllegalStateException
    {
        return null;
    }

    @Override
    public void shutdown()
    {

    }

    @Override
    public QueryResult search(Query query) throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getCurrentTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getCurrentTrends(boolean excludeHashTags) throws TwitterException
    {
        return null;
    }

    @Override
    public List<Trends> getDailyTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public List<Trends> getDailyTrends(Date date, boolean excludeHashTags) throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public List<Trends> getWeeklyTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public List<Trends> getWeeklyTrends(Date date, boolean excludeHashTags) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFriendsTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFriendsTimeline(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getHomeTimeline(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getMentions() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getMentions(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getPublicTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedByMe() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedByMe(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedByUser(String screenName, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedByUser(long userId, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedToMe() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedToMe(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedToUser(String screenName, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetedToUser(long userId, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweetsOfMe(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(String screenName, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserTimeline(long userId, Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public Status destroyStatus(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getRetweetedBy(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getRetweetedByIDs(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getRetweets(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public Status retweetStatus(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public Status showStatus(long id) throws TwitterException
    {
        return null;
    }

    @Override
    public Status updateStatus(String status) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersStatuses(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersStatuses(String screenName, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFollowersStatuses(long userId, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsStatuses(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsStatuses(String screenName, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getFriendsStatuses(long userId, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getMemberSuggestions(String categorySlug) throws TwitterException
    {
        return null;
    }

    @Override
    public ProfileImage getProfileImage(String screenName, ImageSize size) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Category> getSuggestedUserCategories() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getUserSuggestions(String categorySlug) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> lookupUsers(long[] ids) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> searchUsers(String query, int page) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUser(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User showUser(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList createUserList(String listName, boolean isPublicList, String description)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList destroyUserList(int listId) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<UserList> getAllUserLists(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<UserList> getAllUserLists(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListMemberships(String listMemberScreenName,
        long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(String listOwnerScreenName, int id,
        Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getUserListStatuses(long listOwnerId, int id, Paging paging)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserListSubscriptions(String listOwnerScreenName,
        long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<UserList> getUserLists(String listOwnerScreenName, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList showUserList(String listOwnerScreenName, int id) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList updateUserList(int listId, String newListName, boolean isPublicList,
        String newDescription) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMember(int listId, long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(int listId, long[] userIds) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList addUserListMembers(int listId, String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public User checkUserListMembership(String listOwnerScreenName, int listId, long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList deleteUserListMember(int listId, long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(String listOwnerScreenName, int listId,
        long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListMembers(long listOwnerId, int listId, long cursor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User checkUserListSubscription(String listOwnerScreenName, int listId, long userId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public PagableResponseList<User> getUserListSubscribers(String listOwnerScreenName, int listId,
        long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public UserList subscribeUserList(String listOwnerScreenName, int listId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public UserList unsubscribeUserList(String listOwnerScreenName, int listId)
        throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage destroyDirectMessage(long id) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getDirectMessages(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<DirectMessage> getSentDirectMessages(Paging paging) throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(String screenName, String text) throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage sendDirectMessage(long userId, String text) throws TwitterException
    {
        return null;
    }

    @Override
    public DirectMessage showDirectMessage(long id) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(String screenName, boolean follow) throws TwitterException
    {
        return null;
    }

    @Override
    public User createFriendship(long userId, boolean follow) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyFriendship(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyFriendship(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public boolean existsFriendship(String userA, String userB) throws TwitterException
    {
        return false;
    }

    @Override
    public IDs getIncomingFriendships(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getOutgoingFriendships(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(String[] screenNames) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Friendship> lookupFriendships(long[] ids) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship showFriendship(String sourceScreenName, String targetScreenName)
        throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship showFriendship(long sourceId, long targetId) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship updateFriendship(String screenName, boolean enableDeviceNotification,
        boolean retweets) throws TwitterException
    {
        return null;
    }

    @Override
    public Relationship updateFriendship(long userId, boolean enableDeviceNotification,
        boolean retweets) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(long userId, long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFollowersIDs(String screenName, long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(long userId, long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getFriendsIDs(String screenName, long cursor) throws TwitterException
    {
        return null;
    }

    @Override
    public AccountSettings getAccountSettings() throws TwitterException
    {
        return null;
    }

    @Override
    public AccountTotals getAccountTotals() throws TwitterException
    {
        return null;
    }

    @Override
    public RateLimitStatus getRateLimitStatus() throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfile(String name, String url, String location, String description)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(File image, boolean tile) throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileBackgroundImage(InputStream image, boolean tile)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileColors(String profileBackgroundColor, String profileTextColor,
        String profileLinkColor, String profileSidebarFillColor, String profileSidebarBorderColor)
        throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileImage(File image) throws TwitterException
    {
        return null;
    }

    @Override
    public User updateProfileImage(InputStream image) throws TwitterException
    {
        return null;
    }

    @Override
    public User verifyCredentials() throws TwitterException
    {
        return null;
    }

    @Override
    public Status createFavorite(long id) throws TwitterException
    {
        return null;
    }

    @Override
    public Status destroyFavorite(long id) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(int page) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(String id) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Status> getFavorites(String id, int page) throws TwitterException
    {
        return null;
    }

    @Override
    public User disableNotification(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User disableNotification(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User enableNotification(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User enableNotification(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User createBlock(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User createBlock(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyBlock(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public User destroyBlock(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public boolean existsBlock(String screenName) throws TwitterException
    {
        return false;
    }

    @Override
    public boolean existsBlock(long userId) throws TwitterException
    {
        return false;
    }

    @Override
    public ResponseList<User> getBlockingUsers() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<User> getBlockingUsers(int page) throws TwitterException
    {
        return null;
    }

    @Override
    public IDs getBlockingUsersIDs() throws TwitterException
    {
        return null;
    }

    @Override
    public User reportSpam(long userId) throws TwitterException
    {
        return null;
    }

    @Override
    public User reportSpam(String screenName) throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch createSavedSearch(String query) throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch destroySavedSearch(int id) throws TwitterException
    {
        return null;
    }

    @Override
    public List<SavedSearch> getSavedSearches() throws TwitterException
    {
        return null;
    }

    @Override
    public SavedSearch showSavedSearch(int id) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Location> getAvailableTrends() throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Location> getAvailableTrends(GeoLocation location) throws TwitterException
    {
        return null;
    }

    @Override
    public Trends getLocationTrends(int woeid) throws TwitterException
    {
        return null;
    }

    @Override
    public Place createPlace(String name, String containedWithin, String token,
        GeoLocation location, String streetAddress) throws TwitterException
    {
        return null;
    }

    @Override
    public Place getGeoDetails(String id) throws TwitterException
    {
        return null;
    }

    @Override
    public SimilarPlaces getSimilarPlaces(GeoLocation location, String name,
        String containedWithin, String streetAddress) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Place> reverseGeoCode(GeoQuery query) throws TwitterException
    {
        return null;
    }

    @Override
    public ResponseList<Place> searchPlaces(GeoQuery query) throws TwitterException
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
    public RelatedResults getRelatedResults(long statusId) throws TwitterException
    {
        return null;
    }

    @Override
    public boolean test() throws TwitterException
    {
        return false;
    }

}
