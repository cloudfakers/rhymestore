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
 * Controller that delegates execution to a specific method based on the request path.
 * 
 * @author Ignasi Barrera
 */
public class MethodInvokingController implements Controller
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodInvokingController.class);

    /** List of errors produced during method execution. */
    private List<String> errors = new LinkedList<String>();

    @Override
    public String execute(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
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
            throw new ControllerException("Could not find a Controller method with name "
                + methodName + " in class " + this.getClass().getName());
        }

        // Execute the target method
        try
        {
            errors.clear();

            targetMethod.invoke(this, request, response);

            request.setAttribute("errors", errors);
        }
        catch (Exception ex)
        {
            if (ex.getCause() instanceof ControllerException)
            {
                // If it is a Controller exception, just propagate it
                throw (ControllerException) ex.getCause();
            }

            throw new ControllerException("Could not execute the Controller method " + methodName
                + " from class " + this.getClass().getName(), ex);
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
        return (String) request.getSession().getServletContext().getAttribute(
            ContextListener.TWITTER_USER_NAME);
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
}
