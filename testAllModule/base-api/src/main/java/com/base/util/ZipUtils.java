package com.base.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 文件压缩工具类
 * <pre>
 * 。
 * </pre>
 * 
 * @author wenbin.xia@midea.com.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ZipUtils {
	private static final Logger logger = LoggerFactory.getLogger(ZipUtils.class.getName());
	
	/**
	 * 
	 * 
	 * @Description: 此方法压缩指定的文件
	 * @param srcFile
	 *            要进行压缩的文件
	 * @param zipFile
	 *            压缩后的文件
	 * @return void
	 * @date 2015-11-28
	 * @author wenbin.xia@midea.com.cn
	 */
	public static void createZipFile(String srcFilePath, String zipFilePath) throws Exception{
		byte[] buf = new byte[1024];
		ZipOutputStream out = null;
		try {
			File srcFile = new File(srcFilePath);
			File zipFile = new File(zipFilePath);
			// Create the ZIP file
			out = new ZipOutputStream(new FileOutputStream(zipFile));
			// Compress the files
			FileInputStream in = new FileInputStream(srcFile);
			// Add ZIP entry to output stream.
			out.putNextEntry(new ZipEntry(srcFile.getName()));
			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			// Complete the entry
			out.closeEntry();
			in.close();
			// Complete the ZIP file
			out.close();
		} catch (Exception e) {
			logger.error("ZipUtil zipFiles exception:"+e.getMessage(),e);
			throw e;
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("ZipUtil zipFiles IOException:"+e.getMessage(),e);
				}
			}
		}
	}
	/**
	 * 
	 * @Description: 删除文件
	 * @param
	 * @return void
	 * @date 2015-11-28
	 * @author ZHUAL
	 */
	public static void deleteTempFile(String srcFilePath) {
		File file = new File(srcFilePath);
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		}
	}
	
	/**
	 * 
	 * 
	 * @Description: 此方法压缩指定的文件
	 * @param srcFile
	 *            要进行压缩的文件
	 * @param zipFile
	 *            压缩后的文件
	 * @return void
	 * @date 2015-11-28
	 * @author ZHUAL
	 */
	public static void createZipFile(Set<String> srcFilePathSet, String zipFilePath) throws Exception{
		if(srcFilePathSet != null && srcFilePathSet.size() > 0){
			byte[] buf = new byte[1024];
			ZipOutputStream out = null;
			try {
				File zipFile = new File(zipFilePath);
				// Create the ZIP file
				out = new ZipOutputStream(new FileOutputStream(zipFile));
				for(String srcFilePath : srcFilePathSet){
					File srcFile = new File(srcFilePath);
					// Compress the files
					FileInputStream in = new FileInputStream(srcFile);
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(srcFile.getName()));
					// Transfer bytes from the file to the ZIP file
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					// Complete the entry
					out.closeEntry();
					in.close();
				}
				// Complete the ZIP file
				out.close();
			} catch (IOException e) {
				logger.error("ZipUtil zipFiles exception:"+e);
				throw e;
			} finally {
				if (out != null) {
					try {
						out.close();
					} catch (IOException e) {
						logger.error("ZipUtil zipFiles IOException:"+e.getMessage(),e);
					}
				}
			}
		}
	}
	/**
	 * 
	 * @Description: 删除文件
	 * @param
	 * @return void
	 * @date 2015-11-28
	 * @author ZHUAL
	 */
	public static void deleteTempFile(Set<String> srcFilePathSet) {
		if(srcFilePathSet!=null && srcFilePathSet.size() > 0){
			for(String srcFilePath: srcFilePathSet){
				File file = new File(srcFilePath);
				if (file.exists() && !file.isDirectory()) {
					file.delete();
				}
			}
		}
	}
}
