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
 * Identifies the type stress type of a word based on the syllables.
 * 
 * @author Ignasi Barrera
 */
public enum StressType
{
    /** Stress is in the last syllable. */
    LAST,

    /** Stress is in the second-last syllable. */
    SECOND_LAST,

    /** Stress is in the third-last syllable. */
    THIRD_LAST,

    /** Stress is in the fourth-last syllable. */
    FOURTH_LAST;
}
