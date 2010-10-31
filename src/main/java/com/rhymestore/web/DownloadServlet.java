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
 * 
 * @see RhymeStore
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
