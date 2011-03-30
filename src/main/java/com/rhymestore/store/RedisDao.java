package com.rhymestore.store;

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
     * 
     * @param targetClass The class of the object managed by the DAO.
     */
    public RedisDao(Class<T> targetClass)
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
        return JOhm.get(targetClass, id);
    }

    /**
     * Get all the objects of the managed type.
     * 
     * @return The list with all the objects.
     */
    public List<T> findAll()
    {
        Set<T> objects = JOhm.getAll(targetClass);
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
        return JOhm.save(object, true);
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
