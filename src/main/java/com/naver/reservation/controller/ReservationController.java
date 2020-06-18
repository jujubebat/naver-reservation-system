package com.naver.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

  @GetMapping(path = "/mainpage")
  public String mainPage() {
    return "mainpage";
  }

  @GetMapping(path = "/detail")
  public String detail() {
    return "detail";
  }

  @GetMapping(path = "/review")
  public String review() {
    return "review";
  }
  
  @GetMapping(path = "/reserve")
  public String reserve() {
    return "reserve";
  }
  
  @GetMapping(path = "/bookinglogin")
  public String bookinglogin() {
    return "bookinglogin";
  }
  
  @GetMapping(path = "/myreservation")
  public String myreservation() {
    return "myreservation";
  }
  
  @GetMapping(path = "/reviewWrite")
  public String reviewWrite() {
    return "reviewWrite";
  }
  
}
