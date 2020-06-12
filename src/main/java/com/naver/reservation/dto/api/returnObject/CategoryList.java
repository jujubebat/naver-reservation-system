package com.naver.reservation.dto.api.returnObject;

import java.util.Date;
import java.util.List;

import com.naver.reservation.dto.api.Category;
import com.naver.reservation.dto.api.ReservationInfoPrice;
import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;

import lombok.Data;

public @Data class CategoryList {

  private List<Category> items;

  public CategoryList(List<Category> items) {
    this.items = items;
  }

}
