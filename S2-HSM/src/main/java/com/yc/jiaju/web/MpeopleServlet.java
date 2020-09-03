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

import org.apache.commons.beanutils.BeanUtils;

import com.yc.jiaju.dao.mpeopleDao;
import com.yc.jiaju.po.Result;

import com.yc.jiaju.po.manager;

/**
 * Servlet implementation class MpeopleServlet
 */
@WebServlet("/MpeopleServlet.do")
public class MpeopleServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
    private mpeopleDao mdao = new mpeopleDao();
    
	//人员管理
	protected void querym(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		
		/**
		 * bean/op 要装载的实体对象
		 * properties 存放属性值的map集合
		 */
		@SuppressWarnings("unchecked")
		Map<String,Object>Manager=(Map<String, Object>) request.getSession().getAttribute("Manager");
		if(Manager!=null) {
			Integer mid =  (Integer) Manager.get("id") ;
			if(mid!=1) {
				print(response,0);
				return;
			}
			
		}
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("rows"));
		manager mg=new manager();
		//装载方法
		BeanUtils.populate(mg, request.getParameterMap());
		int total=mdao.queryp(mg);
		List<Map<String,Object>>list=mdao.query(mg,page,size);
		String pwd = "";
		for(int i=0; i<list.size() ; i++) {
			pwd = list.get(i).get("password").toString().substring(0,1);
			list.get(i).put("password", pwd+"***");
		}
		HashMap<String, Object> data = new HashMap<String, Object>();
		data.put("rows", list);
		data.put("total", total);
		print(response,data);
	}

	
	protected void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		manager mg=new manager();
		//装载方法
		BeanUtils.populate(mg, request.getParameterMap());
		if(mg.getName()==null||mg.getName().trim().isEmpty()) {
			print(response,new Result(0,"商品名不能为空", mg));
			return;
		}
		if(mg.getPassword()==null||mg.getPassword().trim().isEmpty()) {
			print(response,new Result(0,"商品价格必须大于0", mg));
			return; 
		}
		//System.out.println("sid"+sp.getSid());
		if(mg.getId()==null||mg.getId()==0) {
			mdao.insert(mg);
			print(response,new Result(1,"管理员添加成功", mg));
		}else {
			mdao.update(mg);
			print(response,new Result(1,"管理员修改成功", mg));
		}
	}
	protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sid=Integer.parseInt(request.getParameter("id"));
		mdao.delete(sid);
		response.getWriter().append("删除成功");
	}

}
