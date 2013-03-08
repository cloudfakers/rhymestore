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

package com.rhymestore.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global application configuration.
 * 
 * @author Ignasi Barrera
 */
public class Configuration
{
    /** The logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

    /** The main configuration file. */
    public static final String CONFIG_FILE = "rhymestore.properties";

    /** Name of the property that holds the Redis host. */
    public static final String REDIS_HOST = "rhymestore.redis.host";

    /** Name of the property that holds the Redis port. */
    public static final String REDIS_PORT = "rhymestore.redis.port";

    /** Name of the property that holds the default rhymes to use. */
    public static final String DEFAULT_RHYMES = "rhymestore.wordparser.default";

    /** The singleton instance of the configuration object. */
    private static Configuration instance;

    /** The configuration properties. */
    private Properties properties;

    /**
     * Private constructor. This class should ot be instantiated.
     */
    private Configuration()
    {
        super();
    }

    /**
     * Gets the configuration properties.
     * 
     * @return The configuration properties.
     */
    /* package */static Properties getConfiguration()
    {
        if (instance == null)
        {
            instance = new Configuration();

            LOGGER.debug("Loading configuration from {}", CONFIG_FILE);

            // Load properties
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            instance.properties = new Properties();

            try
            {
                instance.properties.load(cl.getResourceAsStream(CONFIG_FILE));
            }
            catch (Exception ex)
            {
                throw new ConfigurationException("Could not load configuration file: "
                    + ex.getMessage());
            }

            LOGGER.debug("Loaded {} configuration properties", instance.properties.size());
        }

        return instance.properties;
    }

    /**
     * Get the configuration value for the given property name.
     * 
     * @return The value for the given property.
     * @throws ConfigurationException If the property does not exist.
     */
    /* package */static String getRequiredConfigValue(final String propertyName)
    {
        String value = getConfiguration().getProperty(propertyName);

        if (value == null)
        {
            throw new ConfigurationException("Te requested property [" + propertyName
                + "] was not set.");
        }

        return value;
    }

    /**
     * Get the configuration value for the given property name.
     * 
     * @return The value for the given property or <code>null</code> if the value is not defined.
     */
    /* package */static String getConfigValue(final String propertyName)
    {
        return getConfiguration().getProperty(propertyName);
    }

    /**
     * Load Twitter configuration.
     */
    /* package */static void loadTwitterConfig()
    {
        System.setProperty("twitter4j.oauth.consumerKey", System.getenv("TWITTER_CONSUMERKEY"));
        System.setProperty("twitter4j.oauth.consumerSecret",
            System.getenv("TWITTER_CONSUMERSECRET"));
        System.setProperty("twitter4j.oauth.accessToken", System.getenv("TWITTER_ACCESSTOKEN"));
        System.setProperty("twitter4j.oauth.accessTokenSecret",
            System.getenv("TWITTER_ACCESSTOKENSECRET"));
    }

}
