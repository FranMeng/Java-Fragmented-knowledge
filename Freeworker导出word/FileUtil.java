package com.energyfuture.bde.base.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import sun.misc.BASE64Decoder;

public class FileUtil {

	
	/**
	 * 删除目录及其子文件
	 * @param file
	 */
	public static void deleteDir(File file) {
		if (file.exists()) { 
			if (file.isFile()) { 
				file.delete(); 
			} else if (file.isDirectory()) { 
				File[] files = file.listFiles(); 
				for (int i = 0;i < files.length;i ++) { 
					deleteDir(files[i]); 
				}  
				file.delete(); 
			}  
		}
	}

	
    /*
     * 二进制转成图片
     */
	public static void base64StringToImage(String base64String,String url){      
        try {      
        	BASE64Decoder decoder = new sun.misc.BASE64Decoder();  
            byte[] bytes1 = decoder.decodeBuffer(base64String);      
                  
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);      
            BufferedImage bi1 =ImageIO.read(bais);      
            File w2 = new File(url);//可以是jpg,png,gif格式      
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
    } 
	
	
	
	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.isDirectory()) {
			File[] ff = file.listFiles();
			for (int i = 0; i < ff.length; i++) {
				deleteFile(ff[i].getPath());
			}
		}
		file.delete();
	}

	public static void createDirectory(String path) {
		File file = new File(path);

		if (file.exists()) {
			deleteFile(path);
		}

		file.mkdirs();
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
	

	/**
	 * 图片截取 参数：图片流，图片名称,截取后的宽 ,截取后的高； 返回值：图片流;
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static String  ImageCapture(File urlfile,String suffix,int width,int height) {
		String fileinfo="";
		try {
			String src=urlfile.getPath();
			// 取得图片读入器
			Iterator readers = ImageIO.getImageReadersByFormatName(suffix);
			ImageReader reader = (ImageReader) readers.next();
			// 取得图片读入流
			InputStream source = new FileInputStream(src);
			ImageInputStream iis = ImageIO.createImageInputStream(source);
			reader.setInput(iis, true);
			// 图片参数
			ImageReadParam param = reader.getDefaultReadParam();
			Rectangle rect = new Rectangle(0, 0, width, height);
			param.setSourceRegion(rect);
			BufferedImage bi = reader.read(0, param);
			ImageIO.write(bi, suffix, new File(src));
			//生成截取后的图片完成
			File f = new File(src);
			try {
				fileinfo =  ImageUtil.image2String(f);	
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileinfo;
	}
/*
	
	 * 华晨图片截取
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception
	 
	public static String  HuaChenImageCapture(File files,String filename) {
		try {
			String url="D:\\SYMP\\CUTOUT\\";

			File urlfile = new File(url);

			//判断文件夹是否存在,如果不存在则创建文件夹
			if (!urlfile.exists()) {
				urlfile.mkdirs();
			}
			
			// 2.先读取图片的尺寸
			File file = new File(files.getPath());
			int[]  arr = getImgWidthHeight(file);
			int windth =arr[0];
			int hight =arr[1];

			//3.判断是否截取
			if(windth>=1440   &&  hight>=900){ //需要截取的
				String suffix ="";  //图片类型 ， （后缀）
				if(filename.lastIndexOf(".")!=-1){
					suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
				}
				//ImageCapture(file,suffix,530,555);
				ImageCapture(file,suffix,1150,850);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}
*/
	public static void main(String[] args) {
		
		  String path="D:\\SYMP\\AAA\\";
		  File file=new File(path);
		  File[] tempList = file.listFiles();
		  for (int i = 0; i < tempList.length; i++) {
			  System.out.println(tempList[i].getPath());
			  FileUtil.HuaChenImageCapture(tempList[i],tempList[i].getName());
		  }
	/*	String fileinfo="";
		File f = new File("D:\\SYMP\\ss.jpg");
		try {
			FileUtil.HuaChenImageCapture(f,"ss.jpg");
//			fileinfo =  ImageUtil.image2String(f);	
			//String s =HuaChenImageCapture(fileinfo,"a.jpg");
			//System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	/**
	 * 华晨图片截取
	 * @param fileinfo
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static void  HuaChenImageCapture(File files,String filename) {
		//图片名字大于15位是才进入
		//if(filename.length() > 15){
			try {
				// 1.先读取图片的尺寸
				File file = new File(files.getPath());
				int[]  arr = getImgWidthHeight(file);
				int windth =arr[0];
				int hight =arr[1];
	
				//2.判断是否截取（一类导出的图片）
				if(windth == 1440 && hight == 900){ //需要截取的
					String suffix ="";  //图片类型 ， （后缀）
					if(filename.lastIndexOf(".")!=-1){
						suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
					}
					ImageCapture(file,suffix,1252,894);
				}
				//（另一类导出的图片，上海的图片后缀为jpg，其实为png）
				if(windth == 800 && hight == 600){ //需要截取的
					
					ImageCapture(file,"png",530,559);
				}
				
				//宁夏二队  红外检漏
				if(windth == 640 && hight == 480){ //需要截取的
					String suffix ="";  //图片类型 ， （后缀）
					if(filename.lastIndexOf(".")!=-1){
						suffix=filename.substring(filename.lastIndexOf(".")+1, filename.length());
					}
					ImageCapture(file,suffix,548,405);
				}
				
			} catch (
					Exception e) {
				e.printStackTrace();
			}
		//}
	}


	/**
	 * 获取图片宽度
	 * @param file  图片文件
	 * @return 宽度
	 */
	public static int[] getImgWidthHeight(File file) {
		InputStream is = null;
		BufferedImage src = null;
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			is.close();
			return new int[]{src.getWidth(),src.getHeight()};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new int[]{-1,-1};
	}

}
