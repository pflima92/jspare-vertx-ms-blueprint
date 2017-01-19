package io.github.pflima92.plyshare.gateway.entity;

import javax.persistence.AttributeConverter;

import org.apache.commons.lang.StringUtils;

import io.vertx.core.json.JsonObject;

public class JsonObjectConverter implements AttributeConverter<JsonObject, String> {

	@Override
	public String convertToDatabaseColumn(JsonObject attribute) {
		if (attribute == null) {

			attribute = new JsonObject();
		}
		return attribute.encode();
	}

	@Override
	public JsonObject convertToEntityAttribute(String dbData) {

		if (StringUtils.isEmpty(dbData)) return new JsonObject();
		return new JsonObject(dbData);
	}
}