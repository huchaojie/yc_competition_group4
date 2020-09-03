package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.yc.jiaju.dao.YqMapper;
import com.yc.jiaju.po.Yq;
import com.yc.jiaju.util.MyBatisHelper;

@WebServlet("/YqServlet.do")
public class YqServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private String google = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.105 Safari/537.36";
	private String url="https://voice.baidu.com/act/newpneumonia/newpneumonia/?from=osari_aladin_banner";;
	private HttpClientBuilder builder = HttpClients.custom();
	private CloseableHttpClient client = builder.build();
	private HttpGet request1 = new HttpGet(url);
	private String content = null;
	
    
	protected void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request1.setHeader("User-Agent",google);
			CloseableHttpResponse respose1 = client.execute(request1);
			HttpEntity entity =  respose1.getEntity();
			content = EntityUtils.toString(entity,"utf-8");
			
			int begin = content.indexOf("caseList");
			int end = content.lastIndexOf("caseOutsideList");
			SqlSession session = MyBatisHelper.openSession();
			YqMapper ym = session.getMapper(YqMapper.class);
			Map<String ,Object> map = new HashMap<String, Object>();
			map.put("yq", ym.query());
			System.out.println();
			String js = "{\"rows"+content.substring(begin+8,end-2)+","+new Gson().toJson(map).substring(1);
			response.getWriter().append(js);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	
	protected void subList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String area =  request.getParameter("area");
		
		try {
			request1.setHeader("User-Agent",google);
			CloseableHttpResponse respose1 = client.execute(request1);
			HttpEntity entity =  respose1.getEntity();
			content = EntityUtils.toString(entity,"utf-8");
			
			int begin = content.indexOf("caseList");
			int end = content.lastIndexOf("caseOutsideList");
			String js = "{"+content.substring(begin-1,end-2)+"}";
			Map<String,Object> jsonToMap = JSONObject.parseObject(js);
			@SuppressWarnings("rawtypes")
			List<Map> jsonToList = JSONArray.parseArray(jsonToMap.get("caseList").toString(),Map.class);
			for (@SuppressWarnings("rawtypes") Map map : jsonToList) {
				if(map.get("area").equals(area)) {
					response.getWriter().append(map.get("subList").toString());
				}
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void shexian(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int co = Integer.parseInt( request.getParameter("co"));
		System.out.println("城市编号："+co);
		
		try {
			request1.setHeader("User-Agent",google);
			CloseableHttpResponse respose1 = client.execute(request1);
			HttpEntity entity =  respose1.getEntity();
			content = EntityUtils.toString(entity,"utf-8");
			SqlSession session = MyBatisHelper.openSession();
			YqMapper ym = session.getMapper(YqMapper.class);
			Yq yq = new Yq();
			
			int begin = content.indexOf("caseList");
			int end = content.lastIndexOf("caseOutsideList");
			String js = "{"+content.substring(begin-1,end-2)+"}";
			Map<String,Object> jsonToMap = JSONObject.parseObject(js);
			@SuppressWarnings("rawtypes")
			List<Map> jsonToList = JSONArray.parseArray(jsonToMap.get("caseList").toString(),Map.class);
			if(co==2912 || co==332 || co==289 || co==2911 || co==131 ||co==132) {
				 for (@SuppressWarnings("rawtypes") Map map : jsonToList) {
						if(map.get("cityCode")!=null) {
							if(map.get("cityCode").equals(String.valueOf(co))) {
								System.out.println(map.get("area"));
								yq.setProvince((String)map.get("area"));
								yq.setCity((String)map.get("area"));
								yq.setCitycode(co);
								ym.insert(yq);
								session.commit();
								session.close();
								response.getWriter().append("设置成功");
							}
						}
						
					}
			 }else {
				 for (@SuppressWarnings("rawtypes") Map map : jsonToList) {
						//System.out.println(map.get("area"));
						@SuppressWarnings("rawtypes")
						List<Map> jsonToList1 = JSONArray.parseArray(map.get("subList").toString(),Map.class);	
						 for (@SuppressWarnings("rawtypes") Map map1 : jsonToList1) {
							 if(map1.get("cityCode")!=null) {
									if(map1.get("cityCode").equals(String.valueOf(co))) {
										System.out.println(map.get("area"));
										System.out.println(map1.get("city"));
										
										yq.setProvince((String)map.get("area"));
										yq.setCity((String)map1.get("city"));
										yq.setCitycode(co);
										
										ym.insert(yq);
										session.commit();
										session.close();
										response.getWriter().append("设置成功");
									}
							}
					}
				}
			 }
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	protected void getcity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SqlSession session = MyBatisHelper.openSession();
		YqMapper ym = session.getMapper(YqMapper.class);
		Map<String ,Object> map = new HashMap<String, Object>();
		map.put("yq", ym.query());
		print(response, map);
	}
	protected void qxsx(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int co = Integer.parseInt( request.getParameter("co"));
		SqlSession session = MyBatisHelper.openSession();
		YqMapper ym = session.getMapper(YqMapper.class);
		Yq yq = new Yq();
		yq.setCitycode(co);
		ym.qxsx(yq);
		session.commit();
		session.close();
		response.getWriter().append("设置成功");
	}
}
