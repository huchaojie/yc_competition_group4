package com.yc.jiaju.util;




/**
 * IO������
 */
public class IOHelper {

	/**
	 * 	�ر����Ĺ��߷���,  ���е�����ʵ���� Closeable ����, ���Զ���close ����, Ҳ����˵:
	 * 	Closeable ���������ĸ���,  ����ʹ�õľ���OOP��̬��
	 * @param c
	 */
	public static void close(AutoCloseable c) {
		if (c != null) {
			/**
			 * 	������δ򿪴��������� 
			 * 	1, ���ͣ�� ���� ����, eclipse ������������, ���оͰ��� try
			 * 	2, ���ͣ�� ���� ����  ctrl + 1
			 */
			try {
				c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
