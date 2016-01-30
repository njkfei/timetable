package com.niejinkun.timetable.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages ={  "com.niejinkun.timetable.config.service"})
@PropertySource(value = { "classpath:conf/viaggiatreno.properties" })
public class ViaggiatrenoConfig 
{
	
	@Value("${viaggiatreno.resturl}")
	private String baserul;
	
	@Value("${viaggiatreno.starturl}")
	private String starturl;
	// 必须要有这一行，否则上面的＠VALUE无法注入
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
}
