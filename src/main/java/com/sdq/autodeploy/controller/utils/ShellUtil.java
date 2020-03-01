package com.sdq.autodeploy.controller.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:   chenfeiliang
 * Description:
 */
public class ShellUtil {

    /**
     * 运行shell并获得结果，注意：如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流
     *
     * @param shStr 需要执行的shell
     * @return
     */
    public static String runShell(String shStr) {
//        List<String> strList = new ArrayList<String>();
//        try {
//            Process process = Runtime.getRuntime().exec(shStr);
//            InputStreamReader ir = new InputStreamReader(process.getInputStream());
//            LineNumberReader input = new LineNumberReader(ir);
//            String line;
//            process.waitFor();
//            while ((line = input.readLine()) != null) {
//                strList.add(line);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return strList;
        Runtime runtime = Runtime.getRuntime();
        StringBuffer stringBuffer = new StringBuffer();

        try
        {
//利用exec()方法执行shell 命令 ls -al /root ,并且返回一个Process对象 也就是子进程
//ps:这里都以最简单的shell命令举例。
            Process process = runtime.exec(shStr);

            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));


            String temp = null;

            while ((temp = bufferReader.readLine()) != null)
            {
                stringBuffer.append(temp);

                stringBuffer.append("\n");

            }

            System.out.println(stringBuffer);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return new String(stringBuffer);

   }

}