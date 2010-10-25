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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that delegates execution to a specific method based on the request path.
 * 
 * @author Ignasi Barrera
 */
public class MethodInvokingController implements Controller
{

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
            targetMethod.invoke(this, request, response);
        }
        catch (Exception e)
        {
            if (e.getCause() instanceof ControllerException)
            {
                // IF it is a Controller exception, just propagate it
                throw (ControllerException) e.getCause();
            }

            throw new ControllerException("Could not execute the Controller method " + methodName
                + " from class " + this.getClass().getName());
        }

        // The view name is the same than the method
        return methodName;
    }
}
