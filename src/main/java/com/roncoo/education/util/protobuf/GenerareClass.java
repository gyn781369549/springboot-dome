package com.roncoo.education.util.protobuf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * protoc.exe
 * 
 * @author gyn
 * 批量生产protobuf java
 */
public class GenerareClass {
	public static void main(String[] args) {
		String path = System.getProperty("user.dir");// 项目地址
		String filePath= path + "\\" + "proto";
		System.out.println(filePath);
		List<File> files = getFiles(filePath);
		for (File f : files) {
			System.out.println("读取出来的文件---"+f.getName());
			String protoFile = f.getName();

			String strCmd = path + "\\google\\src\\protoc.exe -I=.\\proto --java_out=.\\src\\main\\java\\ .\\proto\\" + protoFile; // proto文件地址
			try {
				// 通过执行cmd命令调用protoc.exe程序
				Runtime.getRuntime().exec(strCmd);
				System.out.println(protoFile + ".java 生成成功！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 读取文件夹下的文件
	 * 
	 * @param path
	 * @return
	 */
	public static List<File> getFiles(String path) {
		File root = new File(path);
		List<File> files = new ArrayList<File>();
		if (!root.isDirectory()) {
			files.add(root);
		} else {
			File[] subFiles = root.listFiles();
			for (File f : subFiles) {
				//指定的文件后缀名
				if (f.getName().contains(".proto")) {
					files.addAll(getFiles(f.getAbsolutePath()));
				}

			}
		}
		return files;
	}

}
