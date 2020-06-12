package com.naver.reservation.dao;

public class ReservationInfoDaoSqls {
	  
	  public static final String SELECT_BY_EMAIL =  
			    "SELECT id                AS reservationInfoId, \r\n" 
		      + "       product_id        AS productId, \r\n" 
		      + "       reservation_email AS reservationEmail, \r\n" 
		      + "       reservation_name  AS reservationName, \r\n"
		      + "       reservation_tel   AS reservationTelephone, \r\n" 
		      + "       cancel_flag       AS cancelYn, \r\n" 
		      + "       create_date       AS createDate, \r\n" 
		      + "       modify_date       AS modifyDate, \r\n" 
		      + "       reservation_date  AS reservationDate, \r\n" 
		      + "       display_info_id   AS displayInfoId \r\n" 
		      + "FROM   reservation_info \r\n" 
		   	  + "WHERE  reservation_email = :email";
			 
	  public static final String SELECT_BY_ID =
			   "SELECT id                AS reservationInfoId, \r\n" 
              + "       product_id        AS productId, \r\n" 
			  + "       reservation_email AS reservationEmail, \r\n" 
			  + "       reservation_name  AS reservationName, \r\n"
			  + "       reservation_tel   AS reservationTelephone, \r\n" 
			  + "       cancel_flag       AS cancelYn, \r\n" 
			  + "       create_date       AS createDate, \r\n" 
		      + "       modify_date       AS modifyDate, \r\n" 
		      + "       reservation_date  AS reservationDate, \r\n" 
		      + "       display_info_id   AS displayInfoId \r\n" 
			  + "FROM   reservation_info \r\n" 
			  + "WHERE  id = :reservationInfoId";
	  
	  public static final String UPDATE_CANCEL_FLAG_BY_ID =
			    "UPDATE reservation_info \r\n"
			  + "SET    cancel_flag = :cancelFlag, modify_date = :modifyDate \r\n"
			  + "WHERE  id = :reservationInfoId";
}