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
     * Creates a new <code>ControllerException</code> with the given message and cause.
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
