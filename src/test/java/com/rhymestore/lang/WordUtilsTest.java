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
import static com.rhymestore.lang.WordUtils.isNumber;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * Unit tests for the {@link WordUtils} class.
 * 
 * @author Ignasi Barrera
 */
public class WordUtilsTest
{
    @Test
    public void testCapitalize()
    {
        assertEquals(capitalize(null), null);
        assertEquals(capitalize(""), "");
        assertEquals(capitalize("hola"), "Hola");
        assertEquals(capitalize("hOLA"), "HOLA");
    }

    @Test
    public void testGetLastWord()
    {
        assertEquals(getLastWord(null), "");
        assertEquals(getLastWord(""), "");
        assertEquals(getLastWord("hola"), "hola");
        assertEquals(getLastWord("hola adios"), "adios");
        assertEquals(getLastWord("hola ."), ".");
    }

    @Test
    public void testIsNumber()
    {
        assertFalse(isNumber(null));
        assertFalse(isNumber(""));
        assertFalse(isNumber("a"));
        assertFalse(isNumber("-a"));
        assertFalse(isNumber("23a"));

        assertTrue(isNumber("-1"));
        assertTrue(isNumber("0"));
        assertTrue(isNumber("12345"));
    }
}
