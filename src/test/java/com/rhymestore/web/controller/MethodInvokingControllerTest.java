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

package com.rhymestore.web.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Unit tests for the {@link MethodInvokingController} class.
 * 
 * @author Ignasi Barrera
 */
public class MethodInvokingControllerTest
{
	/** The base path used for web requests. */
	private static final String BASE_PATH = "http://rhymestore.com/rhymestore/web/mock";

	/** The controller being tested. */
	private MethodInvokingController controller;

	/** The servlet client used to perform unit tests. */
	private ServletUnitClient servletClient;

	@BeforeMethod
	public void setUp()
	{
		controller = new MockController();
		servletClient = new ServletRunner().newClient();
	}

	@Test
	public void testUnexistingMethod() throws Exception
	{
		InvocationContext ic = servletClient.newInvocation(BASE_PATH
				+ "/unexisting");

		checkControllerException(ic, NoSuchMethodException.class);
		assertFalse(controller.errors());
	}

	@Test
	public void testSuccessMethod() throws Exception
	{
		InvocationContext ic = servletClient.newInvocation(BASE_PATH
				+ "/success");
		controller.execute(ic.getRequest(), ic.getResponse());

		assertFalse(controller.errors());
	}

	@Test
	public void testFailMethod() throws Exception
	{
		InvocationContext ic = servletClient.newInvocation(BASE_PATH + "/fail");

		checkControllerException(ic, UnsupportedOperationException.class);
		assertFalse(controller.errors());
	}

	@Test
	public void testInvalidArgumentsMethod() throws Exception
	{
		InvocationContext ic = servletClient.newInvocation(BASE_PATH
				+ "/invalidArguments");

		checkControllerException(ic, IllegalArgumentException.class);
		assertFalse(controller.errors());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMethodWithErrors() throws Exception
	{
		InvocationContext ic = servletClient.newInvocation(BASE_PATH
				+ "/addError");
		controller.execute(ic.getRequest(), ic.getResponse());

		List<String> errors = (List<String>) ic.getRequest().getAttribute(
				MethodInvokingController.ERRORS_ATTRIBUTE);

		assertTrue(controller.errors());
		assertTrue(errors != null);
		assertEquals(errors.size(), 1);
	}

	private void checkControllerException(InvocationContext ic,
			Class<? extends Throwable> exceptionClass)
	{
		try
		{
			controller.execute(ic.getRequest(), ic.getResponse());
			fail("Expected Exception: " + exceptionClass.getName());
		}
		catch (ControllerException ex)
		{
			assertEquals(ex.getCause().getClass(), exceptionClass);
		}
	}
}
