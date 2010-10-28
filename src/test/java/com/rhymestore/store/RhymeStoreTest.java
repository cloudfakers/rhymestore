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

package com.rhymestore.store;

import static org.testng.Assert.assertEquals;

import java.io.IOException;

import org.testng.annotations.Test;

// The tests assumes a redis server working in localhost
public class RhymeStoreTest
{
    private RhymeStore store;

    // @BeforeMethod
    public void setUp() throws Exception
    {
        store = RhymeStore.getInstance();

        store.add("Nada rima con dos.");
        store.add("Mi nabo para vos");
        store.add("Dame dos");
        store.add("El que tengo aquí colgado");
        store.add("Por el culo te la hinco");
    }

    @Test
    public void t() throws IOException
    {
        store = RhymeStore.getInstance();

        store.add("Mi nabo para vos");

        System.out.println(store.getRhyme("Nada rima con dos."));

        assertEquals(store.findAll().size(), 2);
    }

    @Test(enabled = false)
    public void testGetRhyme() throws Exception
    {
        assertEquals(store.getRhyme("Rima inexistente"), RhymeStore.DEFAULT_RHYME);
        assertEquals(store.getRhyme("Llama al abogado"), "El que tengo aquí colgado");
        assertEquals(store.getRhyme("Pon telecinco"), "Por el culo te la hinco");
    }

}
