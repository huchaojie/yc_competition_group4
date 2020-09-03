package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.cartDao;
import com.yc.jiaju.util.DBHelper;


@WebServlet("/CartServlet.do")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private cartDao cdao = new cartDao();   
    
   
	protected void insertcart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String pid=request.getParameter("pid");
		String count = request.getParameter("count");
		try {
			cdao.insert(uid, pid, count);
			response.getWriter().append("Ìí¼Ó³É¹¦");
		} catch (Exception e) {
			response.getWriter().append("Ìí¼ÓÊ§°Ü");
		}
		
	}
	protected void user(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
		}
	}
	protected void getcart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		
		List<Map<String,Object>> list=cdao.query(uid);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}
	protected void changecount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		//System.out.println(uid);
		String pid=request.getParameter("pid");
		String count = request.getParameter("count");
		cdao.changecount(uid, pid, count);
	}
	protected void deleteproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String pid=request.getParameter("pid");
		
		cdao.deleteproduct(uid,pid);
	}

	

}
