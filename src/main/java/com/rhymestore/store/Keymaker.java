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
