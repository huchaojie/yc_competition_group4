package com.yc.jiaju.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.yc.jiaju.dao.UserDao;

@MultipartConfig()
@WebServlet("/Upchatimg.do")
public class Upchatimg extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserDao ud = new UserDao();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		Part part = request.getPart("file");
		part.getName(); //�ļ��ϴ��ֶ���
		String filename = part.getSubmittedFileName();//��ȡ�ϴ����ļ���
		System.out.println(filename);
		part.getSize();//�ļ���С
		//���幤���ڲ����ϴ��ļ���
		String webpath = "/img/head";
		//��ȡӦ�������Ķ���
		ServletContext sc = getServletContext();
		//����web·����Ӧ�Ĵ���·��
		String diskpath = sc.getRealPath(webpath);
		System.out.println(diskpath);
		//�ļ�������ڹ��̵�����
		part.write(diskpath + "/"+filename);
		//���ظ��ļ�web·��
		response.getWriter().append(webpath + "/"+filename);
		
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
