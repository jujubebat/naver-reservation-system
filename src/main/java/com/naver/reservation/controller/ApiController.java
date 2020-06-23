package com.naver.reservation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class ApiController {
  
  @GetMapping("/randomDate")
  public Map<String, String> getRandomDate() {
    Date date = new Date();
    int random = (int) (Math.random() * 5);
    date.setDate(date.getDate() + random);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = formatter.format(date);

    Map<String, String> randomdate = new HashMap<>();
    randomdate.put("reservationDate", formattedDate);

    return randomdate;
  }
  
}
