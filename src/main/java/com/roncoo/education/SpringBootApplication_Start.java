package com.roncoo.education;

import org.apache.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;





@ServletComponentScan
@SpringBootApplication
@ImportResource(locations = { "classpath:druid-bean.xml" })
@EnableTransactionManagement
public class SpringBootApplication_Start {
	public static Logger logger = Logger.getLogger(SpringBootApplication_Start.class);  

	public static void main(String[] args) {
		SpringApplication.run(SpringBootApplication_Start.class, args);
		logger.info("----------------------springboot启动成功！----------------------");
	}

	/**
	 * 设置跨域
	 * 
	 * @return
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				/**
				 * 设置允许的路径和 url前缀
				 */
				registry.addMapping("/**").allowedOrigins("http://localhost:8011");
			}
		};
	}

	
}
