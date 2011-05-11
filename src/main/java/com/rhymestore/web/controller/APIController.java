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

import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordParserFactory;
import com.rhymestore.model.Rhyme;
import com.rhymestore.store.RhymeStore;

/**
 * Controller to handle API calls.
 * 
 * @author Ignasi Barrera
 * @see RhymeController
 */
public class APIController extends HttpMethodController
{
    /** The Rhyme store. */
    private final RhymeStore store;

    /** The {@link WordParser} used to get the default rhyme if none is found. */
    private final WordParser wordParser;

    /**
     * Creates the API {@link Controller}.
     */
    public APIController()
    {
        store = RhymeStore.getInstance();
        wordParser = WordParserFactory.getWordParser();
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
        Rhyme rhyme = new Rhyme();
        bindAndValidate(rhyme, request);

        if (!errors())
        {
            try
            {
                String rhymeResponse = store.getRhyme(rhyme.getRhyme());

                if (rhymeResponse == null)
                {
                    rhymeResponse = wordParser.getDefaultRhyme();
                }

                setModel(rhymeResponse);
            }
            catch (Exception ex)
            {
                error("Could not get rhyme: " + ex.getMessage());
            }
        }

        if (errors())
        {
            setView("errors");
        }

        response.setContentType("text/xml; charset=ISO-8859-1");
    }
}
