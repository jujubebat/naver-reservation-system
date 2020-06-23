package com.naver.reservation.interceptor;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LogInterceptor extends HandlerInterceptorAdapter {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private String  ip; 
  private String  url; 

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    String formatted = formatter.format(date);
    
    logger.debug("Controller {}가 종료되었습니다. 호출시간 : " + formatted+ "ip : " + ip + " url : " + url , handler.toString());
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    String formatted = formatter.format(date);
    ip = request.getRemoteAddr();
    url = request.getRequestURI();
    
    logger.debug("Controller {} 를 호출했습니다. 호출시간 : " + formatted + "ip : " + ip + " url : " + url , handler.toString());
    return true;
  }

}
