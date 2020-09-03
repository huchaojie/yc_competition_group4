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
			response.getWriter().append("���ȵ�¼");
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
			response.getWriter().append("���ȵ�¼");
			return;
		}
		String uid =  String.valueOf(user.get("uid")) ;
		String aid=request.getParameter("aid");
		try {
			adao.setdft(aid,uid);
			response.getWriter().append("����ΪĬ�ϵ�ַ�ɹ�");
		} catch (Exception e) {
			response.getWriter().append("����ΪĬ�ϵ�ַʧ��");
		}	
	}
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aid=request.getParameter("aid");
		try {
			adao.delete(aid);
			response.getWriter().append("ɾ���ɹ�");
		} catch (Exception e) {
			response.getWriter().append("ɾ��ʧ��");
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
		if(province.equals("����")||province.equals("�Ϻ�")||province.equals("���")||province.equals("����")) {
			addr=province+"��"+county+"��"+addr;
		}else if(province.equals("���ɹ�")||province.equals("����")||province.equals("����")||province.equals("����")||province.equals("�½�")){
			addr=province+"������"+city+"��"+county+"��"+addr;
		}else if(county.lastIndexOf("��")!=-1){
			addr=province+"ʡ"+city+"��"+county+addr;
		}else if(county.lastIndexOf("��")==-1){
			addr=province+"ʡ"+city+"��"+county+"��"+addr;
		}
		System.out.println(addr);
		try {
			adao.change(name,phone,addr,sheng,shi,xian,aid);
			response.getWriter().append("�༭�ɹ�");
		} catch (Exception e) {
			response.getWriter().append("�༭ʧ��");
		}
		
	}
	protected void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("���ȵ�¼");
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
		if(province.equals("����")||province.equals("�Ϻ�")||province.equals("���")||province.equals("����")) {
			addr=province+"��"+county+"��"+addr;
		}else if(province.equals("���ɹ�")||province.equals("����")||province.equals("����")||province.equals("����")||province.equals("�½�")){
			addr=province+"������"+city+"��"+county+"��"+addr;
		}else if(county.lastIndexOf("��")!=-1){
			addr=province+"ʡ"+city+"��"+county+addr;
		}else if(county.lastIndexOf("��")==-1){
			addr=province+"ʡ"+city+"��"+county+"��"+addr;
		}
		System.out.println(addr);
		try {
			adao.insert(name,phone,addr,sheng,shi,xian,uid);
			response.getWriter().append("��ӳɹ�");
		} catch (Exception e) {
			response.getWriter().append("���ʧ��");
		}
		
	}


}
