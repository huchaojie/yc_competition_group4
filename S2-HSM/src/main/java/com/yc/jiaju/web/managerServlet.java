package com.yc.jiaju.web;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.yc.jiaju.dao.managerDao;
import com.yc.jiaju.po.Product;
import com.yc.jiaju.po.Result;
import com.yc.jiaju.po.Samepdt;
import com.yc.jiaju.po.manager;
import com.yc.jiaju.util.DBHelper;



@WebServlet("/managerServlet.do")
public class managerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private managerDao mdao=new managerDao();
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String pwd=request.getParameter("pwd");
		/*
		 * if(mdao.login(name, pwd)) { response.getWriter().append("login success");
		 * }else print(response,"login failure");
		 */
		Map<String,Object>Manager=mdao.login(name, pwd);
		if(Manager!=null) {
			request.getSession().setAttribute("Manager",Manager);
			String sql="update tb_manager set statu=1 where id=?";
			new DBHelper().update(sql, Manager.get("id"));
			response.getWriter().append("login success");
		}else print(response,"login failure");
	}
	protected void getManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		print(response,Manager);
		/*
		 * if(Manager!=null) { Manager.clear(); }
		 */
	}
	protected void queryProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("rows"));
		/**
		 * bean/op Ҫװ�ص�ʵ�����
		 * properties �������ֵ��map����
		 */
		Samepdt sp=new Samepdt();
		//װ�ط���
		BeanUtils.populate(sp, request.getParameterMap());
		int total=mdao.lastPage(sp);
		List<Map<String,Object>>list=mdao.queryProduct(sp,page,size);
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response,data);
	}
	//����
	protected void cateGory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<?>list=mdao.query();
		print(response,list);
	}
	//������Ʒ
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		Samepdt sp=new Samepdt();
		//װ�ط���
		BeanUtils.populate(sp, request.getParameterMap());
		if(sp.getSname()==null||sp.getSname().trim().isEmpty()) {
			print(response,new Result(0,"��Ʒ������Ϊ��", sp));
			return;
		}
		if(sp.getPrice()==null||sp.getPrice()<=0) {
			print(response,new Result(0,"��Ʒ�۸�������0", sp));
			return; 
		}
		System.out.println("sid"+sp.getSid());
		if(sp.getSid()==null||sp.getSid()==0) {
			mdao.insert(sp);
			print(response,new Result(1,"��Ʒ��ӳɹ�", sp));
		}else {
			mdao.update(sp);
			print(response,new Result(1,"��Ʒ�޸ĳɹ�", sp));
		}
	}
	//ɾ��
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sid=Integer.parseInt(request.getParameter("sid"));
		mdao.delete(sid);
		response.getWriter().append("ɾ���ɹ�");
	}
	//�˳���¼
	  protected void quitManager(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    @SuppressWarnings("unchecked")
	    Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
	    String sql="update tb_manager set statu=0 where id=?";
	    new DBHelper().update(sql, Manager.get("id"));
	    Manager.clear(); 
	    print(response,"�˳��ɹ�");
	  }
	  protected void gettree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		String tree="[{'id':1,'text': '��ѼҾӺ�̨����','children': [{'id'=11:,'text': '��Ʒ����',},{'id':12,'text': '��������','url':'managerorder.html'},{'id':13,'text': '��Ա����','url':'managerm.html'},{'id':14,'text': '��ϵ�ͻ�','url':'mchat.html'}]}]";
		//String tree1="[{'text': '��ѼҾӺ�̨����','children': [{'text': '��Ʒ����',},{'text': '��������','url':'managerorder.html'},{'text': '��ϵ�ͻ�','url':'mchat.html'}]}]";;
		Integer mid=(Integer) Manager.get("id");
		if(mid==1) {
			response.getWriter().append(tree);
		}else {
			response.getWriter().append(tree);
		}
	  }
	  protected void getmproduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String sid=request.getParameter("sid");
		    print(response, mdao.queryBySid(sid));
	  }
	  protected void savep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
			Product pt=new Product();
			//װ�ط���
			BeanUtils.populate(pt, request.getParameterMap());
			if(pt.getPname()==null||pt.getPname().trim().isEmpty()) {
				print(response,new Result(0,"��Ʒ������Ϊ��", pt));
				return;
			}
			if(pt.getPrice()==null||pt.getPrice()<=0) {
				print(response,new Result(0,"��Ʒ�۸�������0", pt));
				return; 
			}
			System.out.println("pid"+pt.getPid());
			if(pt.getPid()==null||pt.getPid()==0) {
				mdao.insertp(pt);
				print(response,new Result(1,"��Ʒ��ӳɹ�", pt));
			}else {
				mdao.updatep(pt);
				print(response,new Result(1,"��Ʒ�޸ĳɹ�", pt));
			}
		}
	  protected void deletep(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String pid=request.getParameter("pid");
		    try {
		    	mdao.deletep(pid);
		    	print(response,"��Ʒɾ���ɹ�");
			} catch (Exception e) {
				print(response,"��Ʒɾ��ʧ��");
			}
	  }
	
}
