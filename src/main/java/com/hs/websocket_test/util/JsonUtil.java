package com.hs.websocket_test.util;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;  
  
/**
 * json处理工具类
 * 
 * @author jill
 */
@SuppressWarnings("serial")
@Component
public class JsonUtil {

	private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final ObjectMapper mapper;

	public ObjectMapper getMapper() {
		return mapper;
	}

	static {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);

		mapper = new ObjectMapper();
		mapper.setDateFormat(dateFormat);
		mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
			@Override
			public Object findSerializer(Annotated a) {
				if (a instanceof AnnotatedMethod) {
					AnnotatedElement m = a.getAnnotated();
					DateTimeFormat an = m.getAnnotation(DateTimeFormat.class);
					if (an != null) {
						if (!DEFAULT_DATE_FORMAT.equals(an.pattern())) {
							return new JsonDateSerializer(an.pattern());
						}
					}
					JsonSerialize jsonSerialize = m.getAnnotation(JsonSerialize.class);
					if(jsonSerialize != null){
						return jsonSerialize;
					}
				}
				return super.findSerializer(a);
			}
		});
	}

	public static String toJson(Object obj) {
		try {
            return filterNull(mapper.writeValueAsString(obj));
		} catch (Exception e) {
			throw new RuntimeException("转换json字符失败!");
		}
	}

	//过滤null
	public static String filterNull(String jsonStr){
        while(jsonStr.indexOf(":null,")!=-1){
            jsonStr=jsonStr.replace(":null,",":\"\",");	//过滤map中的null
        }
        while(jsonStr.indexOf(":null}")!=-1){
            jsonStr=jsonStr.replace(":null}",":\"\"}");	//过滤map中的null
        }
        while(jsonStr.indexOf(",null,")!=-1){
            jsonStr=jsonStr.replace(",null,",",\"\",");	//过滤list中的null
        }
        while(jsonStr.indexOf("[null,")!=-1){
            jsonStr=jsonStr.replace("[null,","[\"\",");	//过滤list中的null
        }
        while(jsonStr.indexOf(",null]")!=-1){
            jsonStr=jsonStr.replace("ull]",",\"\"]");	//过滤list中的null
        }
        return jsonStr;
    }

	public <T> T toObject(String json, Class<T> clazz) {
		try {
			return mapper.readValue(json, clazz);
		} catch (IOException e) {
			throw new RuntimeException("将json字符转换为对象时失败!");
		}
	}

	public static class JsonDateSerializer extends JsonSerializer<Date> {
		private SimpleDateFormat dateFormat;

		public JsonDateSerializer(String format) {
			dateFormat = new SimpleDateFormat(format);
		}

		@Override
		public void serialize(Date date, JsonGenerator gen, SerializerProvider provider)
				throws IOException, JsonProcessingException {
			String value = dateFormat.format(date);
			gen.writeString(value);
		}
	}

	public static Map<String, Object> getMap(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		Map<String, Object> valueMap = new HashMap<String, Object>();
		valueMap.putAll(jsonObject);
		return valueMap;
	}

	public static  Object json2Object(String json , Class clazz){
		if(json == null || clazz == null){
			return null;
		}
		Object object = JSONObject.parseObject(json, clazz);
		return object;
	}


    public static void main(String[] args) {

    }
}  
