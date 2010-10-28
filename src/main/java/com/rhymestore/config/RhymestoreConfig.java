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

package com.rhymestore.config;

import com.rhymestore.lang.WordParser;
import com.rhymestore.web.controller.Controller;

/**
 * Global application configuration.
 * 
 * @author Ignasi Barrera
 * 
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
