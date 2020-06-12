package com.naver.reservation.dao;

import static com.naver.reservation.dao.ReservationInfoPriceDaoSqls.SELECT_SCORE_TOTAL_PRICE_BY_ID;
import static com.naver.reservation.dao.ReservationInfoPriceDaoSqls.SELECT_BY_RESERVATION_INFO_ID;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.naver.reservation.dto.api.ReservationInfoPrice;

@Repository
public class ReservationInfoPriceDao {
  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;
  private RowMapper<ReservationInfoPrice> rowMapper =
      BeanPropertyRowMapper.newInstance(ReservationInfoPrice.class);

  public ReservationInfoPriceDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info_price")
        .usingGeneratedKeyColumns("id");
  }

  public List<ReservationInfoPrice> insertReservationInfoPriceList(
      List<ReservationInfoPrice> reservationInfoPrice, int productPriceId) {

    Map<String, Object> params = new HashMap<>();

    for (ReservationInfoPrice price : reservationInfoPrice) {

      price.setReservationInfoId(productPriceId);

      params.put("reservation_info_id", price.getReservationInfoId());
      params.put("product_price_id", price.getProductPriceId());
      params.put("count", price.getCount());

      int reservationInfoPriceId = insertAction.executeAndReturnKey(params).intValue();

      price.setReservationInfoPriceId(reservationInfoPriceId);

    }

    return reservationInfoPrice;

  }

  public int selectTotalPriceById(int productPriceId) {

    Map<String, Integer> params = new HashMap<>();
    params.put("reservationInfoId", productPriceId);

    if (jdbc.queryForObject(SELECT_SCORE_TOTAL_PRICE_BY_ID, params, int.class) == null) {
      return 0;
    } else {
      return jdbc.queryForObject(SELECT_SCORE_TOTAL_PRICE_BY_ID, params, int.class);
    }

  }

  public List<ReservationInfoPrice> selectByReservationInfoId(int reservationInfoId) {
    System.out.println(reservationInfoId);

    Map<String, Integer> params = new HashMap<>();
    params.put("reservationInfoId", reservationInfoId);
    return jdbc.query(SELECT_BY_RESERVATION_INFO_ID, params, rowMapper);

  }


}
