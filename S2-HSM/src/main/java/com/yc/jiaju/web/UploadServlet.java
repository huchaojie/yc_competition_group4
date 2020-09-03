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
@WebServlet("/uploadImg")
public class UploadServlet extends HttpServlet {
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
		System.out.println("web路径对应的磁盘路径"+diskpath);
		//文件必须放在工程的里面
		try {
			part.write(diskpath + "/"+filename);
			//返回该文件web路径
			System.out.println("文件必须放在工程的里面成功");
		} catch (Exception e) {
			System.out.println("文件必须放在工程的里面失败");
		}
		
		response.getWriter().append("上传成功");
		
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		System.out.println("user(用户)===>"+user);
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String path = webpath + "/"+filename;
		System.out.println(path);
		String head = "/S2-plhy-jiaju"+path;
		int uid = (int) user.get("uid");
		ud.changeimg(uid, head);
		Map<String, Object> user1 = ud.selectByLogin(uid);
		String yemail = user1.get("email").toString();
		user.put("email", yemail.substring(0,4)+"******"+yemail.substring(yemail.length()-3,yemail.length()));
		if(user1.get("sex").equals("1")) {
			user1.put("sex", "男");
		}else {
			user1.put("sex", "女");
		}
		request.getSession().setAttribute("loginedUser", user1); //存入已登录人
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
