package com.energyfuture.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.energyfuture.project.model.CoomFile;
import com.energyfuture.project.model.TrIncheckEquipment;
import com.energyfuture.project.model.TrIncheckInfo;
import com.energyfuture.project.model.TrIncheckRelevance;
import com.energyfuture.project.model.TrIncheckType;
import com.energyfuture.project.service.DepartmentService;
import com.energyfuture.project.service.IncheckService;
import com.energyfuture.project.service.UserInfoService;
import com.energyfuture.project.util.BaseUtils;
import com.energyfuture.project.util.CompressedUtil;
import com.energyfuture.project.util.Constants;
import com.energyfuture.project.util.CreateJFreeChartXYLine;
import com.mysql.fabric.xmlrpc.base.Array;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import sun.misc.BASE64Encoder;

@SuppressWarnings({ "restriction", "unused" })
@Controller
@RequestMapping("/incheck")
public class ReportShowController {


	// 图片转码
	public String ImagetoEncode(String path) {
		InputStream in = null;
		byte[] data = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				return "";
			}
			in = new FileInputStream(path);

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
//		String coders=encoder.encode(data);
//		return coders.replace(" ", "");
		return encoder.encode(data);
	}

	/**
	 * 删除目录及其子文件
	 * 
	 * @param file
	 */
	public static void deleteDir(File file) {
		if (file.exists()) {
			if (file.isFile()) {
				file.delete();
			} else if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteDir(files[i]);
				}
				file.delete();
			}
		}
	}
	/**
	 * 下载报告（一个压缩包内含有多个）
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/exportword")
	public String downLoadWordAction(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html;charset=utf-8");
		String recordid = request.getParameter("id") == "" ? null : request.getParameter("id");// 获取前台ID
		// --检测报告(检测记录)
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			File f = null;
			// 文档名称
			String wordName = "";
			// 获取文档保存地址templates
			String URL="E:/";
		//	System.out.println(URL+"模板文档地址！！！！！！！！！！！！！");
			String aa = URL + "/FILE/TELE/";
			String aaa = URL+"/FILE/报告.zip";
			String zipname="报告.zip";
			
			String[] ss={"文档1","文档2"};
		
			f = new File(aa);
			if (!f.exists()) {
				f.mkdirs();
			} else {
				
				f.mkdirs();
			}
			for (int h = 0; h < ss.length; h++) {
				
				wordName = ss[h]+"文档名称";
				Template template = null;
				Map<String, Object> root = null;
				Writer out = null;
				// 模板位置
				Configuration configuration = new Configuration();
				//System.out.println("--------------------"+URL);
				configuration.setDirectoryForTemplateLoading(new File(URL));// 模板路径
				configuration.setObjectWrapper(new DefaultObjectWrapper());
				root = new HashMap<String, Object>();
				out = new OutputStreamWriter(new FileOutputStream(aa + wordName + ".doc"), "UTF-8");
				template = configuration.getTemplate("WORD.ftl", "UTF-8");
				
				
				List<Map<String, Object>> list2=new ArrayList<Map<String, Object>>();
				Map<String, Object> map2 = new HashMap<String, Object>();
				map2.put("name", "希尔瓦娜斯");
				map2.put("age", "保密");
				map2.put("sex", "女");
				list2.add(map2);
				Map<String, Object> map3 = new HashMap<String, Object>();
				map3.put("name", "巫妖王");
				map3.put("age", "保密");
				map3.put("sex", "男");
				list2.add(map3);
				
				root.put("Peolist", list2);
				root.put("wordname", ss[h]);
				
				
				String img="<w:pict><v:shapetype id='_x0000_t75' coordsize='21600,21600' o:spt='75' o:preferrelative='t' path='m@4@5l@4@11@9@11@9@5xe' filled='f' stroked='f'><v:stroke joinstyle='miter'/><v:formulas><v:f eqn='if lineDrawn pixelLineWidth 0'/><v:f eqn='sum @0 1 0'/><v:f eqn='sum 0 0 @1'/><v:f eqn='prod @2 1 2'/><v:f eqn='prod @3 21600 pixelWidth'/><v:f eqn='prod @3 21600 pixelHeight'/><v:f eqn='sum @0 0 1'/><v:f eqn='prod @6 1 2'/><v:f eqn='prod @7 21600 pixelWidth'/><v:f eqn='sum @8 21600 0'/><v:f eqn='prod @7 21600 pixelHeight'/><v:f eqn='sum @10 21600 0'/></v:formulas><v:path o:extrusionok='f' gradientshapeok='t' o:connecttype='rect'/><o:lock v:ext='edit' aspectratio='t'/></v:shapetype>"
						+ "<w:binData w:name='wordml://02000001.jpg' xml:space='preserve'>"+ImagetoEncode("E://img.JPG")+"</w:binData>"
								+ "<v:shape id='图片 1' o:spid='_x0000_i1027' type='#_x0000_t75' style='width:132.75pt;height:132.75pt;visibility:visible;mso-wrap-style:square'><v:imagedata src='wordml://02000001.jpg' o:title=''/></v:shape></w:pict>";
				
				root.put("img", img);
				
				//System.out.println("*******************************************"+root.get("img"));
				template.process(root, out);// 写入目标文件
				System.out.println("写入目标文件完成！-------------------------------");
				out.flush();
				out.close();
				
			}
			CompressedUtil compressed = new CompressedUtil();
			//把压缩aa为aaa
			compressed.zip(aa,aaa);
			// 以流的形式下载文件。
			File updatefile  = new File(aaa);
			if(updatefile.exists())
			{
				System.out.println("存在");
			}else{
				System.out.println("？？？？？？？？？？？？？？？？？？？？");
			}
			InputStream fis = new BufferedInputStream(new FileInputStream(aaa));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			response.setContentType("application/force-download");// 设置强制下载不打开
			response.addHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(zipname,"utf-8"));
			response.addHeader("Content-Length", "" + updatefile.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();

			deleteDir(f);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("state", "error");
		}
		return null;
	}
	

	
}
