package com.roncoo.education.util.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.roncoo.education.util.net.proto.user_Proto;





public class HttpClientUtil {
	public static Logger log = Logger.getLogger(HttpClientUtil.class);  
	private static HttpClient httpClient = getHttpClient();
	private static HttpClient postClient = null;
	private static HttpResponse httpResponse = null;

	/**
	 * 适合多线程的HttpClient,用httpClient4.2.1实现
	 * 
	 * @return DefaultHttpClient
	 */
	public static DefaultHttpClient getHttpClient() {
		// 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
		HttpParams params = new BasicHttpParams();

		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
		HttpProtocolParams.setUseExpectContinue(params, true);

		// 设置连接超时时间
		final int REQUEST_TIMEOUT = 15 * 1000; // 设置请求超时2秒钟
		final int SO_TIMEOUT = 15 * 1000; // 设置等待数据超时时间2秒钟
		final long CONN_MANAGER_TIMEOUT = 500L; //
		// 该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大
		//

		HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
		HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);
		// params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT,CONN_MANAGER_TIMEOUT);
		params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);// 在提交请求之前
																						// 测试连接是否可用
		// 设置访问协议
		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

		// 多连接的线程安全的管理器
		PoolingClientConnectionManager pccm = new PoolingClientConnectionManager(schreg);
		pccm.setMaxTotal(200); // 客户端总并行链接最大数 MaxtTotal是整个池子的大小；
		pccm.setDefaultMaxPerRoute(200); // 每个主机的最大并行链接数

		DefaultHttpClient httpClient = new DefaultHttpClient(pccm, params);
		httpClient.setHttpRequestRetryHandler(new DefaultHttpRequestRetryHandler(0, false));// 另外设置http
																							// client的重试次数，默认是3次；当前是禁用掉（如果项目量不到，这个默认即可）
		return httpClient;
	}

	/**
	 * 发送get请求
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static String sendGet(String url) throws IOException {
		int code = 0;
		HttpGet get = new HttpGet(url);
		try {

			String cont = null;

			if (httpClient == null) {
				httpClient = getHttpClient();
			}

			httpResponse = httpClient.execute(get);

			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println(
					new Date("yyyy mm dd hh mm ss") + ",124row WechatKit.sendGet code:" + code + ",URL " + url);
			if (code >= 200 && code < 300) {

				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					cont = EntityUtils.toString(entity);
					System.out.println(cont);
					return cont;
				} else {
					return Integer.toString(code);
				}

			} else {
				return Integer.toString(code);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// if(get!=null){
			// get.reset();
			// }
			if (httpResponse != null) {
				EntityUtils.consume(httpResponse.getEntity());
			}
		}
		return Integer.toString(code);

	}

	/**
	 * 通过post发送!
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String post(String url, JSONObject json, String token) throws ParseException, IOException {
		HttpPost post = new HttpPost(url);
		int code = 0;
		try {

			if (postClient == null) {
				postClient = getClient(true);
			}

			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");// :
													// {“Content-Type”:”application/json”,”Authorization”:”Bearer
													// ${token}”}
			// post.addHeader("Content-Type", "application/json");
			if (token != null) {
				post.addHeader("Authorization", "Bearer " + token);
			}
			post.setEntity(s);

			httpResponse = postClient.execute(post);
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("postcode:" + code);
			if (code >= 200 && code < 300) {
				HttpEntity entity = httpResponse.getEntity();
				if (entity != null) {
					String charset = EntityUtils.toString(entity);
					System.out.println("post返回的东西:" + charset);
					return charset;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				EntityUtils.consume(httpResponse.getEntity());
			}
			if (post != null) { // 不要忘记释放，尽量通过该方法实现，

				post.releaseConnection();

				// 存在风险，不要用

				// postMethod.setParameter("Connection", "close");

				// InputStream is = postMethod.getResponseBodyAsStream();

				// is.clsoe();也会关闭并释放连接的

			}
		}
		return null;

	}
	/**
	 * 通过post发送!
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static byte[] post(String url, JSONObject json) throws ParseException, IOException {
		HttpPost post = new HttpPost(url);
		int code = 0;
		byte[] responseContent=null;
		try {

			if (postClient == null) {
				postClient = getClient(true);
			}

			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("text/plain");// :
													// {“Content-Type”:”application/json”,”Authorization”:”Bearer
													// ${token}”}
			// post.addHeader("Content-Type", "application/json");
			
			post.setEntity(s);
			
			httpResponse = postClient.execute(post);
			code = httpResponse.getStatusLine().getStatusCode();
			System.out.println("postcode:" + code);
			if (code >= 200 && code < 300) {
				 responseContent = EntityUtils.toByteArray(httpResponse.getEntity()); 
				 return responseContent;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpResponse != null) {
				EntityUtils.consume(httpResponse.getEntity());
			}
			if (post != null) { // 不要忘记释放，尽量通过该方法实现，

				post.releaseConnection();

				// 存在风险，不要用

				// postMethod.setParameter("Connection", "close");

				// InputStream is = postMethod.getResponseBodyAsStream();

				// is.clsoe();也会关闭并释放连接的

			}
		}
		return null;

	}

	/**
	 * 
	 * 
	 * @param isSSL
	 * @return
	 */
	public static HttpClient getClient(boolean isSSL) {

		HttpClient httpClient = getHttpClient();
		if (isSSL) {
			X509TrustManager xtm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};

			try {
				SSLContext ctx = SSLContext.getInstance("TLS");

				ctx.init(null, new TrustManager[] { xtm }, null);

				SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);

				httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 443, socketFactory));

			} catch (Exception e) {
				throw new RuntimeException();
			}
		}

		return httpClient;
	}

	public static void main(String[] args) throws ParseException, IOException {
		System.out.println("开始");
	
		//String url="http://127.0.0.1:9000/protocol";
		//String url="http://127.0.0.1:8011/api/selectIdwq?b=false";
		String url="http://127.0.0.1:8011/api/selectIdwq";
		JSONObject json=new JSONObject();
		json.put("cmd", 114);
		json.put("player_token", "N5dEpKPw04hddy6bOzk2YCyeIbAzZNK8JjO3iiv%2FgAqnlOaLhotb2OeyYNSvFT3tZVPIZtbsBHg%3D");
		
		for (int i = 0; i < 1000; i++) {
			long ss=System.currentTimeMillis();
			byte[] byteArray=post(url, json);
			System.out.println("耗费:"+(System.currentTimeMillis()-ss)+"毫秒");
			   // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替  
	        ByteArrayInputStream input = new ByteArrayInputStream(byteArray);  
	          
	        // 反序列化  
	        user_Proto.Response xxg2 = user_Proto.Response.parseFrom(input);  
	        System.out.println("info:" + xxg2.getInfo());  
	        System.out.println("code:" + xxg2.getResult());  
	        
	        System.out.println("friend:");  
		}
		log.info("hahha");
		
	}

}
