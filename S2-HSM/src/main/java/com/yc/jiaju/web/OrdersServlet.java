package com.yc.jiaju.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.KdniaoTrackQueryAPI;
import com.yc.jiaju.dao.mygxinDao;
import com.yc.jiaju.dao.ordersDao;
import com.yc.jiaju.po.Result;

@WebServlet("/OrdersServlet.do")
public class OrdersServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private ordersDao odao = new ordersDao();
    private mygxinDao mdao = new mygxinDao();
    
	protected void queryorders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		
		HashMap<String, Object> page = new HashMap<>();
		
		List<Map<String,Object>> list=odao.listorders(uid);//1为uid
		page.put("list", list);
		
		//System.out.println(page);
		print(response, page);
		
	}
	protected void queryorderxq(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oid = request.getParameter("oid");
		
		print(response, odao.listorderxq(oid));
		
	}
	protected void changestatu(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String statu = request.getParameter("statu");
		String oid = request.getParameter("oid");
		try {
			odao.changestatu(statu, oid);
			response.getWriter().append("修改成功");
		} catch (Exception e) {
			response.getWriter().append("修改失败");
		}
		
		
	}
	protected void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String oids = request.getParameter("oids");
		
		HashMap<String, Object> page = new HashMap<>();
		
		List<Map<String,Object>> list=odao.search(oids);
		page.put("list", list);
		
		//System.out.println(page);
		print(response, page);
		
		
	}
	protected void searchByPname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pname = request.getParameter("pname");
		HashMap<String, Object> page = new HashMap<>();
		
		List<Map<String,Object>> list=odao.searchByPname(pname);
		page.put("list", list);
		
		//System.out.println(page);
		print(response, page);
		
	}
	protected void mygxin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		
		
		Map<String,Object> page=new HashMap<String, Object>();
		page.put("gopay", mdao.querygopay(uid) );
		page.put("getp", mdao.querygetp(uid) );
		page.put("eva", mdao.queryeva(uid) );
		
		//System.out.println(page);
		print(response, page);
		
	}
	@SuppressWarnings("unchecked")
	protected void querym(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String page1=request.getParameter("page");
		String rows=request.getParameter("rows");
		String oids=request.getParameter("oids");
		String uname=request.getParameter("uname");
		String name=request.getParameter("name");
		String phone=request.getParameter("phone");
		String time= request.getParameter("time");
		String statu=request.getParameter("statu");
		//System.out.println(time);
		HashMap<String, Object> data = new HashMap<>();
		if(page1!=null&&rows!=null) {
			List<?> list = odao.querym(statu,time,oids, uname, name, phone, Integer.valueOf(page1), Integer.valueOf(rows));
			data.put("rows", list);
			//System.out.println(list);
		}
		
		int total = odao.queryp(statu,time,oids, uname, name, phone);
		Double alltotal =  (Double) odao.querytotal(time,oids, uname, name, phone).get("total");
		data.put("total", total);
		data.put("alltotal", alltotal);
		print(response, data);
	}
	protected void queryoit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String oid=request.getParameter("oid");
		
		
		List<?> list = odao.queryoit(oid);
		
		print(response, list);
	}
	protected void wuliu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		String oid=request.getParameter("oid");
		
		
		String com=(String) odao.wuliu(oid).get("company");
		String lids=String.valueOf(odao.wuliu(oid).get("lids"));
		String result = null;
		try {
			result = new KdniaoTrackQueryAPI().getOrderTracesByJson(com, lids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.getWriter().append(result);
	}
	protected void lianxi(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		if(Manager==null) {
			print(response,new Result(0,"请先登录"));
			return;
		}
		String rmid=request.getParameter("mid");
		String mid =  String.valueOf(Manager.get("id")) ;
		if(mid.equals(rmid)) {
			
			print(response,new Result(1,"成功"));
		}else {
			
			print(response,new Result(0,"该用户不是你的客户！"));
		}
	}

}
