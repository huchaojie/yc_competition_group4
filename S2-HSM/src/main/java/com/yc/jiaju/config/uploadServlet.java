package com.yc.jiaju.config;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.yc.jiaju.po.Result;

@MultipartConfig
@WebServlet("/uploadServlet.do")
public class uploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		Part part=request.getPart("file");
		//part.getName();//文件上传的字段名file
		String filename=part.getSubmittedFileName();//获取上传的文件名
		//System.out.println(filename);
		//part.getSize();//文件大小
	
		//定义工程内部的上传文件夹
		String webpath="/upload";
		//获取应用上下文对象
		ServletContext sc=getServletContext();
		//返回web路径对应的磁盘路径
		String diskpath=sc.getRealPath(webpath);
		diskpath=diskpath+"/"+filename;
		System.out.println(diskpath);
		webpath+="/"+part.getSubmittedFileName();
		webpath=webpath.substring(1);
		//文件必须放在工程的里边
		part.write(diskpath);
		Result res=new Result(1,"文件上传成功",webpath);
		Gson gson=new Gson();
		String json=gson.toJson(res);
		response.getWriter().append(json);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
