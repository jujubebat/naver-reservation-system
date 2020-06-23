package com.naver.reservation.dto.api.returnObject;

import com.naver.reservation.dto.api.Category;
import java.util.List;

import lombok.Data;

public @Data class CategoryList {

  private List<Category> items;

  public CategoryList(List<Category> items) {
    this.items = items;
  }

}
