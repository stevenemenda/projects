package com.store.catalog.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Add
 */
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
		writer.append("Served at: ").append(request.getContextPath());
		writer.append("\n Method: " + request.getMethod());
		String sa = request.getParameter("param1");
		int a = Integer.valueOf(sa);
		
		String sb = request.getParameter("param2");
		int b = Integer.valueOf(sb);
		
		int result = a + b;
		
		writer.append("\n " + a + " + " + b + " = " + result);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
		writer.append("Served at: ").append(request.getContextPath());
		writer.append("\n Method: " + request.getMethod());
		String req = request.getReader().readLine();
		String  [] lst  = req.split("[+]");
		int len = lst.length;
		int result = Integer.MIN_VALUE;
		if(len >= 2){
			result = Integer.valueOf(lst[0]) + Integer.valueOf(lst[1]);
		}
		if(result  != Integer.MIN_VALUE){
			writer.append("\n " + lst[0] + " + " + lst[1] + " = " + result);
		}else{
			writer.append("\n Error, please check params: " + lst[0] + " " + lst[1]);
		}
		
	}

}
