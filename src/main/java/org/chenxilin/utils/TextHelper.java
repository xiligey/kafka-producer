package org.chenxilin.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * @author chenxilin
 */
public class TextHelper {

    /**
     * 读文本文件
     *
     * @param filePath 文件路径
     * @return 返回一个列表，每行一个字符串
     */
    public static ArrayList<String> readFile(String filePath) throws Exception {
        BufferedReader textFile = new BufferedReader(new FileReader(filePath));
        ArrayList<String> lines = new ArrayList<>();
        try {
            String line = textFile.readLine();
            while (line != null) {
                lines.add(line);
                line = textFile.readLine();
            }
        } finally {
            textFile.close();
        }

        return lines;
    }

    /**
     * TODO: 将多行文本内容写入文件
     *
     * @param lines 多行内容
     */
    public static void writeFile(ArrayList<String> lines) throws Exception {

    }
}
