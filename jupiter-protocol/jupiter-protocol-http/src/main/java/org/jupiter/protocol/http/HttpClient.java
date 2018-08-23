package org.jupiter.protocol.http;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.jupiter.util.JupiterConsts;
import org.jupiter.util.protocol.bean.RequestFailure;
import org.jupiter.util.protocol.bean.enums.ContentType;
import org.springframework.stereotype.Component;

import okhttp3.ConnectionPool;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class HttpClient {

	private OkHttpClient client;
	@Resource
	private HttpConfig httpConfig;
	
	@PostConstruct
	private void _init() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		builder.connectTimeout(httpConfig.getConnTimeout(), TimeUnit.SECONDS);
		builder.readTimeout(httpConfig.getReadTimeout(), TimeUnit.SECONDS);
		builder.writeTimeout(httpConfig.getWriteTimeout(), TimeUnit.SECONDS);
		ConnectionPool pool = new ConnectionPool(httpConfig.getMaxIdleConns(), httpConfig.getConnKeepAlive(), TimeUnit.SECONDS);
		builder.connectionPool(pool);
		client = builder.build();
	}

	// 同步请求
	public Response request(Request request) {
		try {
			return InternalUtil.checkResponse(this.client.newCall(request).execute());
		} catch (IOException e) {
			throw new RequestFailure(e);
		}
	}
	
	// 异步请求
	public void request(Request request, Callback callback) {
		this.client.newCall(request).enqueue(callback);
	}

	// 同步 get 请求
	public Response get(HttpUrl url) {
		Request.Builder rb = new Request.Builder().url(url);
		return request(rb.build());
	}

	// 异步 get 请求
	public void get(HttpUrl url, Callback callback) {
		Request.Builder rb = new Request.Builder().url(url);
		request(rb.build(), callback);
	}

	// 同步 post 表单请求
	public Response postForm(HttpUrl url, Map<String, String> params) {
		Request.Builder rb = new Request.Builder().url(url);
		FormBody.Builder fb = new FormBody.Builder(JupiterConsts.UTF_8);
		for (Entry<String, String> entry : params.entrySet())
			fb.add(entry.getKey(), entry.getValue());
		Request request = rb.post(fb.build()).build();
		return request(request);
	}

	// 同步 post 表单请求
	public Response postForm(HttpUrl url, Map<String, String> params, Map<String, String> headers) {
		Request.Builder rb = new Request.Builder().url(url);
		for (Entry<String, String> entry : headers.entrySet())
			rb.addHeader(entry.getKey(), entry.getValue());
		FormBody.Builder fb = new FormBody.Builder(JupiterConsts.UTF_8);
		for (Entry<String, String> entry : params.entrySet())
			fb.add(entry.getKey(), entry.getValue());
		Request request = rb.post(fb.build()).build();
		return request(request);
	}

	// 同步 post json 请求
	public Response postJson(HttpUrl url, String content) {
		Request.Builder rb = new Request.Builder().url(url);
		RequestBody body = RequestBody.create(MediaType.parse(ContentType.APPLICATION_JSON_UTF_8.mark()), content);
		Request request = rb.post(body).build();
		return request(request);
	}
}
