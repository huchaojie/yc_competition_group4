package com.yc.jiaju.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016102600764887";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC8TYovvLnOu65+Y9ecPzh3nDxX/E1PfOkAv7Oh+iwQrtQEkwZxEORdKuj2T815YEbgtHR/dQSLlA690gQuErllKom3PoKMgMh3tZYNwNPWRblhIODIg1pXYlzhcWNRd1Upd2qLlXQoDP0m/8l/NRkiMsxdHSc6pbl9Yta5k2PXoR5uccs2FJW1tts6ScDFFKjNoK8HG7jZcrXTYmXWkhf5caD2QF7kcI6zdERW26/Q1Nq5YmF4vFUrnpU//jgrOrQRAnyO++w3atNrRUhvyxMNOZkRFjhRdL8y3CCXQBR6VgOYW+A+UjMtWdfkjLTzmBKiGPIYCeEWKZ464bg98WmjAgMBAAECggEAPTA1Jy38cuEp4ogvF+azQqrSMQUbWikMzeF9TbUtfH5TBul/vl3u1xeKe/+SlU5jucDp2kD8KR8Da0tDcHVFKUqZVaJKab2vZrbq+60dKROfGaDFamLEiIC+DcP6sg5E7iSPyqvWAu21lWao410tvZ8kKV2/0Z7ol4X2oHb0iqyRsqWL598UMrLFnkDpwzDiE2iWZjzS9xpwRKEjkqYYGlKh5U0w7Izx7G+eTF/zx0F08HULQalylG5qRwi1S1N7LtJdus33Dng/ZpP7Uoh62pcp31iWLXJdlQKsU5rHs0i4CazNsjhibzwV9cpIXeODpKyB2w/YJlMnzXO/yvZqgQKBgQDekylARRSTn3OMkN3IbJfJP2AFfGc6lBhS7eSAzu/8gM8CWVXQ/9N+tdFO2AJ9Qg8g9WX93E8u+w/hXJ7uMWZAsaJPWDYFgySRM1zKAAVUeq1mhGL0mrzcXs0Xd9X1zT1Mz6NhVcVP7X7UG60XcUcWzLl4Gt/f2P4sNvTWZetn1wKBgQDYlM0UfjxBuqB9lGDpGgryWmLHwLZ/ke8qjZO48G/HOQj89HC6+ZqUlxpFXQSp6X4Ho42nnhnRApO4Dde8Xh/EtAiDOPDFFpEaazis51wu3DA0+MOL9bO24w7TxR+Mt0kL9dyQ//E7P30XkqG0fb/xio1d0Rk7opZoMuivFRyjFQKBgQCYvUh7sGxUegHjnq50EgF/u5v7Z8m3a1Xd4x8CPgB2uTZWGFRJ3qWWuNLwVlAvARw8s+rP8VXtmaDmOJdgQko3by9BX/mCguKfOi4c+TKvLJi+V29JCL8+Q1hfGH+d9MBya7Nk4mVLWmHE0VFRWw072jYU8+xaQyD52qTeVIbddQKBgB2pw6za3UXjWbfZaqyCdJ1c48qYCmbOhgM+AM2DoMuYdDKxB/+T0Cgpyd9WLh1XbXt+Wxhp/v8xayn6O2IBQjier8vJMLDNtnH33XtQ9L82SSP4LlAtBsA1uQIoLXoIYGjS87KoW1HSED/6RUuDLZbXcyU2Tk5spkz/K/9O1O1BAoGARZ6qR9cYpvJaz4iLn/clmEsWU5jKriFulav1Yz4MCYjYCoXrPZDEGfBdFGJkvBQbEH4NSauQ/CdWbKvYoxxBc5qXA5/fkk92SYcxKck8WVY6Ie01o6JJX4os6gWNVttlGeF/kV6w+koOfjn5uEBUawgN6fVY34hGXglQzrQXakI=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://39.99.144.135:8080/S2-plhy-jiaju/NotifyurlSrtvlet";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://39.99.144.135:8080/S2-plhy-jiaju/ok.html";
	// 请求网关地址
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
