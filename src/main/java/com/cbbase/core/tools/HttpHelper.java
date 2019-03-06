package com.cbbase.core.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;


/**
 * @author changbo
 * 
 */
public class HttpHelper {

	public static final int DUFAULT_TIMEOUT = 10000;
	public static final String DUFAULT_CHARACTERSET = "UTF-8";

	private int action = 0; //0-GET,1-POST,2-PUT,3-DELETE,
	private String downloadFile;
	private String uploadFile;
	private String url;
	private String virtualIp;
	private String certFile;
	private String certKey;
	private boolean trustAll = true;
	
	private int timeout = DUFAULT_TIMEOUT;
	private String characterSet = DUFAULT_CHARACTERSET;
	private Map<String, String> headers = new HashMap<String, String>();
	private Map<String, String> params = new HashMap<String, String>();
	private String body;
	//
	private boolean showLog;
	
	public HttpHelper(String url, int action){
		this.url = url;
		this.action = action;
	}
	
	public static HttpHelper createGet(String url){
		return new HttpHelper(url, 0);
	}
	
	public static HttpHelper createPost(String url){
		return new HttpHelper(url, 1);
	}
	
	public static HttpHelper createPut(String url){
		return new HttpHelper(url, 2);
	}
	
	public static HttpHelper createDelete(String url){
		return new HttpHelper(url, 3);
	}

	public HttpHelper addHeader(String key, String value){
		headers.put(key, StringUtil.getValue(value));
		return this;
	}

	public HttpHelper addHeader(Map<String, String> header){
		if(header != null){
			this.headers.putAll(header);
		}
		return this;
	}

	public HttpHelper addParam(String key, String value){
		params.put(key, StringUtil.getValue(value));
		return this;
	}

	public HttpHelper addParam(Map<String, String> param){
		if(param != null){
			this.params.putAll(param);
		}
		return this;
	}
	
	public HttpHelper setVirtualIp(String virtualIp){
		this.virtualIp = virtualIp;
		return this;
	}
	
	public HttpHelper randomVirtualIp(){
		return setVirtualIp(getRandomIp()+", "+getRandomIp());
	}
	
	private String getRandomIp(){
		int ip1 = RandomUtil.random(10, 126);
		int ip2 = RandomUtil.random(10, 255);
		int ip3 = RandomUtil.random(10, 255);
		int ip4 = RandomUtil.random(10, 255);
		String ip = ""+ip1+"."+ip2+"."+ip3+"."+ip4;
		return ip;
	}
	
	public HttpHelper setCharacterSet(String characterSet) {
		this.characterSet = characterSet;
		return this;
	}
	
	public HttpHelper setTimeout(int timeout){
		if(timeout >= 1000 && timeout <= 600000){
			this.timeout = timeout;
		}
		return this;
	}
	
	public HttpHelper setBody(String body) {
		this.body = body;
		return this;
	}
	
	public HttpHelper setDownloadFile(String downloadFile) {
		this.downloadFile = downloadFile;
		return this;
	}
	
	public HttpHelper setUploadFile(String uploadFile) {
		this.uploadFile = uploadFile;
		return this;
	}
	
	public HttpHelper setCertFile(String certFile) {
		this.certFile = certFile;
		return this;
	}
	
	public HttpHelper setCertKey(String certKey) {
		this.certKey = certKey;
		return this;
	}
	
	public HttpHelper setTrustAll(boolean trustAll) {
		this.trustAll = trustAll;
		return this;
	}
	
	public HttpHelper setShowLog(boolean showLog) {
		this.showLog = showLog;
		return this;
	}
	
	public boolean checkUrl(){
		url = StringUtil.getValue(url).trim();
		if(StringUtil.isEmpty(url) || (!url.startsWith("http://") && !url.startsWith("https://"))){
			return false;
		}
		return true;
	}
	
	public ResponseData execute(){
		ResponseData responseData = new ResponseData();
		url = StringUtil.getValue(url).trim();
		if(!checkUrl()){
			responseData.setException(new RuntimeException("url format error"));
			return responseData;
		}
		CloseableHttpClient httpClient = null;
		HttpRequestBase http = null;
		CloseableHttpResponse httpResponse = null;
		try {
			if(StringUtil.isEmpty(certFile)){
				if(trustAll){
					httpClient = getHttpClientTrustAll();
				}else{
					httpClient = getHttpClient();
				}
			}else{
				httpClient = getHttpClientByCert(certFile, certKey);
			}
			
			if(action == 0){
				if(params != null && !params.isEmpty()){
					http = getHttpForGet();
				}else{
					http = new HttpGet(url);
				}
			}else if(action == 1){
				if(StringUtil.hasValue(uploadFile)){
					http = getHttpForUploadFile();
				}else{
					http = new HttpPost(url);
					if(StringUtil.hasValue(body)) {
						((HttpPost)http).setEntity(getBodyEntity());
					}
					if(params != null && !params.isEmpty()) {
						((HttpPost)http).setEntity(getParamEntity());
					}
				}
			}else if(action == 2){
				http = new HttpPut(url);
				if(StringUtil.hasValue(body)) {
					((HttpPut)http).setEntity(getBodyEntity());
				}
				if(params != null && !params.isEmpty()) {
					((HttpPut)http).setEntity(getParamEntity());
				}
			}else if(action == 3){
				http = new HttpDelete(url);
			}
			http.setConfig(getRequestConfig(timeout));
	        for(String key : headers.keySet()){
	        	http.addHeader(key, headers.get(key).toString());
	        }
	        if(StringUtil.hasValue(virtualIp)){
	        	http.addHeader("x-forwarded-for", virtualIp);
	        }
			httpResponse = httpClient.execute(http);//执行请求
			
			int code = httpResponse.getStatusLine().getStatusCode();
			String data = EntityUtils.toString(httpResponse.getEntity());
			responseData.setStatus(code);
			responseData.setData(data);
			if(code == 200){
				if(StringUtil.hasValue(downloadFile)){
					InputStream in = httpResponse.getEntity().getContent();
					FileUtil.writeInputStream2File(in, new File(downloadFile));
				}
			}
			return responseData;
		} catch (Exception e) {
			responseData.setException(e);
		} finally {
			if(http != null){
				http.abort();
				http.releaseConnection();
			}
			close(httpClient);
			close(httpResponse);
		}
		return responseData;
	}
	
	private HttpRequestBase getHttpForGet() throws Exception{
		StringBuilder sb = new StringBuilder();
    	if (null != params) {
        	for (Map.Entry<String, String> query : params.entrySet()) {
        		if (StringUtil.hasValue(query.getKey()) && StringUtil.hasValue(query.getValue())) {
        			sb.append("&").append(query.getKey()+"="+query.getValue());
        			printLog(query.getKey()+"->"+query.getValue());
                }
        	}
        }
    	String data = sb.toString();
    	if(data.length() > 1){
    		data = data.substring(1);
    	}
    	String _url = url + "?" + data;
    	HttpGet http = new HttpGet(_url);
		return http;
	}
	
	private UrlEncodedFormEntity getParamEntity() {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		if(params != null){
			for(Entry<String, String> entry : params.entrySet()){
				paramList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
    			printLog(entry.getKey()+"->"+entry.getValue());
			}
		}
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, characterSet);
		    entity.setContentType("application/x-www-form-urlencoded");
		    return entity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private StringEntity getBodyEntity() {
		try {
		    return new StringEntity(body, characterSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private HttpRequestBase getHttpForUploadFile(){
		HttpPost http = new HttpPost(url);
		try {
			FileBody fileBody = new FileBody(new File(uploadFile));
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
			builder.addPart("upfile", fileBody);
			HttpEntity entity = builder.build();
		    http.setEntity(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return http;
	}
	
	
	private CloseableHttpClient getHttpClient() throws Exception {
	    return getHttpClientNoCert(DUFAULT_TIMEOUT);
	}
	
	public CloseableHttpClient getHttpClient(int timeout) throws Exception {
	    return getHttpClientNoCert(timeout);
	}
	
	private CloseableHttpClient getHttpClientByCert(String certFile, String certKey) throws Exception {
	    return getHttpClient(DUFAULT_TIMEOUT, createSSLContextByKeyStore(certFile, certKey));
	}
	
	private CloseableHttpClient getHttpClientTrustAll() throws Exception {
        return getHttpClient(createSSLContextTrustAll());
    }
	
	private CloseableHttpClient getHttpClient(SSLContext sslContext) throws Exception {
		return getHttpClient(DUFAULT_TIMEOUT, sslContext);
	}
	
	private CloseableHttpClient getHttpClientNoCert(int timeout) throws Exception {
        //
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build(), NoopHostnameVerifier.INSTANCE);
        
        CloseableHttpClient httpClient = HttpClients.custom()
        		.setDefaultSocketConfig(getSocketConfig(timeout))
        		.setDefaultRequestConfig(getRequestConfig(timeout))
        		.setSSLSocketFactory(sslsf)
        		.build();
	    return httpClient;
	}
	
	private CloseableHttpClient getHttpClient(int timeout, SSLContext sslContext) throws Exception {
        
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
        		sslContext,
                new String[] {"TLSv1", "TLSv1.1", "TLSv1.2"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        
        CloseableHttpClient httpClient = HttpClients.custom()
        		.setDefaultSocketConfig(getSocketConfig(timeout))
        		.setDefaultRequestConfig(getRequestConfig(timeout))
        		.setSSLSocketFactory(sslsf)
        		.build();
	    return httpClient;
	}

	private SSLContext createSSLContextTrustAll() throws Exception {
        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
            public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                return true;
            }
        }).build();
        return sslContext;
	}
	
	private SSLContext createSSLContextByKeyStore(String certFile, String certKey) throws Exception {
		char[] keyChars = null;
		if(StringUtil.hasValue(certKey)) {
			keyChars = certKey.toCharArray();
		}
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(new File(certFile), keyChars, new TrustSelfSignedStrategy())
				.build();
		return sslContext;
	}
	
	private RequestConfig getRequestConfig(int timeout){
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout)
                .setSocketTimeout(timeout)
                .build();
		return requestConfig;
	}
	
	private SocketConfig getSocketConfig(int timeout){
        SocketConfig socketConfig = SocketConfig.custom()
	        	.setSoTimeout(timeout)
        		.setTcpNoDelay(true)
        		.build();
        return socketConfig;
	}
	
	private void close(CloseableHttpClient httpClient){
		try {
			if(httpClient != null){
				httpClient.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void close(CloseableHttpResponse httpResponse){
		try {
			if(httpResponse != null){
				httpResponse.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printLog(String log) {
		if(showLog) {
			System.out.println(log);
		}
	}
	
	public class ResponseData{
		
		private int status;
		private String data;
		private Exception exception;
		
		public int getStatus() {
			return status;
		}
		public void setStatus(int status) {
			this.status = status;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public Exception getException() {
			return exception;
		}
		public void setException(Exception exception) {
			this.exception = exception;
		}
		
	}
}
