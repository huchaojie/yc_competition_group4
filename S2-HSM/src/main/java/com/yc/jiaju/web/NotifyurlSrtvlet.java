package com.yc.jiaju.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.yc.jiaju.config.AlipayConfig;
import com.yc.jiaju.dao.ordersDao;


@WebServlet("/NotifyurlSrtvlet")
public class NotifyurlSrtvlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ֧����POST����������Ϣ
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//����������δ����ڳ�������ʱʹ�á����mysign��sign�����Ҳ����ʹ����δ���ת��
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		//��ȡ֧������֪ͨ���ز������ɲο������ĵ���ҳ����תͬ��֪ͨ�����б�(���½����ο�)//
			//�̻�������

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//֧�������׺�

			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//����״̬
			String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

			//��ȡ֧������֪ͨ���ز������ɲο������ĵ���ҳ����תͬ��֪ͨ�����б�(���Ͻ����ο�)//
			//����ó�֪ͨ��֤���
			//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
			boolean verify_result = true;
			try {
				verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(verify_result){//��֤�ɹ�
				//////////////////////////////////////////////////////////////////////////////////////////
				//������������̻���ҵ���߼��������

				//�������������ҵ���߼�����д�������´�������ο�������
				
				if(trade_status.equals("TRADE_FINISHED")){
					//�жϸñʶ����Ƿ����̻���վ���Ѿ���������
						//���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
						//������ж�����ʱ��total_fee��seller_id��֪ͨʱ��ȡ��total_fee��seller_idΪһ�µ�
						//���������������ִ���̻���ҵ�����
						//System.out.println("��Ʒ�Ѿ�����");
					new ordersDao().changestatuto1(out_trade_no);
					//ע�⣺
					//���ǩԼ���ǿ��˿�Э�飬�˿����ڳ������˿����޺��������¿��˿��֧����ϵͳ���͸ý���״̬֪ͨ
					//���û��ǩԼ���˿�Э�飬��ô������ɺ�֧����ϵͳ���͸ý���״̬֪ͨ��
				} else if (trade_status.equals("TRADE_SUCCESS")){
					//�жϸñʶ����Ƿ����̻���վ���Ѿ���������
						//���û�������������ݶ����ţ�out_trade_no�����̻���վ�Ķ���ϵͳ�в鵽�ñʶ�������ϸ����ִ���̻���ҵ�����
						//������ж�����ʱ��total_fee��seller_id��֪ͨʱ��ȡ��total_fee��seller_idΪһ�µ�
						//���������������ִ���̻���ҵ�����
						//System.out.println("�������ݿ�");
					
					new ordersDao().changestatuto1(out_trade_no);
					//ע�⣺
					//���ǩԼ���ǿ��˿�Э�飬��ô������ɺ�֧����ϵͳ���͸ý���״̬֪ͨ��
				}

				//�������������ҵ���߼�����д�������ϴ�������ο�������
				//response.getWriter().clear();
				response.getWriter().println("success");	//�벻Ҫ�޸Ļ�ɾ��

				//////////////////////////////////////////////////////////////////////////////////////////
			}else{//��֤ʧ��
				response.getWriter().println("fail");
				//System.out.println("����ʧ��");
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
