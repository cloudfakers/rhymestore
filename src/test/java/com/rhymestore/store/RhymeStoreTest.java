package com.rhymestore.store;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// The tests assumes a redis server working in localhost
public class RhymeStoreTest
{
    private RhymeStore store;

    @BeforeMethod
    public void setUp() throws Exception
    {
        store = RhymeStore.getInstance();

        store.add("Nada rima con dos.");
        store.add("Mi nabo para vos");
        store.add("Dame dos");
        store.add("El que tengo aquí colgado");
        store.add("Por el culo te la hinco");
    }

    @Test
    public void testSearch() throws Exception
    {
        assertEquals(store.search("dos").size(), 2);
        assertEquals(store.search("os").size(), 3);
        assertEquals(store.search("vos").size(), 1);
        assertEquals(store.search("v").size(), 0);
    }

    @Test
    public void testGetRhyme() throws Exception
    {
        assertEquals(store.getRhyme("Rima inexistente"), RhymeStore.DEFAULT_RHYME);
        assertEquals(store.getRhyme("Llama al abogado"), "El que tengo aquí colgado");
        assertEquals(store.getRhyme("Pon telecinco"), "Por el culo te la hinco");
    }

}
