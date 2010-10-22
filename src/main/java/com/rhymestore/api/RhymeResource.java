package com.rhymestore.api;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

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
    /** The Rhyme Store. */
    private RhymeStore store;

    /**
     * Default constructor.
     */
    public RhymeResource()
    {
        store = RhymeStore.getInstance();
    }

    /**
     * Gets a rhyme for the given sentence.
     * 
     * @param sentence The sentence to rhyme.
     * @return The rhyme for the given sentence.
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String addRhyme(@FormParam(BotizeConstants.TWEET_PARAM) final String sentence)
    {
        try
        {
            Set<String> rhymes = store.search(sentence);
            if (!rhymes.isEmpty())
            {
                return rhymes.iterator().next();
            }

            // TODO
            throw new WebApplicationException();
        }
        catch (IOException e)
        {
            // TODO
            throw new WebApplicationException();
        }
    }
}
