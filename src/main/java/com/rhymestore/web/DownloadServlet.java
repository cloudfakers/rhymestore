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
 * Creates a text file with all stored rhymes and serves it as a file to
 * download.
 * 
 * @author Ignasi Barrera
 */
public class DownloadServlet extends HttpServlet
{
	/** The logger. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DownloadServlet.class);

	/** The name of the download file. */
	private static final String DOWNLOAD_FILE_NAME = "rhymes.txt";

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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Get all stored rhymes
		Set<String> rhymes = store.findAll();

		List<String> sortedRhymes = new ArrayList<String>(rhymes);
		Collections.sort(sortedRhymes, String.CASE_INSENSITIVE_ORDER);

		LOGGER.info("Exporting {} rhymes...", sortedRhymes.size());

		// Set response headers
		resp.setContentType("text/plain");
		resp.setHeader("Content-Disposition", "attachment; filename="
				+ DOWNLOAD_FILE_NAME);

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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		doPost(req, resp);
	}
}
