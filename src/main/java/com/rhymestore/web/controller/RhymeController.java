package com.rhymestore.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rhymestore.store.RhymeStore;

/**
 * Controller to manage stored rhymes.
 * 
 * @author Ignasi Barrera
 */
public class RhymeController extends MethodInvokingController
{
    /** Name of the attribute that will hold the operation result. */
    public static final String RESULT_ATTR = "RhymeController.Result";

    /** The name of the parameter used to submit rhymes. */
    public static final String RHYME_PARAM = "rhyme";

    /** The Rhyme store. */
    private RhymeStore store;

    /**
     * Default constructor
     */
    public RhymeController()
    {
        store = RhymeStore.getInstance();
    }

    /**
     * Adds a new rhyme to the store.
     * 
     * @param request The request.
     * @param response The response.
     * @throws ControllerException If the rhyme cannot be added.
     */
    public void add(final HttpServletRequest request, final HttpServletResponse response)
        throws ControllerException
    {
        String result = null;
        String rhyme = request.getParameter(RHYME_PARAM);

        if (rhyme != null && rhyme.length() > 0)
        {
            try
            {
                store.add(rhyme);
                result = "Added rhyme: " + rhyme;
            }
            catch (IOException ex)
            {
                result = "Could not add rhyme: " + ex.getMessage();
            }

            request.setAttribute(RESULT_ATTR, result);
        }
    }

}
