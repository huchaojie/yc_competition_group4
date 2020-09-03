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
	
	//��¼
	protected void Login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String vcode = request.getParameter("vcode");
		String remb = request.getParameter("remb");
		String vcode1 = (String) request.getSession().getAttribute("vcode");
		System.out.println(remb+"+++++++++++++++++++++++++++++++++++++++++++++");
		if(email.trim().isEmpty()==true||email==null) {
			response.getWriter().append("�������˺�");
			return;
		}
		
		if(pass.trim().isEmpty()==true||pass==null) {
			response.getWriter().append("����������");
			return;
		} 
		if(vcode.trim().isEmpty()==true||vcode==null) {
			response.getWriter().append("��������֤��");
			return;
		}
		if(vcode.equals(vcode1)==false) {
			response.getWriter().append("��֤�����");
			return;
		}
		if(ud.checkemail(email)==false) {
			response.getWriter().append("���˺Ų�����,����������");
			return;
		}
		if(ud.querylogin(email, pass)==false) {
			response.getWriter().append("�˺Ż��������");
			return;
		}else {
			SaveLogined(email,pass,request, response);//����Ự
			if(remb.equals("true")) {
				cookie.setValue(email);
				cookie.setMaxAge(7*24*60*60);
				System.out.println("���ȥ��"+"-----------------------------------------");
			}else {
				cookie.setValue("");
				System.out.println("û�ô��ȥ��-----------------------------------------");
			}
			response.addCookie(cookie);
			response.getWriter().append("��¼�ɹ�");
		}
	}
	
	protected void GetCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie[] coo = request.getCookies();//��ȡrequest��cookie����
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
	
	//�˳���¼
	protected void Loginout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		int uid = (int) user.get("uid");
		String sql="update tb_user set statu=0 where uid=?";
		try {
			new DBHelper().update(sql, uid);
			request.getSession().removeAttribute("loginedUser"); //�����ֵ
			response.getWriter().append("�˳���¼�ɹ�");
		} catch (Exception e) {
			response.getWriter().append("�˳���¼ʧ��");
		}
		
		
	}
	
	//�޸ĸ�����Ϣ����
	protected void Mupdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user=(Map<String, Object>) request.getSession().getAttribute("loginedUser");
		int uid = (int) user.get("uid");
		String name = request.getParameter("uname");
		String birth = request.getParameter("birth");
		String sex = request.getParameter("sex");
		System.out.println(sex);
		if(sex.equals("��")) {
			sex = "1";
		}else {
			sex = "0";
		}
		//birth = birth.replace("/", "-");
		if(name.trim().isEmpty()==true||name==null) {
			response.getWriter().append("����������");
			return;
		}else if(name.length()>4) {
			response.getWriter().append("����������������");
			return;
		}else if(birth.trim().isEmpty()==true||birth==null) {
			response.getWriter().append("����������");
			return;
		}
		ud.updata(uid, name, sex, birth);
		SaveLogined(uid,request, response);
		System.out.println(user);
		response.getWriter().append("�޸ĳɹ�");
		
	}
	
	//ԭ�����޸����� ��֤�� 
	protected void RCheckpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("���ȵ�¼");
			return;
		}
		String pass = request.getParameter("pass");
		String rpass = request.getParameter("rpass");
		String npass = request.getParameter("npass");
		String vcode = request.getParameter("vcode");
		String vcode1 = (String) request.getSession().getAttribute("vcode");
		
		int uid = (int) user.get("uid");
		if(pass.trim().isEmpty()) {
			response.getWriter().append("������ԭ���룡��");
			return;
		}else if(pass.equals(user.get("upass"))==false){
			response.getWriter().append("ԭ������󣡣�");
			return;
		}
		if(rpass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("���������룡��");
			return;
		}else if(npass.trim().length()>16||npass.trim().length()<6) {
			response.getWriter().append("�밴��񳤶����������룡��");
			return;
		}else if(npass.equals(rpass)==false) {
			response.getWriter().append("���벻һ�£�������ȷ��");
			return;
		}
		if(vcode.trim().isEmpty()) {
			response.getWriter().append("��������֤��");
			return;
		}else if(vcode.equals(vcode1)==false) {
			response.getWriter().append("��֤�����");
			return;
		}
		ud.changepwd(uid, npass);
		SaveLogined(uid,request, response);
		response.getWriter().append("�����޸ĳɹ�");
	}
	
	//���������һ����������
	protected void Fcheckemail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true||em==null) {
			response.getWriter().append("����������");
			return;
		}else if(ud.checkemail(em)==false) {
			response.getWriter().append("�����䲻����");
			return;
		}
		sendemail(em,request,response);
	}
	
	//������������֤��
	protected void Fcheckvco(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ver=request.getParameter("vcode");
		System.out.println(ver);
		if(ver.trim().isEmpty()==true||ver==null) {
			response.getWriter().append("��������֤��");
			return;
		}
		int verify=Integer.parseInt(ver);
		Integer vcode=(Integer)request.getSession().getAttribute("vcode");
		if(ver!=null&&ver.trim().isEmpty()==false) {
			if(verify!=vcode) {
				response.getWriter().append("��֤���������������");
				return;
			}
		}else {
			response.getWriter().append("��������֤��");
			return;
		}
		response.getWriter().append("��֤�ɹ�");
	}
	
	//ע�ᷢ������
	protected void Rsendemail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("����������");
			return;
		}
		String pass = request.getParameter("pass");
		
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("���������룡��");
			return;
		}
		if(pass.equals(npass)==false) {
			response.getWriter().append("���벻һ�£�������ȷ��");
			return;
		}
		sendemail(em,request,response);
	}
	
	//���������������
	protected void Fchangepwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("���������룡��");
			return;
		}else if(pass.equals(npass)==false) {
			response.getWriter().append("���벻һ�£�������ȷ��");
			return;
		}
		ud.changepwd(email, pass);
		response.getWriter().append("�����޸ĳɹ�");
	}
	
	//ע��
	protected void Reg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rd = new Random();
		String email = request.getParameter("email");
		if(email.trim().isEmpty()==true||email==null) {
			response.getWriter().append("����������");
			return;
		}
		String pass = request.getParameter("pass");
		String npass = request.getParameter("npass");
		if(pass.trim().isEmpty()||npass.trim().isEmpty()) {
			response.getWriter().append("���������룡��");
			return;
		}
		String ver=request.getParameter("ver");
		if(ver.trim().isEmpty()==true||ver==null) {
			response.getWriter().append("��������֤��");
			return;
		}
		int verify=Integer.parseInt(ver);
		Integer vcode=(Integer)request.getSession().getAttribute("vcode");
		if(pass.equals(npass)==false) {
			response.getWriter().append("���벻һ�£�������ȷ��");
			return;
		}
		if(ver!=null&&ver.trim().isEmpty()==false) {
			if(verify==vcode) {
				if(ud.checkemail(email)==true) {
					response.getWriter().append("�������ѱ�ע��");
					return;
				}else {
					String name = "tb_"+String.valueOf(new Date().getTime()).substring(8,13);
					String head = "img/head/"+(rd.nextInt(15)+1)+".gif";
					ud.insert(email,name,head, pass);
					response.getWriter().append("ע��ɹ�");
				}
			}else {
				response.getWriter().append("��֤���������������");
				return;
			}
		}else {
			response.getWriter().append("��������֤��");
			return;
		}
	}
	

	
	//�������ڸ�ʽ
	protected void GetLoginedUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String json = gson.toJson(user);
		response.getWriter().print(json);
		
	}
	
	//�޸��������䷢����֤��
	protected void Remail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("���ȵ�¼");
			return;
		}
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("����������");
			return;
		}else if((int) user.get("uid")!=ud.checkuid(em)){
			response.getWriter().append("�������뱾�˺Ű󶨵�����");
			return;
		}
		sendemail(em,request,response);
		
	}
	
	protected void Remailpwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		@SuppressWarnings("unchecked")
		Map<String,Object> user =
				(Map<String,Object>) request.getSession().getAttribute("loginedUser");
		if(user==null) {
			response.getWriter().append("���ȵ�¼");
			return;
		}
		String em = request.getParameter("email");
		if(em.trim().isEmpty()==true) {
			response.getWriter().append("����������");
			return;
		}
		String rpass = request.getParameter("rpass");
		String npass = request.getParameter("npass");
		String vcode = request.getParameter("vcode");
		int vcode2 = 0;
		if(vcode.trim().isEmpty()) {
			response.getWriter().append("��������֤��");
			return;
		}else {
			vcode2=Integer.parseInt(vcode);
		}
		int vcode1 = (int) request.getSession().getAttribute("vcode");
		
		int uid = (int) user.get("uid");
		if(vcode2!=vcode1) {
			response.getWriter().append("��֤�����");
			return;
		}else if(npass.trim().isEmpty()) {
			response.getWriter().append("�����������룡��");
			return;
		}else if(npass.trim().length()>16||npass.trim().length()<6) {
			response.getWriter().append("�밴��񳤶����������룡��");
			return;
		}else if(rpass.trim().isEmpty()) {
			response.getWriter().append("������ȷ�����룡��");
			return;
		}else if(npass.equals(rpass)==false) {
			response.getWriter().append("���벻һ�£�������ȷ��");
			return;
		}
		
		ud.changepwd(uid, npass);
		SaveLogined(uid,request, response);
		response.getWriter().append("�����޸ĳɹ�");
		
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//����email
	public void sendemail(String em,HttpServletRequest request, HttpServletResponse response) {
		Random random=new Random();
		int vcode=1000+random.nextInt(8999);
		HttpSession session=request.getSession();
		session.setAttribute("vcode", vcode);
		
		HtmlEmail email=new HtmlEmail();//��������
		
		email.setHostName("smtp.qq.com");//��QQ�������� �˻� ��smtp����
		email.setSSLOnConnect(true);   //ʹ��ssl����true
		email.setSslSmtpPort("465");  //ʹ��465�˿�
		email.setCharset("utf-8");//�����ַ���
		
		try {
			email.addTo(em);
			email.setFrom("1481638917@qq.com","pk send email"); // ���÷�������Ϣ
			email.setAuthentication("1481638917@qq.com", "bmscgyeetenkijbi");
			
			email.setSubject("��֤���ȡ");
			email.setMsg("��֤��Ϊ:"+vcode+"�뼰ʱ��д");
			email.send();
			response.getWriter().append("��֤���ѷ���");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//�����¼����Ϣ
	public void SaveLogined(String email,String pass,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> user = ud.selectByLogin(email, pass);
		String yemail = user.get("email").toString();
		user.put("email", yemail.substring(0,4)+"******"+yemail.substring(yemail.length()-3,yemail.length()));
		if(user.get("sex").equals("1")) {
			user.put("sex", "��");
		}else {
			user.put("sex", "Ů");
		}
		request.getSession().setAttribute("loginedUser", user); //�����ѵ�¼��
	}
	
	//�����¼����Ϣ
	public void SaveLogined(int uid,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> user = ud.selectByLogin(uid);
		String yemail = user.get("email").toString();
		user.put("email", yemail.substring(0,4)+"******"+yemail.substring(yemail.length()-3,yemail.length()));
		if(user.get("sex").equals("1")) {
			user.put("sex", "��");
		}else {
			user.put("sex", "Ů");
		}
		request.getSession().setAttribute("loginedUser", user); //�����ѵ�¼��
	}
	
	public void Checkpass(String pass,String upass,HttpServletRequest request, HttpServletResponse response) {
	}
}
