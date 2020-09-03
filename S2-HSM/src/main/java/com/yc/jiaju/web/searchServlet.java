package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.searchDao;

@WebServlet("/searchServlet.do")
public class searchServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	searchDao sdao=new searchDao();
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sname=request.getParameter("sname");
		List<Map<String, Object>>list=sdao.search(sname);
		HashMap<String, Object> page = new HashMap<String, Object>();
		page.put("list", list);
		print(response,page);
	}
	/*
	 * protected void searchAll(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { List<Map<String,
	 * Object>>list=sdao.searchAll(); HashMap<String, Object> page = new
	 * HashMap<String, Object>(); page.put("list", list); print(response,page); }
	 */
	//商品排序
	protected void searchSort(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String obj=request.getParameter("obj");
		String orderby=request.getParameter("orderby");
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		
		String bywhat=request.getParameter("bywhat");
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		//总页数
		int pages=sdao.countPages(size,a,b);
		List<Map<String, Object>> list = sdao.searchSort(a,b,bywhat,obj,orderby,page,size);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list", list);
		page1.put("pages", pages);
		print(response, page1);
	}
	//搜索页商品排序
	protected void searchhtmlSort(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String orderby=request.getParameter("orderby");
		String sname=request.getParameter("sname");
		String bywhat=request.getParameter("bywhat");
		List<Map<String, Object>> list = sdao.searchSort(sname,bywhat,orderby);
		HashMap<String, Object> page = new HashMap<String, Object>();
		page.put("list", list);
		print(response, page);
	}
	//搜索页新增商品
	protected void searchhtmlIsNew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sname=request.getParameter("sname");
		List<Map<String, Object>> list = sdao.searchIsNew(sname);
		HashMap<String, Object> page = new HashMap<String, Object>();
		page.put("list", list);
		print(response, page);
	}
	//新增商品
	protected void searchIsNew(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int size=Integer.parseInt(request.getParameter("size"));
		int page=Integer.parseInt(request.getParameter("page"));
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		//总页数
		int pages=sdao.countPages(size,a,b);
		List<Map<String, Object>> list = sdao.searchIsNew(page,size,a,b);
		HashMap<String, Object> pagee = new HashMap<String, Object>();
		pagee.put("list", list);
		pagee.put("pages", pages);
		print(response, pagee);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}