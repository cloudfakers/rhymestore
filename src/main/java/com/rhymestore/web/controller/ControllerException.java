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

/**
 * Represents an exception thrown during {@link Controller} execution.
 * 
 * @author Ignasi Barrera
 */
public class ControllerException extends Exception
{
	/** Serial UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new <code>ControllerException</code> with the given message and
	 * cause.
	 * 
	 * @param msg The exception message.
	 * @param cause The exception cause.
	 */
	public ControllerException(final String msg, final Throwable cause)
	{
		super(msg, cause);
	}

	/**
	 * Creates a new <code>ControllerException</code> with the given message.
	 * 
	 * @param msg The exception message.
	 */
	public ControllerException(final String msg)
	{
		super(msg);
	}

	/**
	 * Creates a new <code>ControllerException</code> with the given cause.
	 * 
	 * @param cause The exception cause.
	 */
	public ControllerException(final Throwable cause)
	{
		super(cause);
	}

}
