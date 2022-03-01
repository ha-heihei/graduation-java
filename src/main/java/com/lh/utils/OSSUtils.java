package com.lh.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author LiHao
 * @create 2022-01-16 12:48
 */
public class OSSUtils {

    private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAI5tQnc52CzJnPeydAJsZS";
    private static final String accessKeySecret = "Ijp9iLKcc3zOqqGlYD1aSjY5l96x9Q";
    private static final String bucketName = "dakeji";
    private static final String domian = "https://dakeji.oss-cn-beijing.aliyuncs.com/";

    public static String uploadFile(InputStream inputStream){
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        OSS ossClient = null;
        try {
            String objectName="images/"+dateFormat.format(date) +"/"+UUID.randomUUID()+".jpg";
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            ossClient.putObject(bucketName, objectName, inputStream);
            return domian + objectName;
        } catch (OSSException e){
            e.printStackTrace();
        } finally {
            // 关闭OSSClient。
            if(ossClient!=null){
                ossClient.shutdown();
            }
        }
        return null;
    }

    public static void downLoadFile(String fileUrl,OutputStream os) throws IOException {
        String fileName=fileUrl.substring(fileUrl.indexOf("/",9)+1);
        OSSObject ossObject = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret).getObject(bucketName, fileName);
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght = 0;
        while ((lenght = in.read(buffer)) != -1) {
            out.write(buffer, 0, lenght);
        }
        out.flush();
        out.close();
        in.close();

    }

}
