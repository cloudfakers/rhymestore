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

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sjmvc.controller.ControllerException;
import org.sjmvc.controller.MethodInvokingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.lang.WordUtils;
import com.rhymestore.model.Rhyme;
import com.rhymestore.store.RhymeStore;
import com.rhymestore.twitter.util.TwitterUtils;
import com.rhymestore.web.ContextListener;

/**
 * Controller to manage stored rhymes.
 * 
 * @author Ignasi Barrera
 */
public class RhymeController extends MethodInvokingController
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymeController.class);
    
    /** The Rhyme store. */
    private final RhymeStore store;

    /**
     * Default constructor.
     */
    public RhymeController()
    {
        store = RhymeStore.getInstance();
    }

    /**
     * Lists all rhymes in the the store.
     * 
     * @param request The request.
     * @param response The response.
     * @throws ControllerException If the rhyme cannot be added.
     */
    public void list(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        try
        {
            Set<String> rhymes = store.findAll();

            List<String> sortedRhymes = new ArrayList<String>(rhymes);
            Collections.sort(sortedRhymes, String.CASE_INSENSITIVE_ORDER);

            setModel(sortedRhymes);
        }
        catch (Exception ex)
        {
            error("Could not get rhymes: " + ex.getMessage());
        }
    }

    /**
     * Check if there is a rhyme submitted, and adds it to the store.
     * 
     * @param request The request.
     * @param response The response.
     * @throws ControllerException If the rhyme cannot be added.
     */
    public void add(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        Rhyme rhyme = new Rhyme();
        bindAndValidate(rhyme, request);

        // Add the rhyme only if there are no binding or validation errors
        if (!errors())
        {
            String twitterUser = getTwitterUser(request, response);
            if (twitterUser != null && rhyme.getRhyme().contains(TwitterUtils.user(twitterUser)))
            {
                error("Cannot add a rhyme that contains the Twitter user name");
            }

            if (!errors())
            {
                try
                {
                    String capitalized = WordUtils.capitalize(rhyme.getRhyme());
                    store.add(capitalized);
                }
                catch (Exception ex)
                {
                    error("Could not add rhyme: " + ex.getMessage());
                }
            }
        }

        // Load the new list of rhymes to render the list view
        list(request, response);
        setView("list");
    }

    /**
     * Check if there is a rhyme submitted, and deletes it from the store.
     * 
     * @param request The request.
     * @param response The response.
     * @throws ControllerException If the rhyme cannot be deleted.
     */
    public void delete(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        Rhyme rhyme = new Rhyme();
        bindAndValidate(rhyme, request);

        if (!errors())
        {
            try
            {
                String capitalized = WordUtils.capitalize(rhyme.getRhyme());
                store.delete(capitalized);
            }
            catch (Exception ex)
            {
                error("Could not delete rhyme: " + ex.getMessage());
            }
        }

        // Load the new list of rhymes to render the list view
        list(request, response);
        setView("list");
    }
    
    /**
     * Download all the stored rhymes in a text file..
     * 
     * @param request The request.
     * @param response The response.
     * @throws ControllerException If the rhymes cannot be downloaded.
     */
    public void download(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        try
        {
            // Get all rhymes
            Set<String> rhymes = store.findAll();
            List<String> sortedRhymes = new ArrayList<String>(rhymes);
            Collections.sort(sortedRhymes, String.CASE_INSENSITIVE_ORDER);
            
            LOGGER.info("Exporting {} rhymes...", sortedRhymes.size());
            
            // Configure the response to generate an attachment
            response.setContentType("text/plain");
            response.setHeader("Content-Disposition", "attachment; filename=rhymes.txt");
            
            // Write output
            PrintWriter pw = new PrintWriter(response.getOutputStream());

            for (String rhyme : sortedRhymes)
            {
                pw.println(rhyme);
            }

            pw.flush();
            pw.close();
        }
        catch (Exception ex)
        {
            error("Could not get rhymes: " + ex.getMessage());
            setView("list");
        }
    }

    /**
     * Gets the Twitter user.
     * 
     * @param request The request.
     * @param response The response.
     * @return The Twitter user name.
     */
    private String getTwitterUser(final HttpServletRequest request,
        final HttpServletResponse response)
    {
        return (String) request.getSession().getServletContext().getAttribute(
            ContextListener.TWITTER_USER_NAME);
    }

}
