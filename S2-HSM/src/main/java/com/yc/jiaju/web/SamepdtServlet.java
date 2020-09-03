package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.util.DBHelper;




@WebServlet("/SamepdtServlet.do")
public class SamepdtServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sql="select * from tb_samepdt where cid=5 or cid=6";
		List<?> list = new DBHelper().query(sql);
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", list);
		print(response, page);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
