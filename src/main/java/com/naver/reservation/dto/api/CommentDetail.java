package com.naver.reservation.dto.api;

import java.util.List;
import lombok.Data;

public @Data class CommentDetail extends Comment {
  
  private String reservationName;
  private String reservationTelephone;
  private String reservationEmail; 
  private String reservationDate;
  private List<CommentImage> commentImages;
   
}
