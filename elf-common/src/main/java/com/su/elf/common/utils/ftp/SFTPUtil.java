package com.su.elf.common.utils.ftp;

import com.su.elf.common.ftp.SFTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Vector;

/**
 * @author surongyao
 * @Type
 * @Desc
 * @date 2018-03-23 15:13
 */
public class SFTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);

    /**
     * FTP单个文件下载
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器保存目录
     * @param remoteFileName   要下载的文件名
     * @param localPath   下载后锁存储的全路径
     * @param downloadName 下载后锁存储的文件名
     */
    public static boolean download(String url, int port, String username, String password, String remotePath,
                                   String remoteFileName, String localPath, String downloadName) throws Exception {
        SFTPClient client = new SFTPClient(url, port, username, password);
        try{
            client.connect();
            client.downloadFile(remotePath, remoteFileName, localPath, downloadName);
            client.disconnect();
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if(client!=null){
                client.disconnect();
            }
        }

        return true;
    }

    /**
     * FTP查看列表
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器保存目录
     */
    public static Vector list(String url, int port, String username, String password, String remotePath)
            throws Exception {
        SFTPClient client = new SFTPClient(url, port, username, password);
        Vector list = null;
        try{
            client.connect();
            list = client.listFiles(remotePath);
            client.disconnect();
        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if(client!=null){
                client.disconnect();
            }
        }

        return list;
    }


}
