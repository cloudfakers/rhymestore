package com.rhymestore.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rhymestore.store.RhymeStore;

/**
 * Servlet to export stored rhymes to a text file.
 * 
 * @author Ignasi Barrera
 */
public class ExportServlet extends HttpServlet
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExportServlet.class);

	/** Serial UID. */
	private static final long serialVersionUID = 1L;

	/** The backend rhyme store. */
	private RhymeStore store;

	/**
	 * Initializes the servlet.
	 * 
	 * @throws If the servlet cannot be initialized.
	 */
	@Override
	public void init() throws ServletException
	{
		store = RhymeStore.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Get all stored rhymes
		Set<String> rhymes = store.findAll();

		List<String> sortedRhymes = new ArrayList<String>(rhymes);
		Collections.sort(sortedRhymes, String.CASE_INSENSITIVE_ORDER);

		LOGGER.info("Exporting {} rhymes...", sortedRhymes.size());

		// Set response headers
		resp.setContentType("text/plain");
		resp.setHeader("Content-Disposition", "attachment; filename=rhymes.txt");

		// Write output
		PrintWriter pw = new PrintWriter(resp.getOutputStream());

		for (String rhyme : sortedRhymes)
		{
			pw.println(rhyme);
		}

		pw.flush();
		pw.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doGet(req, resp);
	}
}
