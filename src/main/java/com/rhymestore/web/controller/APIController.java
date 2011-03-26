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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sjmvc.controller.Controller;

import com.rhymestore.lang.WordUtils;

/**
 * Controller to handle API calls.
 * 
 * @author Ignasi Barrera
 * @see RhymeController
 */
public class APIController extends HttpMethodController
{
    /** Controller to delegate execution to. */
    private final RhymeController rhymeController;

    /**
     * Creates the API {@link Controller}.
     */
    public APIController()
    {
        super();
        rhymeController = new RhymeController();
    }

    /**
     * Handles GET requests to the mapped resource.
     * 
     * @param request The request.
     * @param response The response.
     * @throws Exception If an error occurs during request processing.
     */
    public void get(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception
    {
        rhymeController.list(request, response);
        setModel(rhymeController.getModel());
        checkErrors(request, response);
    }

    /**
     * Handles POST requests to the mapped resource.
     * 
     * @param request The request.
     * @param response The response.
     * @throws Exception If an error occurs during request processing.
     */
    public void post(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception
    {
        rhymeController.add(request, response);

        String rhyme = request.getParameter("model.rhyme");
        String capitalized = WordUtils.capitalize(rhyme);

        setModel(capitalized);
        checkErrors(request, response);
    }

    /**
     * Handles DELETE requests to the mapped resource.
     * 
     * @param request The request.
     * @param response The response.
     * @throws Exception If an error occurs during request processing.
     */
    public void delete(final HttpServletRequest request, final HttpServletResponse response)
        throws Exception
    {
        rhymeController.delete(request, response);

        String rhyme = request.getParameter("model.rhyme");
        String capitalized = WordUtils.capitalize(rhyme);

        setModel(capitalized);
        checkErrors(request, response);
    }

    /**
     * Configures the view to be loaded.
     * 
     * @param request The request.
     * @param response The response.
     */
    private void checkErrors(final HttpServletRequest request, final HttpServletResponse response)
    {
        if (rhymeController.errors())
        {
            errors.addAll(rhymeController.getErrors());
            setView("errors");
        }
    }
}
