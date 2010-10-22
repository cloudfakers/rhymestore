package com.rhymestore.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rhymestore.store.RhymeStore;

/**
 * Process requests performed from the Web UI.
 * 
 * @author ibarrera
 */
public class RhymeServlet extends HttpServlet
{
    /** Serial UID. */
    private static final long serialVersionUID = 1L;

    /** Name of the attribute that will hold the operation result. */
    public static final String RESULT_ATTR = "RhymeServlet.Result";

    /** The name of the parameter used to submit rhymes. */
    public static final String RHYME_PARAM = "rhyme";

    /** The Rhyme form JSP. */
    private static final String RHYME_JSP = "/jsp/rhymestore.jsp";

    /** The Rhyme store. */
    private RhymeStore store;

    /**
     * Initializes the servlet.
     * 
     * @throws If the servlet cannog be initialized.
     */
    @Override
    public void init() throws ServletException
    {
        store = RhymeStore.getInstance();
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException
    {
        getServletContext().getRequestDispatcher(RHYME_JSP).forward(req, resp);
    }

    /**
     * Calls the API to store the Rhyme.
     * 
     * @param req The request.
     * @param resp The response.
     */
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException
    {
        String result = null;
        String rhyme = req.getParameter(RHYME_PARAM);

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

            req.setAttribute(RESULT_ATTR, result);
        }

        getServletContext().getRequestDispatcher(RHYME_JSP).forward(req, resp);
    }
}
