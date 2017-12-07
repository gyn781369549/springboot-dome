package com.roncoo.education.controller;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

class ControllerUtil {
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected void writeTo(byte[] by, HttpServletResponse response) throws IOException {
		
		response.setContentType("application/x-protobuf");
		OutputStream outputStream = response.getOutputStream();
		outputStream.write(by);
		outputStream.flush();
		outputStream.close();
		
	}

}
