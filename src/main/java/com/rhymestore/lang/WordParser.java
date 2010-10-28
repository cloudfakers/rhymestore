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

/**
 * Parses words in a concrete language to build perfect rhymes.
 * 
 * @author Ignasi Barrera
 */
public interface WordParser
{
    /**
     * Gets the part of the word that is used to create the rhyme.
     * 
     * @param word The word.
     * @return The part of the word that is used to create the rhyme.
     */
    public String phoneticRhymePart(final String word);

    /**
     * Gets the {@link StressType} of the word based on the syllables.
     * 
     * @param word The word.
     * @return The <code>StressType</code>.
     */
    public StressType stressType(final String word);

    /**
     * Checks if the given words rhyme between them.
     * 
     * @param word1 The first word.
     * @param word2 The second word.
     * @return Boolean indicating if the given words rhyme between them.
     */
    public boolean rhyme(String word1, String word2);

}
