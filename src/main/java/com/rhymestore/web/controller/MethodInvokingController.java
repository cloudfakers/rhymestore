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

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.web.ContextListener;

/**
 * Controller that delegates execution to a specific method based on the request
 * path.
 * 
 * @author Ignasi Barrera
 */
public class MethodInvokingController implements Controller
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MethodInvokingController.class);

	/** The attribute name where the controller errors will be published. */
	public static final String ERRORS_ATTRIBUTE = "errors";

	/** List of errors produced during method execution. */
	private final List<String> errors = new LinkedList<String>();

	@Override
	public String execute(final HttpServletRequest request,
			final HttpServletResponse response) throws ControllerException
	{
		// Get the name of the method
		int lastSlash = request.getRequestURI().lastIndexOf("/");
		String methodName = request.getRequestURI().substring(lastSlash + 1);

		// Find the target method
		Method targetMethod = null;

		for (Method method : this.getClass().getMethods())
		{
			if (method.getName().equals(methodName))
			{
				targetMethod = method;
				break;
			}
		}

		if (targetMethod == null)
		{
			String message = "Could not find a Controller method with name "
					+ methodName + " in class " + this.getClass().getName();

			throw new ControllerException(message, new NoSuchMethodException(
					message));
		}

		// Execute the target method
		try
		{
			errors.clear();

			targetMethod.invoke(this, request, response);

			request.setAttribute(ERRORS_ATTRIBUTE, errors);
		}
		catch (Exception ex)
		{
			if (ex.getCause() instanceof ControllerException)
			{
				// If it is a Controller exception, just propagate it
				throw (ControllerException) ex.getCause();
			}

			throw new ControllerException(
					"Could not execute the Controller method " + methodName
							+ " from class " + this.getClass().getName(),
					ex.getCause() == null ? ex : ex.getCause());
		}

		// The view name is the same than the method
		return methodName;
	}

	/**
	 * Gets the Twitter user.
	 * 
	 * @param request The request.
	 * @param response The response.
	 * @return The Twitter user name.
	 */
	protected String getTwitterUser(final HttpServletRequest request,
			final HttpServletResponse response)
	{
		return (String) request.getSession().getServletContext()
				.getAttribute(ContextListener.TWITTER_USER_NAME);
	}

	/**
	 * Adds the given error to the {@link #errors} list.
	 * 
	 * @param error The error to add.
	 */
	protected void error(final String error)
	{
		errors.add(error);
		LOGGER.error(error);
	}

	/**
	 * Adds the given error to the {@link #errors} list.
	 * 
	 * @param error The error to add.
	 * @param cause The error cause.
	 */
	protected void error(final String error, final Exception cause)
	{
		errors.add(error);
		LOGGER.error(error, cause);
	}

	/**
	 * Checks if there are any errors.
	 * 
	 * @return Boolean indicating if there are any errors.
	 */
	protected boolean errors()
	{
		return !errors.isEmpty();
	}
}
