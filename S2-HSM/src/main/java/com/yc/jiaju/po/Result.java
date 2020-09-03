package com.yc.jiaju.po;
/**
 * ���ؽ����
 * @author 23163
 *
 */
public class Result {
	//���ؽ��,0 fail 1 success
	private int code;
	//���ص���Ϣ
	private String msg;
	//���ظ������������,list map ʵ�����
	private Object data;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Result(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Result [code=" + code + ", msg=" + msg + ", data=" + data + "]";
	}
	
}