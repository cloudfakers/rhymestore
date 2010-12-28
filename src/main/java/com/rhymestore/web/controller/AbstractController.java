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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Base class for {@link Controller} implementations.
 * 
 * @author Ignasi Barrera
 */
public abstract class AbstractController implements Controller
{
    /** The attribute name where the controller errors will be published. */
    public static final String ERRORS_ATTRIBUTE = "errors";

    /** List of errors produced during method execution. */
    private final List<String> errors = new LinkedList<String>();

    /** The view to return. */
    private String returnView;

    /** The model objects used to render the view. */
    private final Map<String, Object> model = new HashMap<String, Object>();

    /**
     * Internal method to implement the controller logic.
     * <p>
     * This method returns void and expects this method to set the view to render by calling the
     * {@link #setView(String)} method.
     * <p>
     * The model must be also populated using the {@link #addModel(String, Object)} method in order
     * to let the views render properly.
     * 
     * @param request The request.
     * @param response The response.
     * @throws Exception If an exception occurs during controller logic execution.
     */
    protected abstract void doExecute(final HttpServletRequest request,
        final HttpServletResponse response) throws Exception;

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        // Clear previous data
        model.clear();
        errors.clear();
        returnView = null;

        try
        {
            // Execute controller logic
            doExecute(request, response);

            if (returnView == null)
            {
                throw new Exception("There was no view set. "
                    + "Use the setView method to set the view to render");
            }
        }
        catch (Exception ex)
        {
            if (ex.getCause() instanceof ControllerException)
            {
                // If it is a Controller exception, just propagate it
                throw (ControllerException) ex.getCause();
            }

            throw new ControllerException("Could not execute the Controller logic at "
                + this.getClass().getName(), ex.getCause() == null ? ex : ex.getCause());
        }

        // Populate model
        for (String key : model.keySet())
        {
            request.setAttribute(key, model.get(key));
        }

        // Populate controlled errors
        request.setAttribute(ERRORS_ATTRIBUTE, errors);

        return returnView;
    }

    /**
     * Add the given object to the model.
     * 
     * @param modelName The name used to publish the model object to the view.
     * @param modelObject The model object to publish to the view.
     */
    protected void addModel(String modelName, Object modelObject)
    {
        model.put(modelName, modelObject);
    }

    /**
     * Adds the given error to the {@link #errors} list.
     * 
     * @param error The error to add.
     */
    protected void error(final String error)
    {
        errors.add(error);
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

    /**
     * Set the view to be returned.
     * 
     * @param viewName The name of the view to be returned.
     */
    protected void setView(String viewName)
    {
        returnView = viewName;
    }

    /**
     * Get the view returned by the controller.
     * 
     * @return The name of the view returned by the controller.
     */
    protected String getView()
    {
        return returnView;
    }
}
