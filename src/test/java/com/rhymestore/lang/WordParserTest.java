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
 * Unit tests for the {@link WordParser}.
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
        wordParser = new WordParser();
    }

    @Test
    public void testGetRhymeTest()
    {
        assertEquals(wordParser.getRhymeText(""), "");

        // Monosilabos
        assertEquals(wordParser.getRhymeText("pez"), "ez");

        // Agudas
        assertEquals(wordParser.getRhymeText("correr"), "er");
        assertEquals(wordParser.getRhymeText("melón"), "ón");

        // Llanas
        assertEquals(wordParser.getRhymeText("lío"), "ío");
        assertEquals(wordParser.getRhymeText("carromato"), "ato");
        assertEquals(wordParser.getRhymeText("Telecinco"), "inco");
        assertEquals(wordParser.getRhymeText("abogado"), "ado");
        assertEquals(wordParser.getRhymeText("auriculares"), "ares");

        // Esdrújulcas y sobreesdrújulas
        assertEquals(wordParser.getRhymeText("cáspita"), "áspita");
        assertEquals(wordParser.getRhymeText("recuérdamelo"), "érdamelo");
    }

    @Test
    public void testWordType()
    {
        assertEquals(wordParser.wordType("pez"), WordType.AGUDA);
        assertEquals(wordParser.wordType("correr"), WordType.AGUDA);
        assertEquals(wordParser.wordType("lío"), WordType.LLANA);
        assertEquals(wordParser.wordType("carromato"), WordType.LLANA);
        assertEquals(wordParser.wordType("cáspita"), WordType.ESDRUJULA);
        assertEquals(wordParser.wordType("recuérdamelo"), WordType.SOBREESDRUJULA);
    }

}
