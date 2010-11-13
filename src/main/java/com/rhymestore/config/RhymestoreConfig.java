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

package com.rhymestore.config;

import com.rhymestore.lang.WordParser;
import com.rhymestore.web.controller.Controller;

/**
 * Global application configuration.
 * 
 * @author Ignasi Barrera
 */
public class RhymestoreConfig
{
	/** The main configuration file. */
	public static final String CONFIG_FILE = "rhymestore.properties";

	/**
	 * Name of the property that holds the {@link WordParser} implementation
	 * class.
	 */
	public static final String WORDPARSER_PROPERTY = "wordparser.class";

	/** The prefix for controller mapping properties. */
	public static final String CONTROLLER_PREFIX = "controller.";

	/** The suffix for controller path mapping properties. */
	public static final String CONTROLLER_PATH_SUFFIX = ".path";

	/** The suffix for controller class mapping properties. */
	public static final String CONTROLLER_CLASS_SUFFIX = ".class";

	/**
	 * Checks if the given property defines a {@link Controller} mapping.
	 * 
	 * @param property The property to check.
	 * @return Boolean indicating if the given property defines a
	 *         <code>Controller</code> mapping.
	 */
	public static boolean isControllerProperty(String property)
	{
		return property.startsWith(CONTROLLER_PREFIX)
				&& property.endsWith(CONTROLLER_PATH_SUFFIX);
	}
}
