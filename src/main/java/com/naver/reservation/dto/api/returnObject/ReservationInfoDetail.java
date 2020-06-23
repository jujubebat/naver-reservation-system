package com.naver.reservation.dto.api.returnObject;

import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.Reservation;
import lombok.Data;

public @Data class ReservationInfoDetail extends Reservation {

  private boolean cancelYn;
  private String createDate;
  private DisplayInfo displayInfo;
  private String modifyDate;
  private String reservationDate;
  private int reservationInfoId;
  private int totalPrice;

}
