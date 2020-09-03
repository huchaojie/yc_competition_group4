package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.chatDao;
import com.yc.jiaju.util.DBHelper;


@WebServlet("/ChatServlet.do")
public class ChatServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private chatDao cdao = new chatDao();
    
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		HashMap<String, Object> page = new HashMap<>();
		
		Map<String,Object> list=cdao.query(uid);
		page.put("list", list);
		page.put("statu",new DBHelper().query("select a.statu from tb_manager a ,tb_chat b where b.mid=a.id  and  b.uid=?", uid).get(0).get("statu"));
		//System.out.println(page);
		print(response, page);
	}

	
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		cdao.insert(uid, content);
	}
	
	/*
	 * protected void mlogin(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { String
	 * email=request.getParameter("email"); String
	 * pass=request.getParameter("pass");
	 * 
	 * Map<String, Object> manager = cdao.mlogin(email, pass);
	 * request.getSession().setAttribute("Manager", manager); //´æÈëÒÑµÇÂ¼ÈË
	 * if(manager==null) { response.getWriter().append("µÇÂ½Ê§°Ü"); }else {
	 * response.getWriter().append("µÇÂ½³É¹¦"); }
	 * 
	 * }
	 */
	protected void mquerypeople(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> manager =
				(Map<String,Object>) request.getSession().getAttribute("Manager");
		System.out.println(manager);
		if(manager==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String id =  String.valueOf(manager.get("id")) ;
		HashMap<String, Object> page = new HashMap<>();
		
		
		page.put("list", cdao.mquerypeople(id));
		
		//System.out.println(page);
		print(response, page);
	}
	protected void mquery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid");
		@SuppressWarnings("unchecked")
		Map<String,Object> manager =
				(Map<String,Object>) request.getSession().getAttribute("Manager");
		System.out.println(manager);
		if(manager==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String id =  String.valueOf(manager.get("id")) ;
		HashMap<String, Object> page = new HashMap<>();
		
		Map<String,Object> list=cdao.mquery(id,uid);
		page.put("list", list);
		
		//System.out.println(page);
		print(response, page);
	}
	protected void minsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("content");
		String uid = request.getParameter("uid");
		@SuppressWarnings("unchecked")
		Map<String,Object> manager =
				(Map<String,Object>) request.getSession().getAttribute("Manager");
		if(manager==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		//String id =  String.valueOf(manager.get("id")) ;
		cdao.minsert(uid, content);
	}
	protected void reset(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String uid = request.getParameter("uid");
		@SuppressWarnings("unchecked")
		Map<String,Object> manager =
				(Map<String,Object>) request.getSession().getAttribute("Manager");
		if(manager==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		//String id =  String.valueOf(manager.get("id")) ;
		cdao.resetxx(uid);
	}
}
