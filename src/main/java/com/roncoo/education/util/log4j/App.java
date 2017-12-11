package com.roncoo.education.util.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.LoggerFactory;
import org.mockito.internal.util.collections.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;

import com.roncoo.education.SpringBootStart;

public class App {

	public static final Logger logger = Logger.getLogger(SpringBootStart.class);

	@Value("${server.port}")
	private int port;
	@Value("${server.sessionTimeout}")
	private int sessionTimeout;

	static {
		try {
			// 初始化log4j
			String log4jPath = "";

			// 配置本地地址
			log4jPath = App.class.getClassLoader().getResource("").getPath() + "log4j.properties";
			logger.info("Log4j线下开发模式初始化。。。");

			logger.info("初始化Log4j。。。。");
			logger.info("path is " + log4jPath);
			PropertyConfigurator.configure(log4jPath);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
