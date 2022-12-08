package org.chenxilin.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author chenxilin
 */
public class PropertiesHelper {

    /**
     * 读取properties配置文件，转为Properties对象
     */
    public static Properties readPropertiesFile(String fileName) throws Exception {
        FileInputStream in = new FileInputStream(fileName);
        Properties properties = new Properties();
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        properties.load(reader);
        return properties;
    }
}
