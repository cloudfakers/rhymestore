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

import java.lang.StringBuilder;

// http://github.com/enricruiz/keymaker
public class Keymaker
{
    String namespace;

    public Keymaker(String namespace)
    {
        this.namespace = namespace;
    }

    public Keymaker build(String ... namespaces)
    {
        StringBuilder builder = new StringBuilder(this.namespace);

        for (String name : namespaces)
        {
            builder.append(":").append(name);
        }

        return new Keymaker(builder.toString());
    }

    @Override
    public String toString()
    {
        return this.namespace;
    }
}
