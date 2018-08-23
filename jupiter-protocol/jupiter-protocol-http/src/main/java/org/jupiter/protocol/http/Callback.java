package org.jupiter.protocol.http;

import java.io.IOException;

import org.jupiter.util.protocol.bean.RequestFailure;

import okhttp3.Call;
import okhttp3.Response;

public abstract class Callback implements okhttp3.Callback {
	
	@Override
	public void onFailure(Call call, IOException e) {
		throw new RequestFailure(e);
	}
	
	@Override
	public void onResponse(Call call, Response response) throws IOException {
		onResponse(InternalUtil.checkResponse(response));
	}
	
	protected abstract void onResponse(Response response); 
}
