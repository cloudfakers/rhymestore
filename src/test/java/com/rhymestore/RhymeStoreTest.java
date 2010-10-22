package com.rhymestore;

import junit.framework.TestCase;

import com.rhymestore.store.RhymeStore;

// The tests assumes a redis server working in localhost
public class RhymeStoreTest extends TestCase
{
    public void testSearch()
    {
        RhymeStore r = RhymeStore.getInstance();

        try
        {
            r.add("Nada rima con dos.");
            r.add("Mi nabo para vos");
            r.add("Dame dos");

            assertEquals(r.search("dos").size(), 2);
            assertEquals(r.search("*os").size(), 3);
            assertEquals(r.search("v*").size(), 1);
        }
        catch (Exception e)
        {
            fail();
        }
    }
}
