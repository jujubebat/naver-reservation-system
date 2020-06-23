package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class ProductPrice {
  
  private int productPriceId;
  private int productId;
  private String priceTypeName;
  private int price;
  private int discountRate;
  private String createDate;
  private String modifyDate;
  
}