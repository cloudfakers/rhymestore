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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.rhymestore.lang.AbstractWordParserTest;
import com.rhymestore.lang.StressType;
import com.rhymestore.lang.WordParser;

/**
 * Unit tests for the {@link SpanishWordParser}.
 * 
 * @author Ignasi Barrera
 */
public class SpanishWordParserTest extends AbstractWordParserTest
{
	@Override
	protected WordParser getWordParser()
	{
		return new SpanishWordParser();
	}

	@Override
	public void testPhoneticRhymePart()
	{
		assertEquals(wordParser.phoneticRhymePart(""), "");

		// Monosilabos
		assertEquals(wordParser.phoneticRhymePart("pez"), "ez");
		assertEquals(wordParser.phoneticRhymePart("seis"), "eis");
		assertEquals(wordParser.phoneticRhymePart("pies"), "es");
		assertEquals(wordParser.phoneticRhymePart("Dios"), "os");

		// Agudas
		assertEquals(wordParser.phoneticRhymePart("correr"), "er");
		assertEquals(wordParser.phoneticRhymePart("melón"), "on");
		assertEquals(wordParser.phoneticRhymePart("adiós"), "os");
		assertEquals(wordParser.phoneticRhymePart("Gasteiz"), "eiz");
		assertEquals(wordParser.phoneticRhymePart("Asier"), "er");

		// Llanas
		assertEquals(wordParser.phoneticRhymePart("lío"), "io");
		assertEquals(wordParser.phoneticRhymePart("carromato"), "ato");
		assertEquals(wordParser.phoneticRhymePart("Telecinco"), "inco");
		assertEquals(wordParser.phoneticRhymePart("abogado"), "ado");
		assertEquals(wordParser.phoneticRhymePart("auriculares"), "ares");
		assertEquals(wordParser.phoneticRhymePart("canoa"), "oa");
		assertEquals(wordParser.phoneticRhymePart("ceuta"), "euta");
		assertEquals(wordParser.phoneticRhymePart("cueva"), "eva");

		// Esdrújulcas y sobreesdrújulas
		assertEquals(wordParser.phoneticRhymePart("cáspita"), "aspita");
		assertEquals(wordParser.phoneticRhymePart("recuérdamelo"), "erdamelo");
		assertEquals(wordParser.phoneticRhymePart("viéndolo"), "endolo");

		// Casos foneticos especiales => sustitucion de consonantes
		assertEquals(wordParser.phoneticRhymePart("suyo"), "ullo");
		assertEquals(wordParser.phoneticRhymePart("barullo"), "ullo");
		assertEquals(wordParser.phoneticRhymePart("barba"), "arva");
		assertEquals(wordParser.phoneticRhymePart("parva"), "arva");
		assertEquals(wordParser.phoneticRhymePart("gong"), "ong");
		assertEquals(wordParser.phoneticRhymePart("falange"), "anje");
		assertEquals(wordParser.phoneticRhymePart("alfanje"), "anje");
		assertEquals(wordParser.phoneticRhymePart("cacho"), "acho");

		// Palabra imposible pero caso contemplado
		assertEquals(wordParser.phoneticRhymePart("gargáreha"), "area");
	}

	@Override
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

	@Override
	public void testRhyme()
	{
		// Rhymes withoud punctuation
		assertTrue(wordParser.rhyme("", ""));
		assertTrue(wordParser.rhyme("pez", "hez"));
		assertTrue(wordParser.rhyme("tres", "revés"));
		assertTrue(wordParser.rhyme("Telecinco", "hinco"));
		assertTrue(wordParser.rhyme("nabo", "centavo"));
		assertTrue(wordParser.rhyme("falange", "alfanje"));
		assertTrue(wordParser.rhyme("parva", "escarba"));
		assertTrue(wordParser.rhyme("tuyo", "murmullo"));
		assertTrue(wordParser.rhyme("cáspita", "supercáspita"));

		// Rhymes with punctuation
		assertTrue(wordParser.rhyme("cantar.", "pescar"));
		assertTrue(wordParser.rhyme("calor!", "motor?"));
		assertTrue(wordParser.rhyme("calor  ", "motor&;'?="));
	}

	@Override
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

		assertTrue(wordParser.isLetter('Ñ'));
		assertTrue(wordParser.isLetter('ñ'));

		// Invalid Letters

		assertFalse(wordParser.isLetter(';'));
		assertFalse(wordParser.isLetter(' '));
		assertFalse(wordParser.isLetter('&'));
		assertFalse(wordParser.isLetter('.'));
		assertFalse(wordParser.isLetter(','));
		assertFalse(wordParser.isLetter(';'));
		assertFalse(wordParser.isLetter('-'));
	}

	@Override
	public void testIsWord()
	{
		// Valid words
		assertTrue(wordParser.isWord("hola"));
		assertTrue(wordParser.isWord("test"));
		assertTrue(wordParser.isWord("adiós"));
		assertTrue(wordParser.isWord("valid!"));
		assertTrue(wordParser.isWord("logroño"));
		assertTrue(wordParser.isWord("LOGROÑO"));

		// Valid numbers
		assertTrue(wordParser.isWord("-1500"));
		assertTrue(wordParser.isWord("-1"));
		assertTrue(wordParser.isWord("0"));
		assertTrue(wordParser.isWord("25"));
		assertTrue(wordParser.isWord("123456789"));

		// Invalid words
		assertFalse(wordParser.isWord("-1-2"));
		assertFalse(wordParser.isWord("-abc"));
		assertFalse(wordParser.isWord("hola.adios"));
		assertFalse(wordParser.isWord("ab23cd"));
	}

	@Override
	public void testGetDefaultRhyme()
	{
		String rhyme0 = ((SpanishWordParser) wordParser).defaultRhymes.get(0);
		String rhyme1 = ((SpanishWordParser) wordParser).defaultRhymes.get(1);

		assertEquals(wordParser.getDefaultRhyme(), rhyme0);
		assertEquals(wordParser.getDefaultRhyme(), rhyme1);
		assertEquals(wordParser.getDefaultRhyme(), rhyme0);
		assertEquals(wordParser.getDefaultRhyme(), rhyme1);
	}

	@Test
	public void testGetNumberPhoneticRhymePart()
	{
		assertEquals(wordParser.phoneticRhymePart("-1"), "uno");
		assertEquals(wordParser.phoneticRhymePart("0"), "ero");
		assertEquals(wordParser.phoneticRhymePart("dos"), "os");
		assertEquals(wordParser.phoneticRhymePart("3521637"), "ete");
		assertEquals(wordParser.phoneticRhymePart("350000"), "il");
		assertEquals(wordParser.phoneticRhymePart("5000000"), "ones");
	}
}
