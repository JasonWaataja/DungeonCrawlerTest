package com.waataja.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Files {
	public static FileInputStream inputStream(String fileName) {
		FileInputStream stream = null;
		try {
			stream = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stream;
	}
	public static String appendFile(String str, String suf) {
		return str + "/" + suf;
	}
	public static ArrayList<File> filesIn(File folder) {
		ArrayList<File> files = new ArrayList<File>();
		for (File file : folder.listFiles()) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				files.addAll(filesIn(file));
			}
		}
		return files;
	}
}
