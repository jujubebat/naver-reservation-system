package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class DisplayInfo {
	
  private int productId;
  private int categoryId;
  private int displayInfoId;
  private String categoryName;
  private String productDescription;
  private String productContent;
  private String productEvent;
  private String openingHours;
  private String placeName;
  private String placeLot; 
  private String placeStreet;
  private String telephone;
  private String homepage;
  private String email;
  private String createDate;
  private String modifyDate;

}
