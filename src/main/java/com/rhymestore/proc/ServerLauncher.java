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

package com.rhymestore.proc;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.security.HashUserRealm;
import org.mortbay.jetty.security.UserRealm;
import org.mortbay.jetty.webapp.WebAppContext;

/**
 * Web application process.
 * <p>
 * Starts the Jetty Server that will be exposed.
 * 
 * @author Ignasi Barrera
 */
public class ServerLauncher
{
    public static void main(final String[] args) throws Exception
    {
        String webappDirLocation = "src/main/webapp/";

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty())
        {
            webPort = "8080";
        }

        // Web application
        WebAppContext webappContext = new WebAppContext();
        webappContext.setContextPath("/");
        webappContext.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        webappContext.setResourceBase(webappDirLocation);
        webappContext.setParentLoaderPriority(true);

        HashUserRealm userRealm = new HashUserRealm("Basic Authentication");

        userRealm.put("admin", System.getenv("ADMINPASS"));
        userRealm.addUserToRole("admin", "rhymestore-rw");

        Server server = new Server(Integer.valueOf(webPort));

        server.setHandler(webappContext);
        server.setUserRealms(new UserRealm[] {userRealm});

        server.start();
        server.join();
    }
}
