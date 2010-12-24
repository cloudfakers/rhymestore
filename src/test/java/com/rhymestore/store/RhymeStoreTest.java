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

package com.rhymestore.store;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordParserFactory;

/**
 * Unit tests for the {@link RhymeStore} class.
 * 
 * @author Enric Ruiz
 */
public class RhymeStoreTest
{
    /** The Redis test database. */
    public static final int TEST_DATABASE = 1;

    /** The store to test. */
    private RhymeStore store;

    /** The {@link WordParser} used to parse rhymes. */
    private WordParser wordParser;

    @BeforeMethod
    public void setUp() throws IOException
    {
        wordParser = WordParserFactory.getWordParser();
        store = new TestRhymeStore();

        store.add("Mi nabo para vos!!");
        store.add("Te la meto del revés");
    }

    @AfterMethod
    public void tearDown() throws IOException
    {
        ((TestRhymeStore) store).cleanDB();
    }

    @Test
    public void testFindAll() throws IOException
    {
        assertEquals(store.findAll().size(), 2);
    }

    @Test
    public void testGetRhyme() throws IOException
    {
        assertEquals(store.getRhyme("¿Hay algo que rime con tres?"), "Te la meto del revés");
        assertEquals(store.getRhyme("Nada rima con dos"), "Mi nabo para vos!!");
        assertEquals(store.getRhyme("Nada rima con be"), wordParser.getDefaultRhyme());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testDeleteRhyme() throws IOException
    {
        store.delete("");
    }
}
