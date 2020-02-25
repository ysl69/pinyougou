package com.pinyougou.fastdfs.test;

import com.pinyougou.common.util.FastDFSClient;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

/**
 * @Author ysl
 * @Date 2019/6/28 9:28
 * @Description:
 **/


public class FastdfsTest {
    @Test
    public void uploadFastdfs() throws Exception{
        //1.创建一个配置文件 文件用于存储服务器的ip地址和端口
        // 2.加载配置文件
        ClientGlobal.init("G:\\practice\\pinuyougou1\\pinyougou-shop-web\\src\\main\\resources\\config\\fastdfs_client.conf");
        //3.创建trackerClient
        TrackerClient trackerClient  = new TrackerClient();
        // 4.获取trackerServer
        TrackerServer trackerServer = trackerClient.getConnection();
        //5.创建storageClient
        StorageClient storageClient = new StorageClient(trackerServer,null);
        //6.上传图片
        //第一个参数：指定本地图片文件的路径
        //第二个参数：文件的扩展名  不要带“.”
        //第三个参数：文件的元数据
        String[] jpgs = storageClient.upload_file("C:\\Users\\Administrator\\Pictures\\Koala.jpg", "jpg", null);

        for (String jpg : jpgs) {
            System.out.println(jpg);
        }
    }



    @Test
    public void uploadFastdfsclient() throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("G:\\practice\\pinuyougou1\\pinyougou-shop-web\\src\\main\\resources\\config\\fastdfs_client.conf");
        String jpg = fastDFSClient.uploadFile("C:\\Users\\Public\\Pictures\\Sample Pictures\\Jellyfish.jpg", "jpg");
        System.out.println(jpg);
    }
}
