package com.naver.reservation.dto.api.returnObject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

public @Data class UserReservationList { // 예약정보조회 객체 반환 객체
  private List<ReservationInfoDetail> reservations;
  private int size;

  public UserReservationList(List<ReservationInfoDetail> reservations) {
    this.reservations = reservations;
    this.size = reservations.size();
  }
}
