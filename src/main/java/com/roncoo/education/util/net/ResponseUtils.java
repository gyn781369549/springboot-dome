package com.roncoo.education.util.net;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * 异步返回各种格式数据：json xml text Protobuf
 * 
 * @author: gyn
 * @date: 2017年12月8日
 */
public class ResponseUtils {
	private ResponseUtils() {

	}

	// 发送内容 "application/json;charset=UTF-8"
	public static void render(HttpServletResponse response, String contentType, String text) {
		response.setContentType(contentType);
		try {
			response.getWriter().write(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 发送的是JSON
	public static void renderJSON(HttpServletResponse response, String text) {
		render(response, "application/json;charset=UTF-8", text);
	}

	// 发送xml
	public static void renderXml(HttpServletResponse response, String text) {
		render(response, "application/xml;charset=UTF-8", text);
	}

	// 发送text
	public static void renderText(HttpServletResponse response, String text) {
		render(response, "application/plain;charset=UTF-8", text);
	}

	// 发送Protobuf
	public static void renderProtobuf(HttpServletResponse response, byte[] by) {
		OutputStream outputStream = null;
		try {

			response.setContentType("application/x-protobuf");
			outputStream = response.getOutputStream();
			outputStream.write(by);
			outputStream.flush();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("renderProtobuf发送异常" + e.getMessage());
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("outputStream.close()异常" + e.getMessage());
				}
			}
		}

	}

}
