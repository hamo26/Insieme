package com.insieme.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insieme.common.database.dto.PersistentEntity;

public class JSONUtil {
	private final Gson gson;
	
	public JSONUtil() {
		gson = new GsonBuilder().create();
	}
	
	public <E extends PersistentEntity> E deserializeRepresentation(String jsonRepresentation, Class<E> cls) {
		return gson.fromJson(jsonRepresentation, cls);
	}
	
	public <E extends PersistentEntity> String serializeRepresentation(E persistentDto) {
		return gson.toJson(persistentDto);
	}
}
