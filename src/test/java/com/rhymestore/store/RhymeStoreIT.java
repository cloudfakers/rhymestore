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

import org.testng.annotations.Test;

// The tests assumes a redis server working in localhost!
public class RhymeStoreIT
{
	private RhymeStore store;

	@Test(enabled = false)
	public void basicTest() throws IOException
	{
		store = RhymeStore.getInstance();

		store.add("Mi nabo para vos");
		store.add("Te la meto del revés!!");

		assertEquals(store.getRhyme("¿Hay algo que rime con tres?"),
				"Te la meto del revés!!");
		assertEquals(store.getRhyme("Nada rima con dos."), "Mi nabo para vos");
		assertEquals(store.getRhyme("Nada rima con be"),
				RhymeStore.DEFAULT_RHYME);
		assertEquals(store.findAll().size(), 2);
	}
}