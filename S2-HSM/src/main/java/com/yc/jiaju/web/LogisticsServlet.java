package com.yc.jiaju.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.LogisticsDao;


@WebServlet("/LogisticsServlet.do")
public class LogisticsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private LogisticsDao ldao= new LogisticsDao();
    
	protected void sendgood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid=request.getParameter("oid");
		String lids=request.getParameter("lids");
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String addr=request.getParameter("addr");
		String company=request.getParameter("company");
		try {
			ldao.insert(oid, company, lids, name, phone, addr);
			response.getWriter().append("�����ɹ���");
		} catch (Exception e) {
			response.getWriter().append("����ʧ�ܣ�");
		}
		
	}
	/*
	 * protected void update(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { String
	 * oid=request.getParameter("oid");
	 * 
	 * String name=request.getParameter("name"); String
	 * phone=request.getParameter("phone"); String
	 * addr=request.getParameter("addr");
	 * 
	 * try { ldao.update(oid, name, phone, addr);
	 * response.getWriter().append("�޸ĳɹ���"); } catch (Exception e) {
	 * response.getWriter().append("�޸�ʧ�ܣ�"); }
	 * 
	 * }
	 */

	

}
