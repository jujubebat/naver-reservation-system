package com.naver.reservation.dto.api.paramObject;

import java.util.List;

import com.naver.reservation.dto.api.Reservation;
import com.naver.reservation.dto.api.ReservationInfoPrice;

import lombok.Data;
import lombok.EqualsAndHashCode;

public @Data class ReservationInfoParam extends Reservation {

  private List<ReservationInfoPrice> prices;
  private String reservationYearMonthDay;

}

/*
 * [질문] : POST 방식으로 인자를 받을 때 이렇게 따로 파라미터 객체를 정의해서 받았습니다.
 *         이런식으로 구현하는 방식이 좋은건지 궁금합니다.
 * */
