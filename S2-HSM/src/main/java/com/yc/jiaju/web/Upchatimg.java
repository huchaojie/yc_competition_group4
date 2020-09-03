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
		part.getName(); //文件上传字段名
		String filename = part.getSubmittedFileName();//获取上传的文件名
		System.out.println(filename);
		part.getSize();//文件大小
		//定义工程内部的上传文件夹
		String webpath = "/img/head";
		//获取应用上下文对象
		ServletContext sc = getServletContext();
		//返回web路径对应的磁盘路径
		String diskpath = sc.getRealPath(webpath);
		System.out.println(diskpath);
		//文件必须放在工程的里面
		part.write(diskpath + "/"+filename);
		//返回该文件web路径
		response.getWriter().append(webpath + "/"+filename);
		
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
