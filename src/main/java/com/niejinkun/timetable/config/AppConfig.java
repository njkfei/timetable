package com.niejinkun.timetable.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.niejinkun.timetable.model.RouteInfo;
import com.niejinkun.timetable.model.TrainBaseInfo;
import com.niejinkun.timetable.model.TrainDetailInfo;

@Configuration
@ComponentScan(basePackages ={  "com.niejinkun.timetable.serviceimpl"})
@PropertySource(value = { "classpath:conf/viaggiatreno.properties" })
public class AppConfig 
{
	
	
}