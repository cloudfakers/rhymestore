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

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller that delegates execution to a specific method based on the request path.
 * 
 * @author Ignasi Barrera
 */
public class MethodInvokingController extends AbstractController
{

    @Override
    public void doExecute(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception
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
            String message =
                "Could not find a Controller method with name " + methodName + " in class "
                    + this.getClass().getName();

            throw new ControllerException(message, new NoSuchMethodException(message));
        }

        // Set the default view to return
        setView(methodName);

        // Execute the target method (parent class will handle exceptions)
        targetMethod.invoke(this, request, response);
    }

}
