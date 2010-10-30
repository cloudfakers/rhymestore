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

package com.rhymestore.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.OAuthAuthorization;
import twitter4j.http.RequestToken;

/**
 * Interactive method to generates the OAuth {@link AccessToken}.
 * 
 * @author Ignasi Barrera
 * 
 * @see Twitter
 * @see OAuthAuthorization
 */
public class AccessTokenGenerator
{
	/**
	 * Interactive {@link AccessToken} generation.
	 * 
	 * @param args No args are required.
	 * @throws Exception If the token cannot be generated.
	 */
	public static void main(final String... args) throws Exception
	{
		Twitter twitter = new TwitterFactory().getInstance();
		RequestToken requestToken = twitter.getOAuthRequestToken();

		// Ask for the PIN
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		AccessToken accessToken = null;

		System.out
				.print("Open the following URL and grant access to your account: ");
		System.out.println(requestToken.getAuthorizationURL());
		System.out.print("Enter the PIN: ");

		accessToken = twitter.getOAuthAccessToken(requestToken, br.readLine());

		System.out.println("AccessToken Key: " + accessToken.getToken());
		System.out.println("AccessToken Secret: "
				+ accessToken.getTokenSecret());

		twitter.shutdown();
	}
}
