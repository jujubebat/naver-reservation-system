package com.naver.reservation.dto.api.paramObject;

import com.naver.reservation.dto.api.Reservation;
import com.naver.reservation.dto.api.ReservationInfoPrice;
import java.util.List;
import lombok.Data;


public @Data class ReservationInfoParam extends Reservation {

  private List<ReservationInfoPrice> prices;
  private String reservationYearMonthDay;

}
