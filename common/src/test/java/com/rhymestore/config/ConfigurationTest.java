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

import static com.rhymestore.config.Configuration.REDIS_HOST;
import static com.rhymestore.config.Configuration.getConfigValue;
import static com.rhymestore.config.Configuration.getRequiredConfigValue;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * Unit tests for the {@link Configuration} class.
 * 
 * @author Ignasi Barrera
 */
public class ConfigurationTest
{
    @Test
    public void testGetConfigValue()
    {
        assertNull(getConfigValue("unexisting"));
        assertEquals(getConfigValue(REDIS_HOST), "localhost");
    }

    @Test
    public void testGetRequiredConfigValue()
    {
        assertEquals(getRequiredConfigValue(REDIS_HOST), "localhost");
    }

    @Test(expectedExceptions = ConfigurationException.class)
    public void testGetUnexistingRequiredConfigValue()
    {
        getRequiredConfigValue("unexisting");
    }
}
