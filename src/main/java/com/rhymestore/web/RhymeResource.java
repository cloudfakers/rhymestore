package com.rhymestore.web;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.rhymestore.botize.BotizeConstants;
import com.rhymestore.store.RhymeStore;

/**
 * Rhyme Management Resource.
 * 
 * @author Ignasi Barrera
 */
@Path("/rhyme")
public class RhymeResource
{
    /** The name of the parameter used to submit rhymes. */
    public static final String RHYME_PARAM = "rhyme";

    /** The backend Rhyme Store. */
    private RhymeStore store;

    /**
     * Default constructor.
     */
    public RhymeResource()
    {
        store = new RhymeStore();
    }

    /**
     * Gets a rhyme for the given sentence.
     * 
     * @param sentence The sentence to rhyme.
     * @return The rhyme for the given sentence.
     */
    @GET
    public String getRhyme(@FormParam(BotizeConstants.TWEET_PARAM) String sentence)
    {
        String rhyme = "";

        try
        {
            Set<String> rhymes = store.search(sentence);
            if (!rhymes.isEmpty())
            {
                rhyme = rhymes.iterator().next();
            }
        }
        catch (IOException e)
        {
            // TODO
        }

        return rhyme;
    }

    /**
     * Adds a new rhyme to the {@link RhymeStore}.
     * 
     * @param rhyme The rhyme to add.
     * @return The added rhyme.
     */
    @POST
    public String addRhyme(@FormParam(RHYME_PARAM) String rhyme)
    {
        try
        {
            store.add(rhyme);
        }
        catch (IOException e)
        {
            // TODO
        }

        return rhyme;
    }
}
