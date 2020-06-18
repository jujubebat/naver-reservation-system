package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class Reservation {

  protected int displayInfoId;
  protected int productId;
  protected String reservationEmail;
  protected String reservationName;
  protected String reservationTelephone;

}

/*
 * [질문] : ReservationInfoParam, ReservationInfoDetail, ReservationInfo 클래스는
 *         Reservation 클래스를 상속합니다. 프로젝트에서 주어진 swagger api 스펙과 똑같이 구현하려다보니
 *         이렇게 상속으로 구현하면 필드 중복이 줄어들것 같아서 해봤는데, 괜찮은 방법인지 궁금합니다.
 * */
 