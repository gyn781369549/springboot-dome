package com.roncoo.education.util.log4j;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.roncoo.education.SpringBootApplication_Start;
public class Log4j_Test {

	public static final Logger logger = Logger.getLogger(SpringBootApplication_Start.class);



	static {
		try {
			// 初始化log4j   ks
			
			String log4jPath = "";

			// 配置本地地址
			log4jPath = Log4j_Test.class.getClassLoader().getResource("").getPath() + "log4j.properties";
			logger.info("Log4j线下开发模式初始化。。。");

			logger.info("初始化Log4j。。。。");
			logger.info("path is " + log4jPath);
			PropertyConfigurator.configure(log4jPath);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

}
