package com.energyfuture.project.util;



import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class TestFingerPrint {
	
	public static void main(String[] args){
		FingerPrint fp1;
		FingerPrint fp2;
		try {
			fp1 = new FingerPrint(ImageIO.read(new File("E:\\FLIR14039.jpg")));
			fp2 =new FingerPrint(ImageIO.read(new File("E:\\FLIR14040.jpg")));
			System.out.println(fp1.toString(true));
			System.out.printf("sim=%f",fp1.compare(fp2));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	}
