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
	 * ProductServlet��Ʒ������Servlet,��Ʒ��ѯ���޸ġ�ɾ��
	 * product.do?op=query
	 * product.do?op=update
	 * product.do?op=del
	 * 
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//������Ӧ�����ַ���
		request.setCharacterEncoding("UTF-8");
		//������ҳ���ַ���
		response.setContentType("text/html;charset=utf-8");
		
		String op = request.getParameter("op");
		//java���似��
		//ͨ��op��ȡ��������
		try {
			Method method=this.getClass().getDeclaredMethod(op, HttpServletRequest.class,HttpServletResponse.class);
			//���÷������Ա�����
			method.setAccessible(true);
			//ִ�з���
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().append("ϵͳ����");
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
