package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.util.DBHelper;


@WebServlet("/DetailServlet.do")
public class DetailServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sid= request.getParameter("sid");
		String sql="select b.* from tb_samepdt a,tb_product b where a.sid=b.sid and a.sid=?";
		List<?> list = new DBHelper().query(sql,sid);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
