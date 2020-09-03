package com.yc.jiaju.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.yc.jiaju.config.AlipayConfig;


@WebServlet("/GopayServlet")
public class GopayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("WIDout_trade_no")!=null){
			// �̻������ţ��̻���վ����ϵͳ��Ψһ�����ţ�����
		    String out_trade_no = new String(request.getParameter("WIDout_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			// �������ƣ�����
		    String subject = new String(request.getParameter("WIDsubject").getBytes("ISO-8859-1"),"UTF-8");
			System.out.println(subject);
		    // ���������
		    String total_amount=new String(request.getParameter("WIDtotal_amount").getBytes("ISO-8859-1"),"UTF-8");
		    // ��Ʒ�������ɿ�
		    String body = new String(request.getParameter("WIDbody").getBytes("ISO-8859-1"),"UTF-8");
		    // ��ʱʱ�� �ɿ�
		   String timeout_express="2m";
		    // ���۲�Ʒ�� ����
		    String product_code="QUICK_WAP_WAY";
		    /**********************/
		    // SDK ���������࣬������������������Լ���װ��ǩ������ǩ�������������עǩ������ǩ     
		    //����RSAǩ����ʽ
		    AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
		    AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();
		    
		    // ��װ����֧����Ϣ
		    AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
		    model.setOutTradeNo(out_trade_no);
		    model.setSubject(subject);
		    model.setTotalAmount(total_amount);
		    model.setBody(body);
		    model.setTimeoutExpress(timeout_express);
		    model.setProductCode(product_code);
		    alipay_request.setBizModel(model);
		    // �����첽֪ͨ��ַ
		    alipay_request.setNotifyUrl(AlipayConfig.notify_url);
		    // ����ͬ����ַ
		    alipay_request.setReturnUrl(AlipayConfig.return_url);   
		    
		    // form������
		    String form = "";
			try {
				// ����SDK���ɱ�
				form = client.pageExecute(alipay_request).getBody();
				response.setContentType("text/html;charset=" + AlipayConfig.CHARSET); 
			    response.getWriter().write(form);//ֱ�ӽ������ı�html�����ҳ�� 
			    response.getWriter().flush(); 
			    response.getWriter().close();
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
