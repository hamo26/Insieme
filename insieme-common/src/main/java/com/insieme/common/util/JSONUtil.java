package com.insieme.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insieme.common.domain.dto.RestEntity;



public class JSONUtil {
	private final Gson gson;
	
	public JSONUtil() {
		gson = new GsonBuilder().create();
	}
	
	public <E extends RestEntity> E deserializeRepresentation(String jsonRepresentation, Class<E> cls) {
		return gson.fromJson(jsonRepresentation, cls);
	}
	
	public <E extends RestEntity> String serializeRepresentation(E persistentDto) {
		return gson.toJson(persistentDto);
	}
}
