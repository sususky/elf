package com.su.elf.common.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

/**
 * @author surongyao
 * @Type
 * @Desc sftp 客户端
 * @date 2018-03-23 16:17
 */
public class SFTPClient {

    private static final Logger logger = LoggerFactory.getLogger(SFTPClient.class);

    private String host;
    private int port = 22;
    private String username;
    private String password;
    private int timeout = 10000;

    private ChannelSftp channelSftp;
    private Session session;

    public SFTPClient(){}

    public SFTPClient(String host, int port, String username, String password){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public SFTPClient(String host, int port, String username, String password, int timeout){
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.timeout = timeout;
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect() throws Exception {
        try {
            JSch jsch = new JSch();

            session = jsch.getSession(username, host, port);
            if (logger.isDebugEnabled()) {
                logger.debug("sftp session created");
            }
            if(password!=null && password.trim().length()>0){
                session.setPassword(password);
            }

            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            session.setConfig(sshConfig);
            session.connect();
            if (logger.isDebugEnabled()) {
                logger.debug("sftp session connected");
            }
            Channel channel = session.openChannel("sftp");
            channel.connect();
            if (logger.isDebugEnabled()) {
                logger.debug("Opening Channel");
            }
            channelSftp = (ChannelSftp) channel;

        } catch (Exception e) {
            //logger.error(e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (this.channelSftp != null) {
            if (this.channelSftp.isConnected()) {
                this.channelSftp.disconnect();
                if (logger.isDebugEnabled()) {
                    logger.debug("sftp channel is closed already");
                }
            }
        }
        if (this.session != null) {
            if (this.session.isConnected()) {
                this.session.disconnect();
                if (logger.isDebugEnabled()) {
                    logger.debug("sftp session is closed already");
                }
            }
        }
    }

    /**
     * 批量下载文件
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @return
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath, String fileFormat, String fileEndFormat)
            throws SftpException, FileNotFoundException {
        List<String> filenames = new ArrayList<String>();
        try {
            // connect();
            Vector v = listFiles(remotePath);
            // sftp.cd(remotePath);
            if (v.size() > 0) {
                if(logger.isInfoEnabled()){
                    logger.info("开始下载，共{}个文件", v.size());
                }

                Iterator it = v.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat.trim();
                        fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename,localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
                            if (filename.startsWith(fileFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
                            if (filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename,localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                }
                            }
                        } else {
                            flag = downloadFile(remotePath, filename,localPath, filename);
                            if (flag) {
                                filenames.add(localFileName);
                            }
                        }
                    }
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("download file is success:remotePath:{} and localPath:{} ,file size is",
                        remotePath, localPath, v.size());
            }
        } catch (SftpException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } catch (FileNotFoundException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } finally {
            // this.disconnect();
        }
        return filenames;
    }

    /**
     * 下载单个文件
     * @param remotePath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName)
            throws FileNotFoundException, SftpException {
        FileOutputStream fieloutput = null;
        try {
            // sftp.cd(remotePath);
            File file = new File(localPath + localFileName);
            // mkdirs(localPath + localFileName);
            fieloutput = new FileOutputStream(file);
            channelSftp.get(remotePath + remoteFileName, fieloutput);
            if (logger.isInfoEnabled()) {
                logger.info("downloadFile: {} success from sftp", remoteFileName);
            }
            return true;
        } catch (FileNotFoundException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } catch (SftpException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 上传单个文件
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName)
            throws FileNotFoundException, SftpException {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            channelSftp.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } catch (SftpException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 批量上传文件
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath) throws Exception {
        try {
            //connect();
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().indexOf("bak") == -1) {
                    this.uploadFile(remotePath, files[i].getName(), localPath, files[i].getName());
                }
            }
            if (logger.isInfoEnabled()) {
                logger.info("upload file is success:remotePath=" + remotePath
                        + "and localPath=" + localPath + ",file size is "
                        + files.length);
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            throw e;
        } finally {
            //this.disconnect();
        }

    }

    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 如果目录不存在就创建目录
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);
        String fs = f.getParent();
        f = new File(fs);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory：要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return channelSftp.ls(directory);
    }

    /**
     * 删除stfp文件
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile) throws Exception {
        try {
            // sftp.cd(directory);
            channelSftp.rm(directory + deleteFile);
            logger.warn("delete file {} success from sftp", deleteFile);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 创建目录
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) throws SftpException {
        try {
            if (isDirExist(createpath)) {
                channelSftp.cd(createpath);
                return true;
            }
            String [] pathArry = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if ("".equals(path)) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    channelSftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    channelSftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    channelSftp.cd(filePath.toString());
                }

            }
            channelSftp.cd(createpath);
            return true;
        } catch (SftpException e) {
            //logger.error(e.getMessage(), e);
            throw e;
        }

    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
