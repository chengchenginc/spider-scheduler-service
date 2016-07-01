package com.nearco.cc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param file
	 */
	public static void writeResponse(final HttpServletRequest request,final HttpServletResponse response,final File file){
		response.addHeader("Content-Disposition", "attachment;filename=" + file.getName());
		response.addHeader("Content-Length", "" + file.length());
		ServletOutputStream out;
		FileChannel fileChannel = null;
		try (FileInputStream fis = new FileInputStream(file)) {
			fileChannel = fis.getChannel();
			out = response.getOutputStream();
			/*long size = fileChannel.size();
			final MappedByteBuffer mbbi = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0L, size);
			mbbi.flip();
			byte[] tempb = new byte[mbbi.limit()]; 
			mbbi.get(tempb);*/
			InputStream ins = new BufferedInputStream(fis);
			byte[] buffer = new byte[ins.available()];
			ins.read(buffer);
			ins.close();
			out.write(buffer);
			out.flush();
			out.close();
		} catch (final Exception e) {

		} finally {
			if (fileChannel != null) {
				try {
					fileChannel.close();
				} catch (final IOException e) {
					// ignore
				}
			}
		}
	}
	
}
