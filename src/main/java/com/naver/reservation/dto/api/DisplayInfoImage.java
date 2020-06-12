package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class DisplayInfoImage {
	
  private int displayInfoImageId;
  private int displayInfoId;
  private int fileId;
  private String fileName;
  private String saveFileName;
  private String contentType; 
  private boolean deleteFlag;
  private String createDate;
  private String modifyDate;
  
}
