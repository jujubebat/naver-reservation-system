package com.naver.reservation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.naver.reservation.controller"})
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

  private static final int SEC_IN_A_YEAR = 60 * 60 * 24 *356;

  /**
   * 특정 URL 요청을 특정 폴더로 매핑해준다.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/img_map/**").addResourceLocations("/img_map/")
        .setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(SEC_IN_A_YEAR);
    registry.addResourceHandler("/font/**").addResourceLocations("/font/").setCachePeriod(SEC_IN_A_YEAR);
  }

  /**
   * default servlet handler를 사용하게 해준다. 매핑정보가 없는 URL정보는 스프링의 default servlet http request handle이
   * 처리하도록 해준다. 매핑이 없는 URL이 넘어왔을때 WAS의 default servlet이 스태틱한 자원을 읽어서 보여줄 수 있겠끔 설정하는 것.
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  /**
   * 특정 URL에 대한 처리를 Controller 필요없이 보여주는 설정.
   */
  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }

  /**
   * 특정 뷰의 위치와 파일 형식을 설정.
   */
  @Bean
  public InternalResourceViewResolver getInternalResourceViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/views/");
    resolver.setSuffix(".jsp");
    return resolver;
  }
}
