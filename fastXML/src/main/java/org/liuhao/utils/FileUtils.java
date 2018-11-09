package org.liuhao.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtils {
	/**
	 * nio实现文件内容读取
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	public String getFileText(File file) throws Exception{
		FileChannel in = null;  
		long size = 0;
		byte[] by = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			in = fis.getChannel();  
		    size = in.size();  
		    by = new byte[(int) size];
		    MappedByteBuffer buf = in.map(FileChannel.MapMode.READ_ONLY, 0, size);  
		    buf.get(by);
		    in.close();  
	    }catch(Exception e){  
	    	e.printStackTrace();  
	    } finally {  
	    	try {
				in.close();
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    }
		return new String(by);
	}
	
	/**
	 * 文本追缴
	 * @param text
	 * @param file
	 */
	public static void appendFileText(String text, File file){
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(file,true));//true,则追加写入text文本  
			output.append(text);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
