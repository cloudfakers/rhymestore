package com.rhymestore.store;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// The tests assumes a redis server working in localhost
public class RhymeStoreTest
{
    private RhymeStore store;

    @BeforeMethod
    public void setUp()
    {
        store = RhymeStore.getInstance();
    }

    @Test
    public void testSearch() throws Exception
    {
        store.add("Nada rima con dos.");
        store.add("Mi nabo para vos");
        store.add("Dame dos");

        assertEquals(store.search("dos").size(), 2);
        assertEquals(store.search("os").size(), 3);
        assertEquals(store.search("vos").size(), 1);
        assertEquals(store.search("v").size(), 0);
    }

}
