package com.rhymestore.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Basic MVC controller.
 * 
 * @author Ignasi Barrera
 */
public interface Controller
{
    /**
     * Executes the controller logic and returns a view.
     * 
     * @param request The request.
     * @param response The response.
     * @return The name of the view to render.
     * @throws ControllerException If an error occurs while executing controller logic.
     */
    public String execute(HttpServletRequest request, HttpServletResponse response)
        throws ControllerException;
}
