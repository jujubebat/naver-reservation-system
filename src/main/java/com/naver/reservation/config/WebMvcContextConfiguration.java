package com.naver.reservation.config;

import com.naver.reservation.interceptor.LogInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.naver.reservation.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
  
  private static final int SEC_IN_A_YEAR = 60 * 60 * 24 * 356;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/font/**").addResourceLocations("/font/").setCachePeriod(SEC_IN_A_YEAR);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }

  @Bean
  public InternalResourceViewResolver getInternalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
  
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
          registry.addInterceptor(new LogInterceptor());
  }
  
  @Bean
  public MultipartResolver multipartResolver() {
      org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = 
          new org.springframework.web.multipart.commons.CommonsMultipartResolver();
      multipartResolver.setMaxUploadSize(1024 * 1024 * 10);
      return multipartResolver;
  }
  
}
