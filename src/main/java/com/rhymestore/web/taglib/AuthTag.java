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

package com.rhymestore.web.taglib;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Taglib to perform authentication checks.
 * 
 * @author Ignasi Barrera
 */
public class AuthTag extends TagSupport
{
    /** Serial UID. */
    private static final long serialVersionUID = 1L;

    /** The list of roles, separated by ','. This variable will be set in the JSP. */
    private String roles;

    @Override
    public int doStartTag() throws JspException
    {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

        String[] roleList = roles.split(",");

        for (String element : roleList)
        {
            if (request.isUserInRole(element))
            {
                return EVAL_BODY_INCLUDE;
            }
        }

        return SKIP_BODY;
    }

    // Getters and setters

    public String getRoles()
    {
        return roles;
    }

    public void setRoles(final String roles)
    {
        this.roles = roles;
    }

}
