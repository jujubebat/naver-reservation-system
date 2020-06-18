package com.naver.reservation.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.naver.reservation.dao.ProductPriceDao;
import com.naver.reservation.dao.ReservationInfoDao;
import com.naver.reservation.dao.ReservationInfoPriceDao;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.ReservationInfoDetail;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;
import com.naver.reservation.service.DisplayInfoService;
import com.naver.reservation.service.ReservationInfoService;

@Service
public class ReservationInfoService {
  private final ReservationInfoDao reservationInfoDao;
  private final ProductPriceDao productPriceDao;
  private final ReservationInfoPriceDao reservationInfoPriceDao;
  private final DisplayInfoService displayInfoService;

  @Autowired
  public ReservationInfoService(ReservationInfoDao reservationInfoDao,
      ProductPriceDao productPriceDao, ReservationInfoPriceDao reservationInfoPriceDao,
      DisplayInfoService displayInfoService) {
    this.reservationInfoDao = reservationInfoDao;
    this.productPriceDao = productPriceDao;
    this.reservationInfoPriceDao = reservationInfoPriceDao;
    this.displayInfoService = displayInfoService;
  }

  @Transactional
  public ReservationInfo addReservationInfo(ReservationInfoParam reservationInfoParam) {
    
    ReservationInfo reservationInfoResult = new ReservationInfo();

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = formatter.format(date);

    // 아래 3 필드는 insertReservationInfo를 수행하기전 반드시 넣어야함.
    reservationInfoResult.setCancelYn(false);
    reservationInfoResult.setCreateDate(formattedDate);
    reservationInfoResult.setModifyDate(formattedDate);

    reservationInfoResult.setDisplayInfoId(reservationInfoParam.getDisplayInfoId());
    reservationInfoResult.setProductId(reservationInfoParam.getProductId());
    reservationInfoResult.setReservationEmail(reservationInfoParam.getReservationEmail());
    reservationInfoResult.setReservationName(reservationInfoParam.getReservationName());
    reservationInfoResult.setReservationTelephone(reservationInfoParam.getReservationTelephone());
    reservationInfoResult.setReservationDate(reservationInfoParam.getReservationYearMonthDay());

    // reservation_info 테이블 삽입.
    int reservationInfoId = reservationInfoDao.insertReservationInfo(reservationInfoResult);

    reservationInfoResult.setReservationInfoId(reservationInfoId);

    // reservation_info_price 테이블 삽입.
    reservationInfoResult.setPrices(reservationInfoPriceDao
        .insertReservationInfoPriceList(reservationInfoParam.getPrices(), reservationInfoId));

    return reservationInfoResult;
  }

  public List<ReservationInfoDetail> getReservationInfoDetailListByEmail(String email) {

    List<ReservationInfoDetail> reservationInfoDetailList = reservationInfoDao.selectByEmail(email);

    // 객체는 참조형태이므로 이런식으로  원본 리스트 수정이 가능하다.
    for (ReservationInfoDetail reservationInfoDetail : reservationInfoDetailList) { 
                                                                                  
      DisplayInfo displayInfo =
          displayInfoService.getDisplayInfoById(reservationInfoDetail.getDisplayInfoId());
      
      int totalPrice = getReservationPriceInfo(reservationInfoDetail.getReservationInfoId());
      reservationInfoDetail.setTotalPrice(totalPrice);
      reservationInfoDetail.setDisplayInfo(displayInfo);
    }

    return reservationInfoDetailList;

  }

  public ReservationInfo getReservationInfoResultById(int reservationInfoId) {

    ReservationInfo reservationInfoResult = reservationInfoDao.selectById(reservationInfoId);

    reservationInfoResult.setPrices(reservationInfoPriceDao
        .insertReservationInfoPriceList(reservationInfoResult.getPrices(), reservationInfoId));

    return reservationInfoResult;

  }

  public int getReservationPriceInfo(int reservationInfoId) {

    return reservationInfoPriceDao.selectTotalPriceById(reservationInfoId);

  }

  @Transactional
  public ReservationInfo cancelReservation(int reservationInfoId) {

    if (reservationInfoDao.updateCancelYnById(reservationInfoId, true) == 1) { // 업데이트가 성공적으로 수행된다면,

      ReservationInfo reservationInfoResult = reservationInfoDao.selectById(reservationInfoId);
      reservationInfoResult
          .setPrices(reservationInfoPriceDao.selectByReservationInfoId(reservationInfoId));
      return reservationInfoResult;

    } else {
      return null;
    }
  }

}

