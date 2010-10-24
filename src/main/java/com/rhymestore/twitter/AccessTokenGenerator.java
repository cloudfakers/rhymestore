package com.rhymestore.twitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

/**
 * Interactive method to generates the OAuth {@link AccessToken}.
 * 
 * @author Ignasi Barrera
 * 
 */
public class AccessTokenGenerator
{

	/**
	 * Interactive {@link AccessToken} generation.
	 * 
	 * @param args No args are required.
	 * @throws Exception If the token cannot be generated.
	 */
	public static void main(String... args) throws Exception
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
	}
}
