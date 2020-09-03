package com.yc.jiaju.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;


@WebServlet("/ZanServlet.do")
public class ZanServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void zan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Jedis jd = new Jedis();
		//jd.auth("");
		String eid = request.getParameter("eid");
		long cnt = jd.scard("eva-zan:"+eid);
		jd.close();
		response.getWriter().append("{ \"cnt\" :"+cnt+"}");
	}

	
	protected void addzan(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user = (Map<String, Object>) request.getSession().getAttribute("loginedUser");
		
		if(user==null) {
			response.getWriter().append("ÇëÏÈµÇÂ¼");
		}else {
			Object uid = user.get("uid");
			
			Jedis jd = new Jedis();
			//jd.auth("");
			String eid = request.getParameter("eid");
			int zan=0;
			if(jd.sismember("eva-zan:"+eid,uid.toString())) {
				
				jd.srem("eva-zan:"+eid,uid.toString());
			}else {
				jd.sadd("eva-zan:"+eid,uid.toString());
				zan=1;
			}
			
			
			long cnt = jd.scard("eva-zan:"+eid);
			jd.close();
			response.getWriter().append("{ \"cnt\" :"+cnt+",\"iszan\" :"+zan+"}");
		}
	}

}
