package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class ProductImage {
	
  private int productId;
  private int productImageId;
  private String type;
  private int fileInfoId;
  private String fileName;
  private String saveFileName;
  private String contentType;
  private boolean deleteFlag;
  private String createDate;
  private String modifyDate;
 
}
