package com.sdq.autodeploy.controller;

import com.sdq.autodeploy.controller.utils.ShellUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Author:   chenfeiliang
 * Description:
 */
@Controller
public class FileController {


    @RequestMapping("/toUpload")
    public String toUpload(){

        return "upload";
    }


    @ResponseBody
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws InterruptedException {

        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String fileName = file.getOriginalFilename();
        String filePath = "/home/";
        File dest = new File(filePath + fileName);
        try {

            Runtime runtime = Runtime.getRuntime();
            //停止进程和删掉jar包
            String stopCommand = "bash /root/hello.sh "+fileName;
            Process pro = runtime.exec(stopCommand);

            int status = pro.waitFor();
            if (status != 0)
            {
                System.out.println("停止进程失败");
                return "停止进程失败 ";
            }

            file.transferTo(dest);

            //运行上传的jar包
            String startCommand = "bash /home/shell.sh "+ fileName;
            pro = runtime.exec(startCommand);

            status = pro.waitFor();

            if (status != 0)
            {
                System.out.println("启动进程失败");
                return "启动进程失败 ";
            }

            String ps = "jps";
            String resltByInputStream = ShellUtil.runShell(ps);
            return resltByInputStream;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "发布失败！";
    }

}