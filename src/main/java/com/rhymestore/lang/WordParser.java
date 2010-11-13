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

	/**
	 * Checks if the specified character is a valid letter.
	 * 
	 * @param letter The character to check.
	 * @return Boolean indicating if the specified character is a valid letter.
	 */
	public boolean isLetter(final char letter);

	/**
	 * Checks if the specified text is a valid word.
	 * 
	 * @param text The text to check.
	 * @return Boolean indicating if the specified text is a valid word.
	 */
	public boolean isWord(final String text);

	/**
	 * Gets the default rhyme.
	 * 
	 * @return The default rhym.
	 */
	public String getDefaultRhyme();

}
