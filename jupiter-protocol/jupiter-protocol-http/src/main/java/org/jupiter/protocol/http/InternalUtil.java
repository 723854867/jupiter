package org.jupiter.protocol.http;

import org.jupiter.util.lang.StringUtil;
import org.jupiter.util.protocol.bean.ResponseFailure;

import okhttp3.Response;

class InternalUtil {

	static Response checkResponse(Response response) { 
		if (response.isSuccessful())
			return response;
		String msg = null;
		try {
			msg = response.body().string();
		} catch (Exception e) {}
		String error = response.message();
		if (StringUtil.hasText(msg))
			error += " - [" + msg + "]";
		throw new ResponseFailure(response.code(), error);
	}
}
