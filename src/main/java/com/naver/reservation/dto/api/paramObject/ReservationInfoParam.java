package com.naver.reservation.dto.api.paramObject;

import java.util.List;

import com.naver.reservation.dto.api.Reservation;
import com.naver.reservation.dto.api.ReservationInfoPrice;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public @Data class ReservationInfoParam extends Reservation {

  private List<ReservationInfoPrice> prices;
  private String reservationYearMonthDay;

}
