package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class Product {

  private int displayInfoId;
  private int productId;
  private String productDescription;
  private String placeName;
  private String productContent;
  private String productImageUrl;

}

