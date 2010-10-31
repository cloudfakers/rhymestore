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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link SpanishWordParser}.
 * 
 * @author Ignasi Barrera
 */
public class SpanishWordParserTest
{
	/** The word parser. */
	private WordParser wordParser;

	@BeforeMethod
	public void setUp()
	{
		wordParser = WordParserFactory.getWordParser();
	}

	@Test
	public void testPhoneticRhymePart()
	{
		assertEquals(wordParser.phoneticRhymePart(""), "");

		// Monosilabos
		assertEquals(wordParser.phoneticRhymePart("pez"), "ez");

		// Agudas
		assertEquals(wordParser.phoneticRhymePart("correr"), "er");
		assertEquals(wordParser.phoneticRhymePart("melón"), "on");

		// Llanas
		assertEquals(wordParser.phoneticRhymePart("lío"), "io");
		assertEquals(wordParser.phoneticRhymePart("carromato"), "ato");
		assertEquals(wordParser.phoneticRhymePart("Telecinco"), "inco");
		assertEquals(wordParser.phoneticRhymePart("abogado"), "ado");
		assertEquals(wordParser.phoneticRhymePart("auriculares"), "ares");
		assertEquals(wordParser.phoneticRhymePart("canoa"), "oa");

		// Esdrújulcas y sobreesdrújulas
		assertEquals(wordParser.phoneticRhymePart("cáspita"), "aspita");
		assertEquals(wordParser.phoneticRhymePart("recuérdamelo"), "erdamelo");

		// Casos foneticos especiales => sustitucion de consonantes
		assertEquals(wordParser.phoneticRhymePart("suyo"), "ullo");
		assertEquals(wordParser.phoneticRhymePart("barullo"), "ullo");
		assertEquals(wordParser.phoneticRhymePart("barba"), "arva");
		assertEquals(wordParser.phoneticRhymePart("parva"), "arva");
		assertEquals(wordParser.phoneticRhymePart("gong"), "ong");
		assertEquals(wordParser.phoneticRhymePart("falange"), "anje");
		assertEquals(wordParser.phoneticRhymePart("alfanje"), "anje");
		assertEquals(wordParser.phoneticRhymePart("cacho"), "acho");
		assertEquals(wordParser.phoneticRhymePart("gargáreha"), "area"); // Palabra
		// imposible
		// pero
		// caso
		// contemplado
	}

	@Test
	public void testStressType()
	{
		assertEquals(wordParser.stressType("pez"), StressType.LAST);
		assertEquals(wordParser.stressType("correr"), StressType.LAST);
		assertEquals(wordParser.stressType("lío"), StressType.SECOND_LAST);
		assertEquals(wordParser.stressType("carromato"), StressType.SECOND_LAST);
		assertEquals(wordParser.stressType("cáspita"), StressType.THIRD_LAST);
		assertEquals(wordParser.stressType("recuérdamelo"),
				StressType.FOURTH_LAST);
	}

	@Test
	public void testRhyme()
	{
		assertTrue(wordParser.rhyme("", ""));
		assertTrue(wordParser.rhyme("pez", "hez"));
		assertTrue(wordParser.rhyme("tres", "revés"));
		assertTrue(wordParser.rhyme("Telecinco", "hinco"));
		assertTrue(wordParser.rhyme("nabo", "centavo"));
		assertTrue(wordParser.rhyme("falange", "alfanje"));
		assertTrue(wordParser.rhyme("parva", "escarba"));
		assertTrue(wordParser.rhyme("tuyo", "murmullo"));

		// Rhymes with punctuation

		assertTrue(wordParser.rhyme("cantar.", "pescar"));
		assertTrue(wordParser.rhyme("calor!", "motor?"));
		assertTrue(wordParser.rhyme("calor  ", "motor&;'?="));
	}

	@Test
	public void testIsLetter()
	{
		// Valid letters

		assertTrue(wordParser.isLetter('a'));
		assertTrue(wordParser.isLetter('A'));
		assertTrue(wordParser.isLetter('z'));
		assertTrue(wordParser.isLetter('Z'));
		assertTrue(wordParser.isLetter('m'));
		assertTrue(wordParser.isLetter('M'));

		assertTrue(wordParser.isLetter('á'));
		assertTrue(wordParser.isLetter('é'));
		assertTrue(wordParser.isLetter('í'));
		assertTrue(wordParser.isLetter('ó'));
		assertTrue(wordParser.isLetter('ú'));
		assertTrue(wordParser.isLetter('ü'));

		assertTrue(wordParser.isLetter('Á'));
		assertTrue(wordParser.isLetter('É'));
		assertTrue(wordParser.isLetter('Í'));
		assertTrue(wordParser.isLetter('Ó'));
		assertTrue(wordParser.isLetter('Ú'));
		assertTrue(wordParser.isLetter('Ü'));

		// Invalid Letters

		assertFalse(wordParser.isLetter(';'));
		assertFalse(wordParser.isLetter(' '));
		assertFalse(wordParser.isLetter('&'));
		assertFalse(wordParser.isLetter('.'));
		assertFalse(wordParser.isLetter(','));
		assertFalse(wordParser.isLetter(';'));
		assertFalse(wordParser.isLetter('-'));
	}
}
