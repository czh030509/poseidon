package com.zaihua.utils.base;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static String getValue(String key, String filename) {
        String value = "";
        try {
            Resource resource = new ClassPathResource(filename); //相对路径
            //Resource resource = new FileSystemResource(filename);//绝对路径

            EncodedResource encRes = new EncodedResource(resource, "UTF-8");
            Properties props = PropertiesLoaderUtils.loadProperties(encRes);
            value = props.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }
}