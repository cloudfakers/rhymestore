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

package com.rhymestore.twitter;

import static com.rhymestore.twitter.util.TwitterUtils.reply;
import static com.rhymestore.twitter.util.TwitterUtils.tweet;
import static com.rhymestore.twitter.util.TwitterUtils.user;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import com.rhymestore.twitter.util.TwitterUtils;

/**
 * Unit tests for the {@link TwitterUtils} class.
 * 
 * @author Ignasi Barrera
 */
public class TwitterUtilsTest
{
	@Test
	public void testTweet()
	{
		assertEquals(tweet(""), "");
		assertEquals(tweet("hola"), "hola");
		assertEquals(
				tweet("esto es un tweet de prueba para ver si funciona el test unitario."
						+ " El tweet tiene más de 140 carácteres, será recortado por la aplicación"
						+ " y se añadirán tres carácteres."),
				"esto es un tweet de prueba para ver si funciona el test unitario."
						+ " El tweet tiene más de 140 carácteres, será recortado por la aplicación ...");
	}

	@Test
	public void testUser()
	{
		assertEquals(user(""), "@");
		assertEquals(user("rimamelo"), "@rimamelo");
		assertEquals(user("IgnasiBarrera"), "@IgnasiBarrera");
		assertEquals(user("hola adios"), "@hola adios");
	}

	@Test
	public void testReply()
	{
		assertEquals(reply("", ""), "@ ");
		assertEquals(reply("rimamelo", ""), "@rimamelo ");
		assertEquals(reply("rimamelo", "que tal"), "@rimamelo que tal");
		assertEquals(
				reply("rimamelo",
						"esto es un tweet de prueba para ver si funciona el test unitario."
								+ " El tweet tiene más de 140 carácteres, será recortado por la aplicación"
								+ " y se añadirán tres carácteres."),
				"@rimamelo esto es un tweet de prueba para ver si funciona el test unitario."
						+ " El tweet tiene más de 140 carácteres, será recortado por la a...");
	}
}
