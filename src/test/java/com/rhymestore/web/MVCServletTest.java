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

package com.rhymestore.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import javax.servlet.http.HttpServletResponse;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.meterware.httpunit.HttpException;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Unit tests for the {@link MVCServlet} class.
 * <p>
 * To test the loading of mapped views, a full servlet container is required.
 * During test execution JSPs are not compiled, and will not exist in the
 * classpath, so the rendering of mapped views needs to be tested in an
 * integration test.
 * 
 * @author Ignasi Barrera
 */
public class MVCServletTest
{
	/** The base path used for web requests. */
	private static final String BASE_PATH = "http://rhymestore.com/rhymestore/web";

	/** The servlet being tested. */
	private MVCServlet mvcServlet;

	/** The servlet client used to perform unit tests. */
	private ServletUnitClient servletClient;

	@BeforeMethod
	public void setUp()
	{
		mvcServlet = new MVCServlet();

		ServletRunner servletRunner = new ServletRunner();
		servletRunner.registerServlet("rhymestore/web/*",
				MVCServlet.class.getName());
		servletClient = servletRunner.newClient();
	}

	@Test
	public void testLoadMappings() throws Exception
	{
		mvcServlet.loadMappings();

		assertTrue(mvcServlet.controllers != null);
		assertEquals(mvcServlet.controllers.size(), 2);
	}

	@Test
	public void testHandleUnmappedRequest() throws Exception
	{
		WebRequest request = new PostMethodWebRequest(BASE_PATH
				+ "/unmapped/test");
		HttpException ex = checkReponseError(request,
				HttpServletResponse.SC_NOT_FOUND);

		// Ensure MVC servlet has not tried to load the view
		assertFalse(ex.getMessage().contains(".jsp"));
	}

	@Test
	public void testHandleMappedWithoutView() throws Exception
	{
		WebRequest request = new PostMethodWebRequest(BASE_PATH
				+ "/mock/success");
		HttpException ex = checkReponseError(request,
				HttpServletResponse.SC_NOT_FOUND);

		// Ensure MVC servlet has tried to load the view
		assertTrue(ex.getMessage().contains("success.jsp"));
	}

	@Test
	public void testControllerError() throws Exception
	{
		WebRequest request1 = new PostMethodWebRequest(BASE_PATH + "/mock/fail");
		WebRequest request2 = new PostMethodWebRequest(BASE_PATH
				+ "/mock/invalidArguments");

		checkReponseError(request1,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		checkReponseError(request2,
				HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	private HttpException checkReponseError(WebRequest request, int expectedCode)
	{
		try
		{
			servletClient.getResponse(request);
			fail("Expected Response error: " + expectedCode);
			return null;
		}
		catch (Exception ex)
		{
			assertTrue(ex instanceof HttpException);
			assertEquals(((HttpException) ex).getResponseCode(), expectedCode);

			return (HttpException) ex;
		}
	}
}
