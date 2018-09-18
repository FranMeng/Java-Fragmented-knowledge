package com.energyfuture.bde.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.tools.zip.ZipOutputStream;

public class CompressedUtil {

	static final int BUFFER = 8192;   

	/*
	 * inputFileName 输入一个文件夹 zipFileName 输出一个压缩文件夹
	 */
	public void zip(String inputFileName, String destFile) throws Exception {
		long time = System.currentTimeMillis();
		System.out.println("开始压缩：" + inputFileName);
		zip(destFile, new File(inputFileName));
		System.out.println("压缩完成，共用时 " + (System.currentTimeMillis() - time)/1000 + " 秒");
	}

	private void zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, inputFile, "");
		out.close();
	}
	
	


	public static void CopyFileWjj(String sourcePath, String targetPath) {
		try {
			//sourcePath 文件路径
			//targetPath 需要转的路径
			File file = new File(sourcePath);
			File filelist[] = null;
			if (file.exists()) {
				filelist = file.listFiles();
			}
			if(file!=null && filelist.length!=0){
				for (int i = 0; i < filelist.length;i++) {
					fileChannelCopy(filelist[i].getCanonicalPath(),targetPath+filelist[i].getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public static void fileChannelCopy(String sourcePath, String targetPath) {
		File source = new File(sourcePath);
		File target = new File(targetPath);

		if (!source.exists()) {
			return;
		}
		
		
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;

		try {
			fi = new FileInputStream(source);
			fo = new FileOutputStream(target);
			// 得到对应的文件通道
			in = fi.getChannel();
			// 得到对应的文件通道
			out = fo.getChannel();
			// 连接两个通道，并且从in通道读取，然后写入out通道
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	private void zip(ZipOutputStream out, File f, String base) throws Exception {

		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			//out.putNextEntry(new org.apache.tools.zip.ZipEntry(base + "/"));
			base = base.length() == 0 ? "" : base + "/";
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			out.putNextEntry(new org.apache.tools.zip.ZipEntry(base));
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			int count = 0;
			byte data[] = new byte[BUFFER];   
			while ((count = in.read(data, 0, BUFFER))!= -1) {
				out.write(data, 0, count);
			}
			System.out.println("fileName: " + f.getName());
			in.close();
		}


	}
	public static void main(String[] temp) {
		System.out.println("=-=");
		CompressedUtil compressed = new CompressedUtil();
		try {
			//compressed.zip("D:\\SYMP\\IMG\\ddc3c77f-1ad8-40d5-95c1-3d2217ff00be\\cb32aa21-7457-4482-b7d1-5107de819639\\014.jpg", "D:\\aaaaaaaaaaaaaaaaaaaaaa\\a.zip");// 你要压缩的文件夹
			compressed.zip("D:\\SYMP\\USER", "D:\\SYMP\\A.zip");// 你要压缩的文件夹
			//compressed.zip("D:\\SYMPDOC\\古渡变电站例行巡检2015年29月18日", "D:\\SYMPDOC\\古渡变电站例行巡检2015年29月18日.zip");// 你要压缩的文件夹
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
