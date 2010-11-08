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

package com.rhymestore.web;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

/**
 * Unit tests for the {@link DownloadServlet} class.
 * 
 * @author Ignasi Barrera
 */
public class DownloadServletTest
{
    /** The base path used for web requests. */
    private static final String DOWNLOAD_PATH = "http://rhymestore.com/rhymestore/download";

    /** The servlet client used to perform unit tests. */
    private ServletUnitClient servletClient;

    @BeforeMethod
    public void setUp()
    {
        ServletRunner servletRunner = new ServletRunner();
        servletRunner.registerServlet("rhymestore/download", DownloadServlet.class.getName());
        servletClient = servletRunner.newClient();
    }

    @Test
    public void testDownload() throws Exception
    {
        WebRequest request = new PostMethodWebRequest(DOWNLOAD_PATH);
        WebResponse response = servletClient.getResponse(request);

        assertEquals(response.getContentType(), DownloadServlet.DOWNLOAD_CONTENT_TYPE);
        assertEquals(response.getHeaderField("Content-Disposition"),
            DownloadServlet.ATTACHMENT_HEADER);
    }
}
