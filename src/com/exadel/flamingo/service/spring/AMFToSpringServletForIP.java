/*
 * Exadel Flamingo
 * Copyright (C) 2008 Exadel, Inc.
 * 
 * This file is part of Exadel Flamingo.
 * 
 * Exadel Flamingo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation
 * 
 * AMFToSpringServlet.java
 * 
 * Last modified by: $Author: klebed $
 * $Revision: 1446 $   $Date: 2008-05-02 12:00:17 +0300 (Fri, 02 May 2008) $
 */
package com.exadel.flamingo.service.spring;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation of AMF protocol connecting to Spring.
 * 
 * @author apleskatsevich
 */
public class AMFToSpringServletForIP extends HttpServlet {
	private static final long serialVersionUID = -4014289603696902856L;

	/**
	 * Invokes AMFToSeamRequestProcessor processor.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @throws javax.servlet.ServletException
	 *             If errors occur
	 * @throws java.io.IOException
	 *             If IO errors occur
	 */
	@Override
	protected void service(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
//		System.out.println(request.getRemoteHost()+"---------------frIP-------------");
		AMFToSpringRequestProcessor.instance().process(request, response);
	}
}
