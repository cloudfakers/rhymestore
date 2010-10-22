package com.rhymestore.web;

import java.io.IOException;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.wink.common.annotations.Workspace;

import com.rhymestore.store.RhymeStore;

/**
 * Rhyme Management Resource.
 * 
 * @author Ignasi Barrera
 */
@Path("/")
@Workspace(workspaceTitle = "Rhymestore", collectionTitle = "rhymes")
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
    @Produces(MediaType.TEXT_PLAIN)
    public String getRhyme()
    {
        String rhyme = "";

        try
        {
            Set<String> rhymes = store.search("");
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String addRhyme(@FormParam(RHYME_PARAM) final String rhyme)
    {
        try
        {
            store.add(rhyme);
            return rhyme;
        }
        catch (IOException e)
        {
            // TODO
            return null;
        }
    }
}
