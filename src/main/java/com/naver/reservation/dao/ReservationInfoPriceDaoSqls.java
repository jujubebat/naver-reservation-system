package com.naver.reservation.dao;

public class ReservationInfoPriceDaoSqls {

	public static final String SELECT_SCORE_TOTAL_PRICE_BY_ID = 
					  " SELECT Sum(count * price) \r\n" 
					+ "	FROM   reservation_info_price \r\n" 
					+ "	       INNER JOIN product_price \r\n" 
					+ "                ON reservation_info_price.product_price_id = product_price.id \r\n" 
					+ " WHERE  reservation_info_id = :reservationInfoId";
	
	public static final String SELECT_BY_RESERVATION_INFO_ID = 
			  " SELECT *, \r\n" 
			+ "        id AS reservationInfoPriceId \r\n" 
			+ " FROM   reservation_info_price \r\n" 
			+ " WHERE  reservation_info_id = :reservationInfoId";
	
}
