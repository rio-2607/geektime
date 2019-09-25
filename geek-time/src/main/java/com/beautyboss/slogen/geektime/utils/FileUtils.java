package com.beautyboss.slogen.geektime.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static void writeFile(String fileName, String fileContent) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(
                    new FileOutputStream(f), "gbk");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static List<String> readFile(String filePath, boolean tripEmpty) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(ResourceUtils.getFile("classpath:" + filePath)));
        BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream, "gbk"));
        String line;
        List<String> contents = new ArrayList<>();
        while(null != (line = reader.readLine())) {
            if(tripEmpty) {
                if(!"".equals(line)) {
                    contents.add(line);
                }
            } else {
                contents.add(line);
            }
        }
        return contents;
    }


}
