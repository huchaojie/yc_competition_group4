package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.yc.jiaju.dao.ProductMapper;
import com.yc.jiaju.dao.productDao;
import com.yc.jiaju.util.MyBatisHelper;

@WebServlet("/queryProductServlet.do")
public class queryProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	productDao pdao = new productDao();
	//鏁版嵁搴撳晢鍝佹暟鎹煡锟�?
	protected void queryProduct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		List<Map<String, Object>> list = pdao.queryProduct(a,b);
		HashMap<String, Object> page = new HashMap<String, Object>();
		page.put("list", list);
		print(response, page);
	}
	/**
	 * 鏌ヨ鎶楃柅鍟嗗搧
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void queryPagefight(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		int begin = (page - 1) * size;
		SqlSession session = MyBatisHelper.openSession();
		ProductMapper p = session.getMapper(ProductMapper.class);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list", p.queryPagefight( begin, size));
		print(response, page1);
	}
	//鍒涙剰瀹跺眳鍒嗛〉鏌ヨ
	protected void queryPageidea(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		//鎬婚〉锟�?
		int pages=pdao.countPages(size*9);
		int begin = (page - 1) * size;
		SqlSession session = MyBatisHelper.openSession();
		ProductMapper p = session.getMapper(ProductMapper.class);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list", p.queryPageidea(begin, size));
		page1.put("pages", pages);
		print(response, page1);
	}
	//鍒嗛〉鏌ヨ
	protected void queryPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page=Integer.parseInt(request.getParameter("page"));
		int size=Integer.parseInt(request.getParameter("size"));
		int a=Integer.parseInt(request.getParameter("a"));
		int b=Integer.parseInt(request.getParameter("b"));
		//鎬婚〉锟�?
		int pages=pdao.countPages(size,a,b);
		System.out.println(pages);
		int begin = (page - 1) * size;
		SqlSession session = MyBatisHelper.openSession();
		ProductMapper p = session.getMapper(ProductMapper.class);
		HashMap<String, Object> page1 = new HashMap<String, Object>();
		page1.put("list",p.queryPage(a, b, begin, size));
		page1.put("pages", pages);
		print(response, page1);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
