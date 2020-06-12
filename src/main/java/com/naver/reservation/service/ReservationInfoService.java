 package com.naver.reservation.service;

import java.util.List;

import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.ReservationInfoDetail;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;

public interface ReservationInfoService {
	public ReservationInfo addReservationInfo(ReservationInfoParam reservationInfoParam);
	
	public List<ReservationInfoDetail> getReservationInfoDetailListByEmail(String email);
	
	public int getReservationPriceInfo(int reservationInfoId);
	
	public ReservationInfo getReservationInfoResultById(int reservationInfoId);
	
	public ReservationInfo cancelReservation(int reservationInfoId);
	
}
