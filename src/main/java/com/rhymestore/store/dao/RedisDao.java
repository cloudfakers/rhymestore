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

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import redis.clients.johm.JOhm;

/**
 * Generic Redis DAO.
 * 
 * @author Ignasi Barrera
 * @param <T> The type of the object managed by the DAO.
 * @see JOhm
 */
public class RedisDao<T>
{
    /** The target class of the objects managed by the DAO. */
    private Class<T> targetClass;

    /**
     * Create the DAO for the given class.
     * <p>
     * The constructor is package protected and should not be used. Use the
     * {@link RedisDaoFactory#getDAO(Class)} method instead.
     * 
     * @param targetClass The class of the object managed by the DAO.
     * @param The host where the Redis database is running.
     * @param The port where the Redis database is running.
     */
    RedisDao(Class<T> targetClass)
    {
        super();
        this.targetClass = targetClass;
    }

    /**
     * Get an object given its id.
     * 
     * @param id The id of the object.
     * @return The object or <code>null</code> if the object does not exist.
     */
    public T get(Long id)
    {
        return JOhm.<T> get(targetClass, id);
    }

    /**
     * Get all the objects of the managed type.
     * 
     * @return The list with all the objects.
     */
    public List<T> findAll()
    {
        Set<T> objects = JOhm.<T> getAll(targetClass);
        return new LinkedList<T>(objects);
    }

    /**
     * Save the given object in the Redis store.
     * 
     * @param object The object to save.
     * @return The saved object with the <code>id</code> field set.
     */
    public T save(T object)
    {
        return JOhm.<T> save(object, true);
    }

    /**
     * Delete the object with the given id.
     * 
     * @param id The id of the object to delete.
     */
    public void delete(Long id)
    {
        JOhm.delete(targetClass, id);
    }

}
