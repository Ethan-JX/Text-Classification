package com.ethan.util;

import java.io.*;

/**
 * Created by Ethan on 2017/3/29.
 */
public class FileUtil {
    public static void createFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    public static String readAllContentByFile(File file)throws IOException{
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GB18030"));
        String lineTxt = null;
        while((lineTxt = reader.readLine()) != null){
            stringBuffer.append(lineTxt)
                .append("\n");
        }
        reader.close();
        return stringBuffer.toString();
    }



    public static void main(String[] args) throws IOException {
//        FileUtil.createFile("C:\\Users\\Ethan\\Desktop\\文本分类语料库\\1.txt");

//        File f = new File("C:\\Users\\Ethan\\Desktop\\文本分类语料库\\交通214\\");
//        System.out.print(FileUtil.readAllContentByFile(f));
    }
}
