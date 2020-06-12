package com.naver.reservation.dto.api;

import java.util.List;

import lombok.Data;

public @Data class Comment {
  
  private int commentId;
  private int productId;
  private int reservationInfoId;
  private int score;
  private String comment;
  private String reservationName;
  private String reservationTelephone;
  private String reservationEmail; 
  private String reservationDate;
  private String createDate;
  private String modifyDate;
  private List<CommentImage> commentImages;
   
}
