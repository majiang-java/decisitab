package com.ifre.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import com.ifre.exception.IfreException;



public class FtpUtil {
	private static final Logger LOG = Logger.getLogger(FtpUtil.class);

	/**
	 * 连接FTP服务
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws IfreException
	 */
	public static FTPClient connect(Object url, Object port,Object username, Object password) throws IfreException {
		FTPClient ftpClient = null;
		//连接指定ip、端口号的ftp服务
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(url.toString().trim(), Integer.parseInt(String.valueOf(port)));
			ftpClient.setControlEncoding("UTF-8");
			ftpClient.enterLocalPassiveMode();
			//判断ftp应答码是否是大于等于200,且小于300
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
			//连接ftp（用户、密码）
				if (ftpClient.login(username.toString().trim(), password.toString().trim())) {
					return ftpClient;
				}
			}else{
				ftpClient.disconnect();
				throw new IfreException(" 连接FTP服务器--失败！");
			}
		} catch (Exception e) {
			throw new IfreException(200000," 连接FTP服务器--失败！",e);
		} 
		return ftpClient;
	}
	/**
	 * 本地上传文件到ftp
	 * @param ftpClient
	 * @param ftpUploadPath
	 * @param localPath
	 * @param fileName
	 * @throws IfreException
	 */
	public static void uploadFile(FTPClient ftpClient, Object ftpUploadPath, Object  localPath, String fileName, int type)throws IfreException{
		FileInputStream input =null;
		//用来指定ftp服务器上传文件目录
		try {
			if(ftpClient == null) throw new IfreException("FTP连接异常 ！");
			if(type == 1){
				ftpClient.changeWorkingDirectory((String) ftpUploadPath);
			}else if(type == 2){
				ftpClient.changeToParentDirectory();
				ftpClient.changeWorkingDirectory((String) ftpUploadPath);
			} 
		}catch (IOException e) {
			throw new IfreException(200000," 设置FTP服务器目录--失败！",e );
		}
		//开始上传文件
		try {
			try {
				input = new FileInputStream(localPath + File.separator + fileName);
			} catch (FileNotFoundException e) {
				throw new IfreException(200000,"读取文件--失败！ ",e );
			}

			if (ftpClient.storeFile(fileName, input) ==false)
				throw new IfreException(" 文件上传--失败！ ");
		} catch (IOException e) {
			throw new IfreException(200000," 文件上传--失败！ ",e );
		}finally{
			try{
				if(input != null){
					input.close();
				}
			} catch (Exception e) {
				LOG.error("关闭流--失败！");
			}
		}
		
	}
	/**
	 * 从ftp下载放款结果文件
	 * @param ftpClient
	 * @param prefix
	 * @param map
	 * @return
	 * @throws IfreException
	 * 
	 * 修改后为 downFiles
	 * 
	 */
	public static List<String> downFiles(FTPClient ftpClient, String prefix, Map<String, Object> map)throws IfreException{
		List<String> filesname = new ArrayList<String>();
		try {
			ftpClient.changeWorkingDirectory(map.get("ftpDownloadPath").toString());
		} catch (IOException e) {
			throw new IfreException(200000," 设置ftp服务器路径--失败！",e );
		}
		LOG.info("ftp文件目录===="+ map.get("ftpDownloadPath"));
		OutputStream output = null;
		String ftpName = null;
		FTPFile[] files = null;
		try {
			files = ftpClient.listFiles();
		} catch (IOException e) {
			throw new IfreException(200000," 获取ftp服务器目录文件--失败！",e );
		}
        for(FTPFile file:files){
        	boolean isdown = false;
    		ftpName = file.getName();
        	if (prefix!= null && (prefix.trim().isEmpty() == false)){
            	if(ftpName.startsWith(prefix)){
            		isdown = true;
            	}
        	}else isdown = true;
        	
        	if(isdown){
        		try {
					try {
						File dir = new File((String) map.get("dateDir"));
						if(!dir.exists()){
							dir.mkdir();
						}
						output = new FileOutputStream(new File(map.get("dateDir")+ File.separator +file.getName()));
					} catch (FileNotFoundException e) {
						throw new IfreException(200000,"创建输出流--失败！ ",e );
					}  
					try {
						ftpClient.retrieveFile(file.getName(), output);
					} catch (IOException e) {
						throw new IfreException(200000," 从ftp服务器下载文件到本地--失败！",e );
					}
					LOG.info("文件["+ ftpName +"],已下载成功！");
					filesname.add(ftpName);
        		}catch(Exception e) {
					throw new IfreException(200000," 从ftp服务器下载文件到本地--失败！",e );
				}finally{
			        if(output != null){
						try {
							output.close();
						} catch (IOException e) {
							LOG.error("关闭流--失败！", e);
						} 
					}
				}
        	}
        }
        return filesname;
	}
	/**
	 * 从ftp下载成功扣款文件
	 * @param ftpClient
	 * @param map
	 * @return
	 * @throws IfreException
	 */
	public static String downloadSuccessFile(FTPClient ftpClient, Map<String, Object> map)throws IfreException{
		String today = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String ftpName = null;
		String ftpFileName = null;
		OutputStream output = null;
		FTPFile[] files = null;
		try {
			ftpClient.changeWorkingDirectory(map.get("ftpDownloadPath").toString());
		} catch (IOException e) {
			throw new IfreException(200000," 设置ftp服务器路径--失败！",e );
		} 
		LOG.info("ftp文件目录===="+ map.get("ftpDownloadPath"));
		try {
			files = ftpClient.listFiles();
		} catch (IOException e) {
			throw new IfreException(200000," 获取ftp服务器目录文件--失败！",e );
		} 
		 for(FTPFile file:files){
			ftpName = file.getName().substring(0,8);
			if(ftpName.equals(today)){
				ftpFileName = file.getName();
				try {
					File dir = new File((String) map.get("dateDir"));
					if(!dir.exists()){
						dir.mkdir();
					}
					output = new FileOutputStream(new File(map.get("dateDir")+ File.separator + ftpFileName));
				} catch (Exception e) {
					throw new IfreException(200000," 创建输出流--失败！",e );
				}  
				try {
					ftpClient.retrieveFile(ftpFileName, output);
				} catch (IOException e) {
					throw new IfreException(200000,"从ftp服务器下载文件到本地--失败！ ",e );
				} finally{
					if(output != null){
						try {
							output.close();
						} catch (IOException e) {
							LOG.error("关闭流--失败！", e);
						} 
					}
				}
			}
		 }
		return ftpFileName;
	}
	
	/**
	 * 关闭连接
	 */
	public static void close(FTPClient ftpClient) throws Exception{
		if (ftpClient == null || !ftpClient.isConnected()) {
			return;
		}
		LOG.info("关闭ftp连接--开始！");
		ftpClient.logout();
		ftpClient.disconnect();
		LOG.info("关闭ftp连接--结束！");
	}
}
