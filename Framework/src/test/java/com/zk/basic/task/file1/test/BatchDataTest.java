package com.zk.basic.task.file1.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class BatchDataTest {
	
	public static void main(String[] args) throws IOException, Throwable {
		File file =new File("D:\\test.txt");
		Writer out =new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		for(int i=1;i<=56213;i++){
			out.write("左凯,1,"+i+"\r\n");
		}
		out.close();
	}
}