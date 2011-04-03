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

package com.rhymestore.store.dao;

import com.rhymestore.config.Configuration;
import com.rhymestore.config.ConfigurationException;

import redis.clients.jedis.JedisPool;
import redis.clients.johm.JOhm;

/**
 * Factory class used to create the {@link RedisDao}.
 * 
 * @author Ignasi Barrera
 */
public class RedisDaoFactory
{
    /** The Redis database connection pool. */
    private static JedisPool jedisPool;

    /**
     * Get the {@link RedisDao} for the given object class.
     * 
     * @param <T> The type of the object managed by the DAO.
     * @param targetClass The class of the object managed by the DAO.
     * @return The <code>RedisDAO</code> for the given object class.
     */
    public static <T> RedisDao<T> getDAO(Class<T> targetClass)
    {
        // Initialize the pool only once
        if (jedisPool == null)
        {
            try
            {
                String host = Configuration.getConfigValue(Configuration.REDIS_HOST_PROPERTY);
                String port = Configuration.getConfigValue(Configuration.REDIS_PORT_PROPERTY);

                jedisPool = new JedisPool(host, Integer.valueOf(port));
                JOhm.setPool(jedisPool);
            }
            catch (Exception ex)
            {
                throw new ConfigurationException("Could not initialize the Redis pool", ex);
            }
        }

        // Return a new DAO with the singleton Redis pool
        return new RedisDao<T>(targetClass);
    }
}
