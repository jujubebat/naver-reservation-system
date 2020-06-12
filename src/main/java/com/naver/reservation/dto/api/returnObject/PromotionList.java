package com.naver.reservation.dto.api.returnObject;

import java.util.List;

import com.naver.reservation.dto.api.Promotion;

import lombok.Data;

public @Data class PromotionList {

  private List<Promotion> items;

  public PromotionList(List<Promotion> items) {
    this.items = items;
  }

}
