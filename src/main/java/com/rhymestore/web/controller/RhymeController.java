package com.rhymestore.web.controller;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.store.RhymeStore;

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
    private RhymeStore store;

    /**
     * Default constructor
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
            request.setAttribute("rhymes", rhymes);
        }
        catch (IOException ex)
        {
            String result = "Could get rhymes: " + ex.getMessage();
            LOGGER.error(result);
            request.setAttribute("result", result);
        }
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
        String rhyme = request.getParameter("rhyme");

        if (rhyme != null && rhyme.length() > 0)
        {
            try
            {
                store.add(rhyme);
                result = "Added rhyme: " + rhyme;

                LOGGER.info(result);
            }
            catch (IOException ex)
            {
                result = "Could not add rhyme: " + ex.getMessage();
                LOGGER.error(result);
            }

            request.setAttribute("result", result);
        }
    }

}
