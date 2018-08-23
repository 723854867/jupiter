package org.jupiter.protocol.http;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.jupiter.util.protocol.bean.HttpResonse;
import org.jupiter.util.protocol.bean.RequestFailure;
import org.jupiter.util.serializer.Serializer;

import okhttp3.Response;

@SuppressWarnings("unchecked")
public abstract class ResponseCallback<RESPONSE extends HttpResonse> extends Callback {
	
	private Class<RESPONSE> clazz;
	private Serializer serializer;
	
	public ResponseCallback(Serializer serializer) {
		this.serializer = serializer;
		Type superType = getClass().getGenericSuperclass();   
		Type[] generics = ((ParameterizedType) superType).getActualTypeArguments();  
		this.clazz = (Class<RESPONSE>) generics[0];
	}

	@Override
	protected final void onResponse(Response response) {
		try {
			onResponse(serializer.deserial(response.body().bytes(), clazz));
		} catch (IOException e) {
			throw new RequestFailure(e);
		}
	}
	
	protected abstract void onResponse(RESPONSE response);
}
