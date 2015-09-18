package com.nfky.yaoyijia.net.converter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * Created by David on 8/25/15.
 *
 * JacksonConverter是为Retrofit编写的使用Jackson来解析对象的Json Converter，默认使用的mime_type是 application/json，可以在构造函数中选择text/html
 * 此文件无需修改
 *
 **/

public class JacksonConverter implements Converter {

	public static final String APPLICATION_JSON_VALUE = "application/json; charset=UTF-8";
	public static final String TEXT_HTML_VALUE = "text/html; charset=UTF-8";

	private static final String MIME_TYPE = APPLICATION_JSON_VALUE;
	private static final String ENCODE = "UTF-8";

	private final ObjectMapper objectMapper;
	private String currMimeType;

	public JacksonConverter() {
		this(new ObjectMapper());
	}

	public JacksonConverter(ObjectMapper objectMapper) {
		this(objectMapper, MIME_TYPE);
	}

	public JacksonConverter(String mimeType) {
		this(new ObjectMapper(), mimeType);
	}

	public JacksonConverter(ObjectMapper objectMapper, String mimeType) {
		this.currMimeType = mimeType;
		this.objectMapper = objectMapper;
	}

	@Override
	public Object fromBody(TypedInput body, Type type)
			throws ConversionException {
		try {
			JavaType javaType = objectMapper.getTypeFactory().constructType(type);
			return objectMapper.readValue(body.in(), javaType);
		} catch (IOException e) {
			throw new ConversionException(e);
		}
	}

	@Override
	public TypedOutput toBody(Object object) {
		try {
			String json = objectMapper.writeValueAsString(object);
			return new TypedByteArray(currMimeType, json.getBytes(ENCODE));
		} catch (IOException e) {
			throw new AssertionError(e);
		}
	}

}
