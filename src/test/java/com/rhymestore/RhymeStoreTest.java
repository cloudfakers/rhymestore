package com.rhymestore;

import junit.framework.TestCase;

import com.rhymestore.store.RhymeStore;

// The tests assumes a redis server working in localhost
public class RhymeStoreTest extends TestCase
{
    public void testSearch()
    {
        RhymeStore store = RhymeStore.getInstance();

        try
        {
            store.add("Nada rima con dos.");
            store.add("Mi nabo para vos");
            store.add("Dame dos");

            assertEquals(store.search("dos").size(), 2);
            assertEquals(store.search("os").size(), 3);
            assertEquals(store.search("vos").size(), 1);
            assertEquals(store.search("v").size(), 0);
        }
        catch (Exception e)
        {
            fail();
        }
    }
}
