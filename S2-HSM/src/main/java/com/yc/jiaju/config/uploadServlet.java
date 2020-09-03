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
		//part.getName();//�ļ��ϴ����ֶ���file
		String filename=part.getSubmittedFileName();//��ȡ�ϴ����ļ���
		//System.out.println(filename);
		//part.getSize();//�ļ���С
	
		//���幤���ڲ����ϴ��ļ���
		String webpath="/upload";
		//��ȡӦ�������Ķ���
		ServletContext sc=getServletContext();
		//����web·����Ӧ�Ĵ���·��
		String diskpath=sc.getRealPath(webpath);
		diskpath=diskpath+"/"+filename;
		System.out.println(diskpath);
		webpath+="/"+part.getSubmittedFileName();
		webpath=webpath.substring(1);
		//�ļ�������ڹ��̵����
		part.write(diskpath);
		Result res=new Result(1,"�ļ��ϴ��ɹ�",webpath);
		Gson gson=new Gson();
		String json=gson.toJson(res);
		response.getWriter().append(json);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
