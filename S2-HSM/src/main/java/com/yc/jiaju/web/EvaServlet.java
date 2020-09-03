package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yc.jiaju.dao.evaDao;

import redis.clients.jedis.Jedis;

@WebServlet("/EvaServlet.do")
public class EvaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private evaDao edao = new evaDao();

	protected void query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String estatu = request.getParameter("estatu");
		
		HashMap<String, Object> page = new HashMap<>();
		page.put("list", edao.query(uid));
		print(response, page);
	}

	protected void queryByPid(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		Object uid =null;
		if(user!=null) {
			uid = user.get("uid");
		}
		Jedis jd = new Jedis("39.99.144.135",6379);
		jd.auth("aaa");
		
		String sid = request.getParameter("sid");
		HashMap<String, Object> page = new HashMap<>();
		List<Map<String,Object>> list = edao.queryByPid(sid);
		for (Map<String, Object> map : list) {
			String eid= String.valueOf(map.get("eid"));
			System.out.println(eid);
			long cnt = jd.scard("eva-zan:"+eid);
			int zan=0;
			if(uid!=null) {
				if(jd.sismember("eva-zan:"+eid,uid.toString())) {
					zan=1;
				}
			}
			map.put("iszan", zan);
			map.put("cnt", cnt);
			
		}
		jd.close();
		page.put("list", list);
		print(response, page);
	}

	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String star = request.getParameter("star");
		String content = request.getParameter("content");
		String eid = request.getParameter("eid");

		try {
			edao.save(eid, star, content);
			edao.updateestatu(eid);
			edao.changeproducteva(eid);
			response.getWriter().append("ÆÀ¼Û³É¹¦");
		} catch (Exception e) {
			response.getWriter().append("ÆÀ¼ÛÊ§°Ü");
		}

	}

	protected void zan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * if(user==null) { response.getWriter().append("ÇëÏÈµÇÂ¼"); }else { //Object uid =
		 * user.get("uid"); Object uid =1; Jedis jd = new Jedis(); String eid =
		 * request.getParameter("eid");
		 * if(jd.sismember("topic-zan:"+eid,uid.toString())) {
		 * 
		 * jd.srem("topic-zan:"+eid,uid.toString()); }else {
		 * jd.sadd("topic-zan:"+eid,uid.toString()); } boolean
		 * zan=jd.sismember("topic-zan:"+eid,uid.toString()); long cnt =
		 * jd.scard("topic-zan:"+eid);
		 * response.getWriter().append("{ \"cnt\" :"+cnt+",\"zan\":"+zan+"}"); //}
		 */

	}
	

}
