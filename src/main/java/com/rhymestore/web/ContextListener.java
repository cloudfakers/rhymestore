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

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.config.RhymeModule;
import com.rhymestore.config.RhymeStore;
import com.rhymestore.util.SSLUtils;

/**
 * Initializes and shuts down the twitter scheduler.
 * 
 * @author Ignasi Barrera
 */
public class ContextListener implements ServletContextListener
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextListener.class);

    @Override
    public void contextInitialized(final ServletContextEvent sce)
    {
        // Initialize the IoC. No need for Twitter module here
        RhymeStore.create(new RhymeModule());

        // Load the rhymes URI
        String rhymesURI = System.getenv("DEFAULT_RHYMES");

        if (rhymesURI != null)
        {
            LOGGER.info("Adding rhymes from: {}", rhymesURI);

            try
            {
                // Ensure there won't be SSL certificate issues
                SSLUtils.installIgnoreCertTrustManager();
                URL url = new URL(rhymesURI);
                URLConnection conn = url.openConnection();

                // Load the rhymes from the configured URI
                RhymeStore.getRhymeLoader().load(conn.getInputStream());
            }
            catch (Exception ex)
            {
                LOGGER.error("Could not load the default rhymes: " + ex.getMessage(), ex);
            }

        }
    }

    @Override
    public void contextDestroyed(final ServletContextEvent sce)
    {

    }

}
