package com.naver.reservation.controller;

import com.naver.reservation.dto.api.returnObject.PromotionList;
import com.naver.reservation.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/promotions")
public class PromotionsApiController {

  private final PromotionService promotionService;
  
  @Autowired
  public PromotionsApiController(PromotionService promotionService){
    this.promotionService = promotionService;
  }
  
  @GetMapping
  public PromotionList getPromotions() {
    return new PromotionList(promotionService.getPromotions());
  }
  
}
