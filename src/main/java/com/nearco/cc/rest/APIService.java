package com.nearco.cc.rest;

import java.util.HashMap;
import java.util.Map;

public abstract class APIService {

	protected Map<?,?> success(Object data,String message){
		Map<String,Object> ret = new HashMap<>();
		ret.put("message", message);
		ret.put("data", data);
		ret.put("success", true);
		return ret;
	}
	
	protected Map<?,?> error(String message){
		Map<String,Object> ret = new HashMap<>();
		ret.put("message", message);
		ret.put("success", false);
		return ret;
	}
}
