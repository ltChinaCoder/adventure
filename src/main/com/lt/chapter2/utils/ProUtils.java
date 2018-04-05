package com.lt.chapter2.utils;
import lombok.extern.slf4j.Slf4j;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
@Slf4j
public class ProUtils {
    public static Properties loadProps(String fileName) {
        Properties properties = null;
        InputStream inputStream = null;
        inputStream = ProUtils.class.getClassLoader().getResourceAsStream(fileName);
        try {
            if (inputStream == null)
                throw new FileNotFoundException(fileName + " is not found");
            properties = new Properties();
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("load propertis" + fileName + " close inputstream occurs error",e);
                }
            }
        }
        return properties;
    }
    /**
     * 返回特定的
     *
     * @param properties 属性文件
     * @param key        属性key值
     * @return
     */
    public static String getString(Properties properties, String key) {
        return getString(properties, key, "");
    }
    /**
     * @param properties
     * @param key
     * @param s
     * @return
     */
    public static String getString(Properties properties, String key, String s) {
        String string = properties.getProperty(key);
        if (string == null)
            return s;
        return string;
    }
    public static int getInt(Properties properties, String key) {
        return getInt(properties, key, 0);
    }
    /**
     * @param properties
     * @param key
     * @param s
     * @return
     */
    public static int getInt(Properties properties, String key, int s) {
        String property = properties.getProperty(key);
        Integer string = Integer.parseInt(property);
        if (string == null)
            return s;
        return string;
    }
    public static boolean getBoolean(Properties properties, String key) {
        return getInt(properties, key, false);
    }
    /**
     * @param properties
     * @param key
     * @param s
     * @return
     */
    public static boolean getInt(Properties properties, String key, boolean s) {
        String property = properties.getProperty(key);
        Boolean b = Boolean.parseBoolean(property);
        if (b == null)
            return s;
        return b;
    }
}
