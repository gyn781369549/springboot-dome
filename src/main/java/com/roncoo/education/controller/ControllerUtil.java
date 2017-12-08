package com.roncoo.education.controller;


import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


import com.roncoo.education.util.net.ResponseUtils;

@SuppressWarnings("all")
public class ControllerUtil {
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected  static ResponseUtils responseUtils;
	protected void writeTo(byte[] by, HttpServletResponse response){
		
		responseUtils.renderProtobuf(response, by);
		
	}
	protected void writeTo(String json, HttpServletResponse response){
		
		responseUtils.renderJSON(response, json);
		
	}
	

}
