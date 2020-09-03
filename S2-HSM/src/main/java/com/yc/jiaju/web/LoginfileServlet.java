package com.yc.jiaju.web;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yc.jiaju.dao.UserDao;
import com.yc.jiaju.util.DBHelper;

@WebServlet("/LoginfileServlet")
public class LoginfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	private UserDao ud = new UserDao();
	static Cookie cookie = new Cookie("email", "");
	
	//登录
	protected void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String vcode = request.getParameter("vcode");
		String remb = request.getParameter("remb");
		String vcode1 = (String) request.getSession().getAttribute("vcode");
		System.out.println(remb+"+++++++++++++++++++++++++++++++++++++++++++++");
		if(email.trim().isEmpty()==true||email==null) {
			response.getWriter().append("请输入账号");
			return;
		}
		
		if(pass.trim().isEmpty()==true||pass==null) {
			response.getWriter().append("请输入密码");
			return;
		} 
		if(vcode.trim().isEmpty()==true||vcode==null) {
			response.getWriter().append("请输入验证码");
			return;
		}
		if(vcode.equals(vcode1)==false) {
			response.getWriter().append("验证码错误");
			return;
		}
		if(ud.checkemail(email)==false) {
			response.getWriter().append("该账号不存在,请重新输入");
			return;
		}
		if(ud.querylogin(email, pass)==false) {
			response.getWriter().append("账号或密码错误");
			return;
		}else {
			SaveLogined(email,pass,request, response);//存入会话
			if(remb.equals("true")) {
				cookie.setValue(email);
				cookie.setMaxAge(7*24*60*60);
				System.out.println("存就去了"+"-----------------------------------------");
			}else {
				cookie.setValue("");
				System.out.println("没用存进去了-----------------------------------------");
			}
			response.addCookie(cookie);
			response.getWriter().append("登录成功");
		}
	}
	
	protected void GetCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] coo = request.getCookies();//获取request中cookie集合
		String email = null;
		for(Cookie c : coo) {
			if(c.getName().equals("email")) {
				email=c.getValue();
			}
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(email);
		response.getWriter().print(json);
		
	}
	
	//退出登录
	protected void Loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		int uid = (int) user.get("uid");
		String sql="update tb_user set statu=0 where uid=?";
		try {
			new DBHelper().update(sql, uid);
			request.getSession().removeAttribute("loginedUser"); //存入空值
			response.getWriter().append("退出登录成功");
		} catch (Exception e) {
			response.getWriter().append("退出登录失败");
		}
		
		
	}
	
	//修改个人信息保存
	protected void Mupdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		int uid = (int) user.get("uid");
		String name = request.getParameter("uname");
		String birth = request.getParameter("birth");
		String sex = request.getParameter("sex");
		System.out.println(sex);
		if(sex.equals("男")) {
			sex = "1";
		}else {
			sex = "0";
		}
		//birth = birth.replace("/", "-");
		if(name.trim().isEmpty()==true||name==null) {
			response.getWriter().append("请输入姓名");
			return;
		}else if(name.length()>4) {
			response.getWriter().append("姓名超长，请修正");
			return;
		}else if(birth.trim().isEmpty()==true||birth==null) {
			response.getWriter().append("请输入生日");
			return;
		}
		ud.updata(uid, name, sex, birth);
		SaveLogined(uid,request, response);
		System.out.println(user);
		response.getWriter().append("修改成功");
		
	}
	
	//原密码修改密码 验证码 
	protected void RCheckpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String pass = request.getParameter("pass");
		String rpass = request.getParameter("rpass");
		String npass = request.getParameter("npass");
		String vcode = request.getParameter("vcode");
		String vcode1 = (String) request.getSession().getAttribute("vcode");
		
		int uid = (int) user.get("uid");
		if(pass.trim().isEmpty()) {
			response.getWriter().append("请输入原密码！！");
			return;
		}else if(pass.equals(user.get("upass"))==false){
			response.getWriter().append("原密码错误！！");
			return;
		}
		if(rpass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("请输入密码！！");
			return;
		}else if(npass.trim().length()>16||npass.trim().length()<6) {
			response.getWriter().append("请按规格长度输入新密码！！");
			return;
		}else if(npass.equals(rpass)==false) {
			response.getWriter().append("密码不一致，请重新确认");
			return;
		}
		if(vcode.trim().isEmpty()) {
			response.getWriter().append("请输入验证码");
			return;
		}else if(vcode.equals(vcode1)==false) {
			response.getWriter().append("验证码错误");
			return;
		}
		ud.changepwd(uid, npass);
		SaveLogined(uid,request, response);
		response.getWriter().append("密码修改成功");
	}
	
	//忘记密码第一步发送邮箱
	protected void Fcheckemail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true||em==null) {
			response.getWriter().append("请输入邮箱");
			return;
		}else if(ud.checkemail(em)==false) {
			response.getWriter().append("该邮箱不存在");
			return;
		}
		sendemail(em,request,response);
	}
	
	//忘记密码检查验证码
	protected void Fcheckvco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ver=request.getParameter("vcode");
		System.out.println(ver);
		if(ver.trim().isEmpty()==true||ver==null) {
			response.getWriter().append("请输入验证码");
			return;
		}
		int verify=Integer.parseInt(ver);
		Integer vcode=(Integer)request.getSession().getAttribute("vcode");
		if(ver!=null&&ver.trim().isEmpty()==false) {
			if(verify!=vcode) {
				response.getWriter().append("验证码错误，请重新输入");
				return;
			}
		}else {
			response.getWriter().append("请输入验证码");
			return;
		}
		response.getWriter().append("验证成功");
	}
	
	//注册发送邮箱
	protected void Rsendemail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("请输入邮箱");
			return;
		}
		String pass = request.getParameter("pass");
		
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("请输入密码！！");
			return;
		}
		if(pass.equals(npass)==false) {
			response.getWriter().append("密码不一致，请重新确认");
			return;
		}
		sendemail(em,request,response);
	}
	
	//忘记密码更改密码
	protected void Fchangepwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("请输入密码！！");
			return;
		}else if(pass.equals(npass)==false) {
			response.getWriter().append("密码不一致，请重新确认");
			return;
		}
		ud.changepwd(email, pass);
		response.getWriter().append("密码修改成功");
	}
	
	//注册
	protected void Reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rd = new Random();
		String email = request.getParameter("email");
		if(email.trim().isEmpty()==true||email==null) {
			response.getWriter().append("请输入邮箱");
			return;
		}
		String pass = request.getParameter("pass");
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("请输入密码！！");
			return;
		}
		String ver=request.getParameter("ver");
		if(ver.trim().isEmpty()==true||ver==null) {
			response.getWriter().append("请输入验证码");
			return;
		}
		int verify=Integer.parseInt(ver);
		Integer vcode=(Integer)request.getSession().getAttribute("vcode");
		if(pass.equals(npass)==false) {
			response.getWriter().append("密码不一致，请重新确认");
			return;
		}
		if(ver!=null&&ver.trim().isEmpty()==false) {
			if(verify==vcode) {
				if(ud.checkemail(email)==true) {
					response.getWriter().append("该邮箱已被注册");
					return;
				}else {
					String name = "tb_"+String.valueOf(new Date().getTime()).substring(8,13);
					String head = "img/head/"+(rd.nextInt(15)+1)+".gif";
					ud.insert(email,name,head, pass);
					response.getWriter().append("注册成功");
				}
			}else {
				response.getWriter().append("验证码错误，请重新输入");
				return;
			}
		}else {
			response.getWriter().append("请输入验证码");
			return;
		}
	}
	

	
	//设置日期格式
	protected void GetLoginedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(user);
		response.getWriter().print(json);
		
	}
	
	//修改密码邮箱发送验证码
	protected void Remail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("请输入邮箱");
			return;
		}else if((int) user.get("uid")!=ud.checkuid(em)){
			response.getWriter().append("请输入与本账号绑定的邮箱");
			return;
		}
		sendemail(em,request,response);
		
	}
	
	protected void Remailpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("请先登录");
			return;
		}
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("请输入邮箱");
			return;
		}
		String rpass = request.getParameter("rpass");
		String npass = request.getParameter("npass");
		String vcode = request.getParameter("vcode");
		int vcode2 = 0;
		if(vcode.trim().isEmpty()) {
			response.getWriter().append("请输入验证码");
			return;
		}else {
			vcode2=Integer.parseInt(vcode);
		}
		int vcode1 = (int) request.getSession().getAttribute("vcode");
		
		int uid = (int) user.get("uid");
		if(vcode2!=vcode1) {
			response.getWriter().append("验证码错误");
			return;
		}else if(npass.trim().isEmpty()) {
			response.getWriter().append("请输入新密码！！");
			return;
		}else if(npass.trim().length()>16||npass.trim().length()<6) {
			response.getWriter().append("请按规格长度输入新密码！！");
			return;
		}else if(rpass.trim().isEmpty()) {
			response.getWriter().append("请输入确认密码！！");
			return;
		}else if(npass.equals(rpass)==false) {
			response.getWriter().append("密码不一致，请重新确认");
			return;
		}
		
		ud.changepwd(uid, npass);
		SaveLogined(uid,request, response);
		response.getWriter().append("密码修改成功");
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//发送email
	public void sendemail(String em,HttpServletRequest request, HttpServletResponse response) {
		Random random=new Random();
		int vcode=1000+random.nextInt(8999);
		HttpSession session=request.getSession();
		session.setAttribute("vcode", vcode);
		
		HtmlEmail email=new HtmlEmail();//创建对象
		
		email.setHostName("smtp.qq.com");//在QQ邮箱设置 账户 打开smtp服务
		email.setSSLOnConnect(true);   //使用ssl加密true
		email.setSslSmtpPort("465");  //使用465端口
		email.setCharset("utf-8");//设置字符集
		
		try {
			email.addTo(em);
			email.setFrom("1481638917@qq.com","pk send email"); // 设置发件人信息
			email.setAuthentication("1481638917@qq.com", "bmscgyeetenkijbi");
			
			email.setSubject("验证码获取");
			email.setMsg("验证码为:"+vcode+"请及时填写");
			email.send();
			response.getWriter().append("验证码已发送");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//保存登录者信息
	public void SaveLogined(String email,String pass,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> user = ud.selectByLogin(email, pass);
		String yemail = user.get("email").toString();
		user.put("email", yemail.substring(0,4)+"******"+yemail.substring(yemail.length()-3,yemail.length()));
		if(user.get("sex").equals("1")) {
			user.put("sex", "男");
		}else {
			user.put("sex", "女");
		}
		request.getSession().setAttribute("loginedUser", user); //存入已登录人
	}
	
	//保存登录者信息
	public void SaveLogined(int uid,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> user = ud.selectByLogin(uid);
		String yemail = user.get("email").toString();
		user.put("email", yemail.substring(0,4)+"******"+yemail.substring(yemail.length()-3,yemail.length()));
		if(user.get("sex").equals("1")) {
			user.put("sex", "男");
		}else {
			user.put("sex", "女");
		}
		request.getSession().setAttribute("loginedUser", user); //存入已登录人
	}
	
	public void Checkpass(String pass,String upass,HttpServletRequest request, HttpServletResponse response) {
	}
}
