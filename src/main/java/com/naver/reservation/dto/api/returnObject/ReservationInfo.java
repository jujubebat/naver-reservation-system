package com.naver.reservation.dto.api.returnObject;

import java.util.List;

import com.naver.reservation.dto.api.Reservation;
import com.naver.reservation.dto.api.ReservationInfoPrice;

import lombok.Data;

public @Data class ReservationInfo extends Reservation {

  private boolean cancelYn;
  private String createDate;
  private String modifyDate;
  private List<ReservationInfoPrice> prices;
  private String reservationDate;
  private int reservationInfoId;

}
