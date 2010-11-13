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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.config.RhymestoreConfig;
import com.rhymestore.web.controller.Controller;
import com.rhymestore.web.controller.ControllerException;

/**
 * Process requests performed from the Web UI.
 * 
 * @author Ignasi Barrera
 * 
 * @see Controller
 */
public class MVCServlet extends HttpServlet
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MVCServlet.class);

	/** Serial UID. */
	private static final long serialVersionUID = 1L;

	/** The view path. */
	private static final String VIEW_PATH = "/jsp";

	/** The view suffix. */
	private static final String VIEW_SUFFIX = ".jsp";

	/** Mappings from request path to {@link Controller} objects. */
	protected Map<String, Controller> controllers;

	/**
	 * Default constructor.
	 */
	public MVCServlet()
	{
		controllers = new HashMap<String, Controller>();
	}

	/**
	 * Initializes the servlet.
	 * 
	 * @throws If the servlet cannot be initialized.
	 */
	@Override
	public void init() throws ServletException
	{
		try
		{
			loadMappings();
		}
		catch (Exception ex)
		{
			throw new ServletException("Could not load controllers: "
					+ ex.getMessage(), ex);
		}
	}

	@Override
	protected void doGet(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException
	{
		String controllerPath = null;
		Controller controller = null;
		String requestedPath = getRequestedPath(req);

		for (String path : controllers.keySet())
		{
			if (requestedPath.startsWith(path))
			{
				controllerPath = path;
				controller = controllers.get(path);

				LOGGER.debug("Using {} controller to handle request to: {}",
						controller.getClass().getName(), req.getRequestURI());
			}
		}

		if (controller != null)
		{
			try
			{
				String viewName = controller.execute(req, resp);
				String viewPath = VIEW_PATH + controllerPath + "/" + viewName
						+ VIEW_SUFFIX;

				getServletContext().getRequestDispatcher(viewPath).forward(req,
						resp);
			}
			catch (ControllerException ex)
			{
				String errorMessage = "An error occured during request handling: "
						+ ex.getMessage();

				LOGGER.error(errorMessage, ex);

				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						errorMessage);
			}
		}
		else
		{
			resp.sendError(
					HttpServletResponse.SC_NOT_FOUND,
					"No controller was found to handle request to: "
							+ req.getRequestURI());
		}
	}

	/**
	 * Calls the API to store the Rhyme.
	 * 
	 * @param req The request.
	 * @param resp The response.
	 */
	@Override
	protected void doPost(final HttpServletRequest req,
			final HttpServletResponse resp) throws ServletException,
			IOException
	{
		doGet(req, resp);
	}

	/**
	 * Get the requested path relative to the servlet path.
	 * 
	 * @param req The request.
	 * @return The requested path.
	 */
	private String getRequestedPath(final HttpServletRequest req)
	{
		return req.getRequestURI().replaceFirst(req.getContextPath(), "")
				.replaceFirst(req.getServletPath(), "");
	}

	/**
	 * Load configured controller mappings.
	 * 
	 * @throws Exception If mappings cannot be loaded.
	 */
	protected void loadMappings() throws Exception
	{
		Properties mappings = new Properties();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		mappings.load(cl.getResourceAsStream(RhymestoreConfig.CONFIG_FILE));

		LOGGER.info("Loading controller mappings...");

		for (Object mappingKey : mappings.keySet())
		{
			String key = (String) mappingKey;

			if (RhymestoreConfig.isControllerProperty(key))
			{
				String path = mappings.getProperty(key);
				String clazz = mappings.getProperty(key.replace(
						RhymestoreConfig.CONTROLLER_PATH_SUFFIX,
						RhymestoreConfig.CONTROLLER_CLASS_SUFFIX));

				if (clazz == null)
				{
					throw new Exception("Missing controller class for path: "
							+ path);
				}

				Controller controller = (Controller) Class.forName(clazz, true,
						cl).newInstance();
				controllers.put(path, controller);

				LOGGER.info("Mapping {} to {}", path, controller.getClass()
						.getName());
			}
		}
	}
}
