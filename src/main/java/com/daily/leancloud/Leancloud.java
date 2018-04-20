package com.daily.leancloud;


import com.avos.avoscloud.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created by Administrator on 2017/7/20.
 */
public class Leancloud {
    public static String uploadImages(MultipartFile multipartFile) throws AVException, IOException {
        // 启用节点
        AVOSCloud.useAVCloudCN();
        // 参数依次为 AppId、AppKey、MasterKey
        AVOSCloud.initialize("ud7op3rBUXapSpO3tknaIFrG-gzGzoHsz","zixCKSoYUXTtnq8oflbeddLS","zdHDri92LLrv6JxIuw8SIwis");
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);
        AVFile file = new AVFile(multipartFile.getName(), multipartFile.getBytes() );
        file.save();
        return file.getUrl()+"?imageView2/1/w/150/h/120";
    }

    //图片到byte数组
    public static byte[] image2byte(String path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        }
        catch (FileNotFoundException ex1) {
            ex1.printStackTrace();
        }
        catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }

}
