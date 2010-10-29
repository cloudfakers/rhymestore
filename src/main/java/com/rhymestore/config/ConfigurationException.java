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

/**
 * Represents an exception thrown due to configuration errors.
 * 
 * @author Ignasi Barrera
 */
public class ConfigurationException extends RuntimeException
{
	/** Serial UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new <code>ConfigurationException</code> with the given message
	 * and cause.
	 * 
	 * @param msg The exception message.
	 * @param cause The exception cause.
	 */
	public ConfigurationException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	/**
	 * Creates a new <code>ConfigurationException</code> with the given message.
	 * 
	 * @param msg The exception message.
	 */
	public ConfigurationException(final String msg)
	{
		super(msg);
	}

	/**
	 * Creates a new <code>ConfigurationException</code> with the given cause.
	 * 
	 * @param cause The exception cause.
	 */
	public ConfigurationException(final Throwable cause)
	{
		super(cause);
	}

}
