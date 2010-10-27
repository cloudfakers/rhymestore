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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for the {@link SpanishWordParser}.
 * 
 * @author Ignasi Barrera
 */
public class WordParserTest
{
    /** The word parser. */
    private WordParser wordParser;

    @BeforeMethod
    public void setUp()
    {
        wordParser = new SpanishWordParser();
    }

    @Test
    public void testGetRhymeTest()
    {
        assertEquals(wordParser.rhymePart(""), "");

        // Monosilabos
        assertEquals(wordParser.rhymePart("pez"), "ez");

        // Agudas
        assertEquals(wordParser.rhymePart("correr"), "er");
        assertEquals(wordParser.rhymePart("melón"), "ón");

        // Llanas
        assertEquals(wordParser.rhymePart("lío"), "ío");
        assertEquals(wordParser.rhymePart("carromato"), "ato");
        assertEquals(wordParser.rhymePart("Telecinco"), "inco");
        assertEquals(wordParser.rhymePart("abogado"), "ado");
        assertEquals(wordParser.rhymePart("auriculares"), "ares");

        // Esdrújulcas y sobreesdrújulas
        assertEquals(wordParser.rhymePart("cáspita"), "áspita");
        assertEquals(wordParser.rhymePart("recuérdamelo"), "érdamelo");
    }

    @Test
    public void testWordType()
    {
        assertEquals(wordParser.stressType("pez"), StressType.LAST);
        assertEquals(wordParser.stressType("correr"), StressType.LAST);
        assertEquals(wordParser.stressType("lío"), StressType.SECOND_LAST);
        assertEquals(wordParser.stressType("carromato"), StressType.SECOND_LAST);
        assertEquals(wordParser.stressType("cáspita"), StressType.THIRD_LAST);
        assertEquals(wordParser.stressType("recuérdamelo"), StressType.FOURTH_LAST);
    }

}
