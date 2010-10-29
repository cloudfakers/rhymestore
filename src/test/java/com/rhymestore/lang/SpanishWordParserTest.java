/**
 * The Rhymestore project.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
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
        assertEquals(wordParser.stressType("recuérdamelo"), StressType.FOURTH_LAST);
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
