package com.zaihua.utils.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

//import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;
//import org.json.JSONArray;


/**
 *
 * 实现描述：Json处理方法，封装Jackson
 *
 * @author chenlong
 * @version v1.0.0
 * @see
 */
public class JacksonUtils {
    private final static Logger logger = LoggerFactory.getLogger(JacksonUtils.class);

    private final static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
    /**
     * 反序列化为对象
     * @param json
     * @param targetClass
     * @param <T>
     * @return
     * @throws java.io.IOException
     */

    public static <T> T unmarshalFromString(String json, Class<T> targetClass)  {
        if(StringUtils.isBlank(json)||targetClass==null)
            return null;

        try {
            return mapper.readValue(json,targetClass);
        } catch (Exception e) {
            logger.error(String.format("unmarshalFromString error %s, %s", json, targetClass.toString()), e);

        }
        return null;
    }

    /**
     * 反序列化为集合对象
     * @param json
     * @param type
     * @param <T>
     * @return
     * @throws java.io.IOException
     */
    public static <T> T unmarshalFromString(String json, TypeReference<T> type)  {
        if(StringUtils.isBlank(json)||type==null)
            return null;

        try {
            return  mapper.readValue(json, type);
        } catch (Exception e) {
            logger.error(String.format("unmarshalFromString error %s, %s", json, type.getType()), e);
        }
        return null;
    }

    /**
     * 反序列化为List
     * @param json
     * @param targetClass
     * @param <T>
     * @return
     * @throws java.io.IOException
     */
    public static <T> List<T> unmarshalFromString2List(String json, Class<T> targetClass)  {
      //  logger.info("JacksonUtils===unmarshalFromString2List "+json);
        if(StringUtils.isBlank(json)||targetClass==null)
            return null;
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, targetClass));
        } catch (Exception e) {
            logger.error(String.format("unmarshalFromString2List error %s, %s", json, targetClass.toString()), e);
        }
        return null;
    }

    /**
     * 序列化
     * @param obj
     * @return
     */
    public static String marshalToString(Object obj) {
        if(obj==null)
            return null;

        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(String.format("marshalToString error object: %s ", obj), e);
        }
        return null;
    }

    /**
     * 序列化，输出格式化的JSON
     * @param obj
     * @return
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public static String marshalToStringPretty(Object obj)  {
        if(obj==null)
            return null;
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            logger.error(String.format("marshalToStringPretty error object: %s ", obj), e);
        }
        return null;
    }

    /**
     * 转化为JsonNode
     * @param Json
     * @return
     */
    public static JsonNode toJsonNode(String Json){
        if(StringUtils.isBlank(Json))
            return null;
        try {
            JsonNode arrNode = new ObjectMapper().readTree(Json);
            return arrNode;
        } catch (Exception e) {
            logger.error(String.format("toJsonNode error Json: %s ", Json), e);
        }
        return null;
    }


}
