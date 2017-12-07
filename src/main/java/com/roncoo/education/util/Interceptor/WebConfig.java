package com.roncoo.education.util.Interceptor;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	protected final Logger logger = Logger.getLogger(this.getClass());
    private ApplicationContext applicationContext;

    public WebConfig(){
        super();
    }
    /**
     * 重写addResourceHandlers（）后 /static/ 下的静态资源可以访问了
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	 logger.info("------addResourceHandlers------");
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/templates/");

        super.addResourceHandlers(registry);  
        }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	logger.info("------setApplicationContext------");
        this.applicationContext = applicationContext;
    } 
    /**
     * 设置拦截路径，放行路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	 logger.info("------addInterceptors------");
        //拦截规则：除了login，/api/selectU其他都拦截判断
//        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/adminUser/login").excludePathPatterns("/api/selectU");
        super.addInterceptors(registry);
    }

}
