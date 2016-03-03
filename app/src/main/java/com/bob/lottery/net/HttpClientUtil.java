package com.bob.lottery.net;

import com.bob.lottery.util.ConstantValue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.InputStream;


public class HttpClientUtil {
	private HttpClient client;

	private HttpPost post;
	private HttpGet get;

	public HttpClientUtil() {
		client = new DefaultHttpClient();
		client.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		client.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);

//		// 判断是否需要设置代理信息
//		if (StringUtils.isNotBlank(GlobalParams.PROXY)) {
//			// 设置代理信息
//			HttpHost host = new HttpHost(GlobalParams.PROXY, GlobalParams.PORT);
//			client.getParams()
//					.setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
//		}
	}

	/**
	 * 向指定的链接发送xml文件
	 *  @param uri
	 * @param xml*/
	public InputStream sendXml(String uri, String xml) {
		post = new HttpPost(uri);
		try {
			StringEntity entity = new StringEntity(xml, ConstantValue.ENCONDING);
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			HttpEntity entry=response.getEntity();

			StringBuilder sb=new StringBuilder();
			// 200
			if (response.getStatusLine().getStatusCode() == 200) {
				//test
				System.out.println(response.getStatusLine().getStatusCode());
//				if (entry!=null){
//					entity.consumeContent();
//					BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
//					String line=null;
//					while ((line=reader.readLine())!=null){
//						sb.append(line);
//					}
//				}
//				if (entry!=null){
//					entry.consumeContent();
//					BufferedReader reader=new BufferedReader(new InputStreamReader(entity.getContent(),"UTF-8"));
//					String line=null;
//					while ((line=reader.readLine())!=null){
//						sb.append(line);
//					}
//					System.out.println(sb.toString());
//					return new InputStreamReader(entity.getContent(),"UTF-8");
//				}
				return response.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

}
