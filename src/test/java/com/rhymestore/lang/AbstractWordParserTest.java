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

package com.rhymestore.lang;

import static com.rhymestore.lang.WordUtils.capitalize;
import static com.rhymestore.lang.WordUtils.getLastWord;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Base class for all {@link WordParser} unit testing.
 * <p>
 * All <code>WordParser</code> must implement this test class.
 * 
 * @author Ignasi Barrera
 */
public abstract class AbstractWordParserTest
{
    /** The word parser. */
    protected WordParser wordParser;

    @BeforeMethod
    public void setUp()
    {
        wordParser = getWordParser();
    }

    /**
     * Gets the {@link WordParser} to test.
     * 
     * @return The <code>WordParser</code> to test
     */
    protected abstract WordParser getWordParser();

    // Common tests

    @Test
    public void testCapitalize()
    {
        assertEquals(capitalize(""), "");
        assertEquals(capitalize("a"), "A");
        assertEquals(capitalize("word"), "Word");
        assertEquals(capitalize("capitalize test"), "Capitalize test");
    }

    @Test
    public void testGetLastWord()
    {
        assertEquals(getLastWord(""), "");
        assertEquals(getLastWord("test"), "test");
        assertEquals(getLastWord("two words"), "words");
    }

    // Tests to be implemented by each WordParser implementation tests

    /**
     * Tests the {@link WordParser#phoneticRhymePart(String)} method.
     */
    @Test
    public abstract void testPhoneticRhymePart();

    /**
     * Tests the {@link WordParser#stressType(String)} method.
     */
    @Test
    public abstract void testStressType();

    /**
     * Tests the {@link WordParser#rhyme(String, String)} method.
     */
    @Test
    public abstract void testRhyme();

    /**
     * Tests the {@link WordParser#isLetter(char)} method.
     */
    @Test
    public abstract void testIsLetter();

    /**
     * Tests the {@link WordParser#isWord(String)} method.
     */
    @Test
    public abstract void testIsWord();

}
