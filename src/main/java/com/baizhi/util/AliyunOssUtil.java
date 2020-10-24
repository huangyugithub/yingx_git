package com.baizhi.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class AliyunOssUtil {
    // Endpoint以杭州为例，其它Region请按实际情况填写。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String accessKeyId = "LTAI4G96jxNmwb9tdguvNKmz"; //密钥
    private static String accessKeySecret = "eVwrnyxTCtfKP5rBpouAeqriihSZL8";
    private static String bucketName = "yingx-huangy";

    /*
        上传本地文件
        上传的目录 / 上传的文件名: fileName = "photo/1.jpg";
        本地路径: localPath = "C:\\Users\\huangyu\\Desktop\\img\\river.jpg";
    */
    public static void uploadLocalFile(String fileName, String localPath) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new File(localPath));

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

        // 上传文件。
        ossClient.putObject(putObjectRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /*
     *上传byte数组  上传文件到阿里云
     * */
    public static void uploadByteFile(MultipartFile multipartFile, String fileName) {
        byte[] content = new byte[0];
        try {
            content = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。
        //byte[] content = "Hello OSS".getBytes();
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(content));

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //删除单个文件  String objectName = "photo/1.jpg";
    public static void deleteOne(String objectName) {

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //删除多个文件

    //下载到本地
    public static void testDownload(String objectName, String locaPath) {

        // String objectName = "photo/1.jpg";
        //下载到本地的位置
        // String locaPath = "C:\\Users\\huangyu\\Desktop\\img\\hhhhh.jpg";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(locaPath));

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
