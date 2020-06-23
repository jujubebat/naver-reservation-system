package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class Comment {
  
  private int commentId;
  private int productId;
  private int reservationInfoId;
  private int score;
  private String comment;
  private String createDate;
  private String modifyDate;
  
}
