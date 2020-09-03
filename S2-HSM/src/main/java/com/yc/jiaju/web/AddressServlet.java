package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.addressDao;


@WebServlet("/AddressServlet.do")
public class AddressServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private addressDao adao = new addressDao();
    
	
	protected void queryadd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		HashMap<String, Object> page = new HashMap<>();
		List<Map<String,Object>> list=adao.queryadd(uid);
		
		page.put("list", list);
		print(response, page);
		
	}
	protected void setdft(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String aid=request.getParameter("aid");
		try {
			adao.setdft(aid,uid);
			response.getWriter().append("设置为默认地址成功");
		} catch (Exception e) {
			response.getWriter().append("设置为默认地址失败");
		}	
	}
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aid=request.getParameter("aid");
		try {
			adao.delete(aid);
			response.getWriter().append("删除成功");
		} catch (Exception e) {
			response.getWriter().append("删除失败");
		}
		
	}
	protected void change(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aid=request.getParameter("aid");
		String name=request.getParameter("name");
		String addr=request.getParameter("addr");
		String phone=request.getParameter("phone");
		String sheng=request.getParameter("sheng");
		String shi=request.getParameter("shi");
		String xian=request.getParameter("xian");
		String province=AreaConstants.PROVINCES[Integer.valueOf(sheng)];
		String city=AreaConstants.CITYS[Integer.valueOf(sheng)][Integer.valueOf(shi)];
		String county=AreaConstants.COUNTYS[Integer.valueOf(sheng)][Integer.valueOf(shi)][Integer.valueOf(xian)];
		if(province.equals("北京")||province.equals("上海")||province.equals("天津")||province.equals("重庆")) {
			addr=province+"市"+county+"区"+addr;
		}else if(province.equals("内蒙古")||province.equals("西藏")||province.equals("广西")||province.equals("宁夏")||province.equals("新疆")){
			addr=province+"自治区"+city+"市"+county+"区"+addr;
		}else if(county.lastIndexOf("区")!=-1){
			addr=province+"省"+city+"市"+county+addr;
		}else if(county.lastIndexOf("区")==-1){
			addr=province+"省"+city+"市"+county+"区"+addr;
		}
		System.out.println(addr);
		try {
			adao.change(name,phone,addr,sheng,shi,xian,aid);
			response.getWriter().append("编辑成功");
		} catch (Exception e) {
			response.getWriter().append("编辑失败");
		}
		
	}
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		
		String name=request.getParameter("name");
		String addr=request.getParameter("addr");
		String phone=request.getParameter("phone");
		String sheng=request.getParameter("sheng");
		String shi=request.getParameter("shi");
		String xian=request.getParameter("xian");
		String province=AreaConstants.PROVINCES[Integer.valueOf(sheng)];
		String city=AreaConstants.CITYS[Integer.valueOf(sheng)][Integer.valueOf(shi)];
		String county=AreaConstants.COUNTYS[Integer.valueOf(sheng)][Integer.valueOf(shi)][Integer.valueOf(xian)];
		if(province.equals("北京")||province.equals("上海")||province.equals("天津")||province.equals("重庆")) {
			addr=province+"市"+county+"区"+addr;
		}else if(province.equals("内蒙古")||province.equals("西藏")||province.equals("广西")||province.equals("宁夏")||province.equals("新疆")){
			addr=province+"自治区"+city+"市"+county+"区"+addr;
		}else if(county.lastIndexOf("区")!=-1){
			addr=province+"省"+city+"市"+county+addr;
		}else if(county.lastIndexOf("区")==-1){
			addr=province+"省"+city+"市"+county+"区"+addr;
		}
		System.out.println(addr);
		try {
			adao.insert(name,phone,addr,sheng,shi,xian,uid);
			response.getWriter().append("添加成功");
		} catch (Exception e) {
			response.getWriter().append("添加失败");
		}
		
	}


}
