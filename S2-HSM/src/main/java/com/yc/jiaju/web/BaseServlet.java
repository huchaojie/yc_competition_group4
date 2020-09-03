package com.yc.jiaju.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


//@WebServlet("/BaseAction")
public	abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/*
	 * ProductServlet商品操作的Servlet,产品查询、修改、删除
	 * product.do?op=query
	 * product.do?op=update
	 * product.do?op=del
	 * 
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//设置响应对象字符集
		request.setCharacterEncoding("UTF-8");
		//设置网页的字符集
		response.setContentType("text/html;charset=utf-8");
		
		String op = request.getParameter("op");
		//java反射技术
		//通过op获取方法对象
		try {
			Method method=this.getClass().getDeclaredMethod(op, HttpServletRequest.class,HttpServletResponse.class);
			//设置方法可以被访问
			method.setAccessible(true);
			//执行方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append("系统错误");
		}
		//
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}
	protected void print(HttpServletResponse response,Object obj) throws IOException {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		response.getWriter().append(gson.toJson(obj));
	}
	
}
