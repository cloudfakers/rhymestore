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
package com.rhymestore.lang.es;

import static com.rhymestore.lang.es.SpanishNumber.getBaseSound;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Unit tests for the {@link SpanishNumber} enum.
 * 
 * @author Serafin Sedano
 */
public class SpanishNumberTest
{
    @Test
    public void testGetDefinedBaseSounds()
    {
        for (SpanishNumber number : SpanishNumber.values())
        {
            assertEquals(getBaseSound(number.getIntValue()), number.getWord());
        }
    }

    @Test
    public void testGetBaseSound()
    {
        assertEquals(getBaseSound(0), "cero");
        assertEquals(getBaseSound(1), "uno");
        assertEquals(getBaseSound(2), "dos");
        assertEquals(getBaseSound(3), "tres");
        assertEquals(getBaseSound(4), "cuatro");
        assertEquals(getBaseSound(5), "cinco");
        assertEquals(getBaseSound(6), "seis");
        assertEquals(getBaseSound(7), "siete");
        assertEquals(getBaseSound(8), "ocho");
        assertEquals(getBaseSound(9), "nueve");
        assertEquals(getBaseSound(10), "diez");

        assertEquals(getBaseSound(11), "once");
        assertEquals(getBaseSound(12), "doce");
        assertEquals(getBaseSound(13), "trece");
        assertEquals(getBaseSound(14), "catorce");
        assertEquals(getBaseSound(15), "quince");
        assertEquals(getBaseSound(16), "seis");
        assertEquals(getBaseSound(17), "siete");
        assertEquals(getBaseSound(18), "ocho");
        assertEquals(getBaseSound(19), "nueve");
        assertEquals(getBaseSound(20), "veinte");

        assertEquals(getBaseSound(100), "cien");
        assertEquals(getBaseSound(1000000), "millón");

        // Decentas
        for (int i = 30; i < 100; i += 10)
        {
            assertEquals(getBaseSound(i), "enta");
        }

        // Centenas
        for (int i = 200; i < 1000; i += 100)
        {
            assertEquals(getBaseSound(i), "cientos");
        }

        // Millares
        for (int i = 1000; i < 10000; i += 1000)
        {
            assertEquals(getBaseSound(i), "mil");
        }

        // Millones
        for (int i = 2000000; i < 10000000; i += 1000000)
        {
            assertEquals(getBaseSound(i), "millones");
        }
    }
}
