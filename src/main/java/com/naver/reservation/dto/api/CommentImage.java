package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class CommentImage {
	
  private int imageId;
  private int reservationInfoId;
  private int reservationUserCommentId;
  private String fileId;
  private String fileName;
  private String saveFileName;
  private String contentType;
  private boolean deleteFlag;
  private String createDate;
  private String modifyDate;
  
}
