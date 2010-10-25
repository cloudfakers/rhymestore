package com.rhymestore.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.web.controller.Controller;
import com.rhymestore.web.controller.ControllerException;
import com.rhymestore.web.controller.RhymeController;

/**
 * Process requests performed from the Web UI.
 * 
 * @author ibarrera
 */
public class RhymeServlet extends HttpServlet
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RhymeServlet.class);

    /** Serial UID. */
    private static final long serialVersionUID = 1L;

    /** The view path. */
    private static final String VIEW_PATH = "/jsp";

    /** The view suffix. */
    private static final String VIEW_SUFFIX = ".jsp";

    /** Mappings from request path to {@link Controller} objects. */
    private Map<String, Controller> controllers;

    /**
     * Initializes the servlet.
     * 
     * @throws If the servlet cannog be initialized.
     */
    @Override
    public void init() throws ServletException
    {
        controllers = new HashMap<String, Controller>();

        // Register controllers
        register("/rhymes", new RhymeController());
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException
    {
        String controllerPath = null;
        Controller controller = null;
        String requestedPath = getRequestedPath(req);

        for (String path : controllers.keySet())
        {
            if (requestedPath.startsWith(path))
            {
                controllerPath = path;
                controller = controllers.get(path);

                LOGGER.debug("Using {} controller to handle request to: {}", controller.getClass()
                    .getName(), req.getRequestURI());
            }
        }

        if (controller != null)
        {
            try
            {
                String viewName = controller.execute(req, resp);
                String viewPath = VIEW_PATH + controllerPath + "/" + viewName + VIEW_SUFFIX;

                getServletContext().getRequestDispatcher(viewPath).forward(req, resp);
            }
            catch (ControllerException e)
            {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An error occured dirung request handling: " + e.getMessage());
            }
        }
        else
        {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                "No controller was found to handle request to: " + req.getRequestURI());
        }
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
        doGet(req, resp);
    }

    /**
     * Get the requested path relative to the servlet path.
     * 
     * @param req The request.
     * @return The requested path.
     */
    private String getRequestedPath(final HttpServletRequest req)
    {
        return req.getRequestURI().replaceFirst(req.getContextPath(), "").replaceFirst(
            req.getServletPath(), "");

    }

    /**
     * Registers the given controller to handle requests to the given path.
     * 
     * @param path The path.
     * @param controller The controller.
     */
    private void register(final String path, final Controller controller)
    {
        LOGGER.info("Mapping {} to {}", path, controller.getClass().getName());

        controllers.put(path, controller);
    }
}
