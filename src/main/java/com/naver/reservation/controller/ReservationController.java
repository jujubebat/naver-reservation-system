package com.naver.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationController {

  @GetMapping(path = "/mainpage")
  public String main() {
    return "mainpage";
  }

}
