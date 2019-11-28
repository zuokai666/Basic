package com.zk.basic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.task.config.FileWriterConfig;

public class FileMerger {
	
	private static final Logger log = LoggerFactory.getLogger(FileMerger.class);
	
	public static String END = FileWriterConfig.FILE_END;
	
	public static void newFile(String basePath){
		File file = new File(basePath + END);
		deleteFile(file);
		file.mkdirs();
	}
	
	static boolean deleteFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
            	deleteFile(f);
            }
        }
        return file.delete();
    }

	public static void merge(String basePath) {
		log.debug("开始合并文件...");
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(basePath));
			File directory = new File(basePath + END);
			File[] files = directory.listFiles();
			for(File file : files){
				BufferedReader br = new BufferedReader(new FileReader(file));
				String line;
                while((line=br.readLine())!=null) {
                    bw.write(line);
                    bw.newLine();
                }
                br.close();
			}
			bw.close();
			log.debug("合并文件成功");
		} catch (IOException e) {
			log.error("", e);
		}
	}
}