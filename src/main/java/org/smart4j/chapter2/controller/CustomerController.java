package org.smart4j.chapter2.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.smart4j.chapter2.service.CustomerService;

@WebServlet("/customer")
public class CustomerController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomerService cs;
	
	
	
	@Override
	public void init() throws ServletException {
		cs = new CustomerService();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("cs", cs.queryEntryList());
		req.getRequestDispatcher("/WEB-INF/jsp/customer_list.jsp").forward(req, resp);
	}

	
}
