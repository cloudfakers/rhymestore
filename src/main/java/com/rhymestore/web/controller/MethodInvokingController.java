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
