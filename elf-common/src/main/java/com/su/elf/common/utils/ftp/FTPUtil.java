package com.su.elf.common.utils.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FTPUtil {

    /**
     * Description: 创建链接，登录ftp服务器
     *
     * @param ftp FTPClient
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @return 成功返回ftp，否则返回null
     */
    public static FTPClient createConnection(FTPClient ftp, String url, int port, String username, String password)
            throws IOException {
        int reply;
        ftp.connect(url, port);//连接FTP服务器
        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
        ftp.login(username, password);//登录
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null;
        }
        //被动模式
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    /**
     * Description: 向FTP服务器上传文件
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory FTP服务器保存目录
     * @param fileName 上传到FTP服务器上的文件名
     * @param localFile 本地文件名
     * @return 成功返回true，否则返回false
     */
    public static boolean upload(String url, int port, String username, String password, String directory,
                              String fileName, String localFile) throws IOException {
        boolean success;
        FileInputStream fis = null;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            try {
                File srcFile = new File(localFile);
                fis = new FileInputStream(srcFile);
                ftp.changeWorkingDirectory(directory);
                //ftp.setBufferSize(1024);
                //ftp.setControlEncoding("gbk");
                // 设置文件类型（二进制）
                //ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                success = ftp.storeFile(fileName, fis);
                ftp.logout();
                ftp.disconnect();
            } finally {
                if (fis!=null) {
                    fis.close();
                }
            }
        }else{
            success = false;
        }
        return success;
    }


    /**
     * FTP单个文件下载
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory FTP服务器保存目录
     * @param destFileName   要下载的文件名
     * @param downloadName   下载后锁存储的文件名全路径
     */
    public static boolean download(String url, int port, String username, String password, String directory,
                                   String destFileName, String downloadName) throws IOException {

        boolean success;
        FileOutputStream fos = null;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            try {
                ftp.changeWorkingDirectory(directory);
               // System.out.println("destFileName:" + destFileName + ",downloadName:" + downloadName);
                fos = new FileOutputStream(downloadName);
                success = ftp.retrieveFile(destFileName, fos);
                ftp.logout();
                ftp.disconnect();

            } finally {
                if(fos!=null){
                    fos.close();
                }
            }
        }else{
            success = false;
        }

        return success;
    }


    /**
     * 重命名ftp文件
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory FTP服务器保存目录
     * @param oldFileName     要重命名的文件名, 也可以是文件夹
     * @param newFileName     重命名后的文件名, 也可以是文件夹
     * @throws IOException
     */
    public static boolean rename(String url, int port, String username, String password, String directory,
                                 String oldFileName, String newFileName) throws IOException {

        boolean success;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            ftp.changeWorkingDirectory(directory);
            success = ftp.rename(oldFileName, newFileName);//重命名远程文件
            ftp.logout();
            ftp.disconnect();
        }else{
            success = false;
        }

        return success;
    }


    /**
     * 删除ftp文件
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory FTP服务器保存目录
     * @param fileName  要删除的文件名
     * @return
     * @throws IOException
     */
    public static boolean remove(String url, int port, String username, String password, String directory,
                                 String fileName) throws IOException
    {

        boolean success;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            ftp.changeWorkingDirectory(directory);
            success = ftp.deleteFile(fileName);
            ftp.logout();
            ftp.disconnect();
        }else{
            success = false;
        }

        return success;
    }


    /**
     *
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory 要创建的目录所在ftp的路径名不包含ftp地址
     * @param newDirectory  要创建的新目录名
     * @return
     * @throws IOException
     */
    public static boolean makeDirecotory(String url, int port, String username, String password, String directory,
                                         String newDirectory) throws IOException
    {
        boolean success;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            ftp.changeWorkingDirectory(directory);
            success = ftp.makeDirectory(newDirectory);//创建新目录
            ftp.logout();
            ftp.disconnect();
        }else{
            success = false;
        }

        return success;
    }

    /**
     *
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param directory 要创建的目录所在ftp的路径名不包含ftp地址
     * @param deldirectory    要删除的目录名
     * @return
     * @throws IOException
     */
    public static boolean removeDirecotory(String url, int port, String username, String password, String directory,
                                           String deldirectory) throws IOException
    {

        boolean success;
        FTPClient ftp = new FTPClient();
        ftp = createConnection(ftp, url, port, username, password);
        if(ftp!=null){
            ftp.changeWorkingDirectory(directory);
            success = ftp.removeDirectory(deldirectory);//删除目录
            ftp.logout();
            ftp.disconnect();
        }else{
            success = false;
        }

        return success;
    }

}
