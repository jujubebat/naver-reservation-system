package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class Reservation {

  protected int displayInfoId;
  protected int productId;
  protected String reservationEmail;
  protected String reservationName;
  protected String reservationTelephone;

}