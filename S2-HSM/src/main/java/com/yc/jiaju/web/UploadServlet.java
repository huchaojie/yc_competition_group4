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
		System.out.println("web·����Ӧ�Ĵ���·��"+diskpath);
		//�ļ�������ڹ��̵�����
		try {
			part.write(diskpath + "/"+filename);
			//���ظ��ļ�web·��
			System.out.println("�ļ�������ڹ��̵�����ɹ�");
		} catch (Exception e) {
			System.out.println("�ļ�������ڹ��̵�����ʧ��");
		}
		
		response.getWriter().append("�ϴ��ɹ�");
		
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		System.out.println("user(�û�)===>"+user);
		if(user==null) {
			response.getWriter().append("���ȵ�¼");
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
			user1.put("sex", "��");
		}else {
			user1.put("sex", "Ů");
		}
		request.getSession().setAttribute("loginedUser", user1); //�����ѵ�¼��
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
}
