package com.yc.jiaju.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.google.gson.Gson;
import com.yc.jiaju.dao.AddrMapper;
import com.yc.jiaju.dao.YqMapper;
import com.yc.jiaju.dao.ordersDao;
import com.yc.jiaju.dao.payDao;
import com.yc.jiaju.po.Addr;
import com.yc.jiaju.po.Result;
import com.yc.jiaju.po.Yq;
import com.yc.jiaju.util.MyBatisHelper;


@WebServlet("/PayServlet.do")
public class PayServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private payDao pdao = new payDao();
    private ordersDao odao = new ordersDao();
	protected void getproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String pids = request.getParameter("pid");
		String arr[]=pids.split(",");
		HashMap<String, Object> page = new HashMap<>();
		List<Map<String,Object>> list=new ArrayList<>();
		for (String pid : arr) {
			list.addAll(pdao.listProductFromCart(uid, pid));
		}
		
		page.put("list", list);
		System.out.println(page);
		print(response, page);
		
	}
	protected void listproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String count = request.getParameter("count");
		
		HashMap<String, Object> page = new HashMap<>();
		
		List<Map<String,Object>> list=	pdao.listProduct( pid);
		//Map<String,Object> map=new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			map.put("count", count);
		}
		page.put("list", list);
		System.out.println(list);
		print(response, page);
		
	}

	protected void creatorder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] pids = request.getParameter("pid").split(",");
		System.out.println("pids===>"+pids.toString());
		String pay = request.getParameter("pay");
		String total = request.getParameter("total");
		String aid = request.getParameter("aid");
		//Double total =Math.floor((Double.valueOf(price)*Integer.valueOf(count)) * 100) / 100 ;
		System.out.println(System.currentTimeMillis());
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		SqlSession session = MyBatisHelper.openSession();
		AddrMapper am = session.getMapper(AddrMapper.class);
		YqMapper ym = session.getMapper(YqMapper.class);
		Addr addr = am.selectByAid(Integer.parseInt(aid));
		List<Yq> list  = ym.query();
		for (Yq yq : list) {
			if(yq.getProvince().equals(AreaConstants.PROVINCES[addr.getSheng()])) {
				if(yq.getCity().equals(AreaConstants.CITYS[addr.getSheng()][addr.getShi()])) {
					response.getWriter().append(new Gson().toJson(new Result(0,"因疫情原因，该市无法配送货物!")));
					return;
				}
			}
		}
		odao.addorder(uid, aid, total, "支付宝",System.currentTimeMillis()+"");
		System.out.println("执行生成订单完成");
		for (String pid : pids) {
			odao.addorderitem(uid,pid);//1为uid后期补
			odao.cutproductstock(pid);
			odao.cutcart(pid);
			odao.addeval(uid);
		}
		response.getWriter().append(new Gson().toJson(new Result(1,"跳转至支付页面")));
	}
	protected void creatorder2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		String pay = request.getParameter("pay");
		String total = request.getParameter("total");
		String aid = request.getParameter("aid");
		String count = request.getParameter("count");
		//Double total =Math.floor((Double.valueOf(price)*Integer.valueOf(count)) * 100) / 100 ;
		System.out.println(System.currentTimeMillis());
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		SqlSession session = MyBatisHelper.openSession();
		AddrMapper am = session.getMapper(AddrMapper.class);
		YqMapper ym = session.getMapper(YqMapper.class);
		Addr addr = am.selectByAid(Integer.parseInt(aid));
		List<Yq> list  = ym.query();
		for (Yq yq : list) {
			if(yq.getProvince().equals(AreaConstants.PROVINCES[addr.getSheng()])) {
				if(yq.getCity().equals(AreaConstants.CITYS[addr.getSheng()][addr.getShi()])) {
					response.getWriter().append(new Gson().toJson(new Result(0,"因疫情原因，该市无法配送货物!")));
					return;
				}
			}
		}
		
		
		odao.addorder(uid, aid, total, "支付宝",System.currentTimeMillis()+"");
		
		odao.addorderitem2(uid,pid,count);//1为uid后期补
		odao.cutproductstock2(pid,count);
		odao.addeval(uid);
		response.getWriter().append(new Gson().toJson(new Result(1,"跳转至支付页面")));	
	}
	protected void paylist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String oid = request.getParameter("oid");
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", odao.payByOid(oid,uid));
		print(response,page );
			
	}

}
