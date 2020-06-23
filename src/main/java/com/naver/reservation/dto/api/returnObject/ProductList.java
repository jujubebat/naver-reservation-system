package com.naver.reservation.dto.api.returnObject;

import com.naver.reservation.dto.api.Product;
import java.util.List;
import lombok.Data;

public @Data class ProductList {
  
  private int totalCount;
  private List<Product> items;

  public ProductList(int totalCount, List<Product> items) {
    this.totalCount = totalCount;
    this.items = items;
  }

}
