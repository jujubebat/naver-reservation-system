package com.naver.reservation.dao;

import static com.naver.reservation.dao.ReservationInfoDaoSqls.SELECT_BY_EMAIL;
import static com.naver.reservation.dao.ReservationInfoDaoSqls.SELECT_BY_ID;
import static com.naver.reservation.dao.ReservationInfoDaoSqls.UPDATE_CANCEL_FLAG_BY_ID;

import com.naver.reservation.dto.api.returnObject.ReservationInfo;
import com.naver.reservation.dto.api.returnObject.ReservationInfoDetail;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationInfoDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;
  private RowMapper<ReservationInfoDetail> detailRowMapper =
      BeanPropertyRowMapper.newInstance(ReservationInfoDetail.class);
  private RowMapper<ReservationInfo> resultRowMapper =
      BeanPropertyRowMapper.newInstance(ReservationInfo.class);

  public ReservationInfoDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_info")
        .usingGeneratedKeyColumns("id");
  }

  public int insertReservationInfo(ReservationInfo reservationInfoResult) {
    Map<String, Object> params = new HashMap<>();

    params.put("product_id", reservationInfoResult.getProductId());
    params.put("display_info_id", reservationInfoResult.getDisplayInfoId());
    params.put("reservation_name", reservationInfoResult.getReservationName());
    params.put("reservation_tel", reservationInfoResult.getReservationTelephone());
    params.put("reservation_email", reservationInfoResult.getReservationEmail());
    params.put("reservation_date", reservationInfoResult.getReservationDate());
    params.put("cancel_flag", false);
    params.put("create_date", reservationInfoResult.getCreateDate());
    params.put("modify_date", reservationInfoResult.getModifyDate());

    return insertAction.executeAndReturnKey(params).intValue();
  }

  public List<ReservationInfoDetail> selectByEmail(String email) {
    Map<String, String> params = new HashMap<>();
    params.put("email", email);

    return jdbc.query(SELECT_BY_EMAIL, params, detailRowMapper);
  }

  public ReservationInfo selectById(int reservationInfoId) {
    Map<String, Object> params = new HashMap<>();
    params.put("reservationInfoId", reservationInfoId);
    return jdbc.queryForObject(SELECT_BY_ID, params, resultRowMapper);
  }

  public int updateCancelYnById(int reservationInfoId, boolean cancelFlag) {
    Map<String, Object> params = new HashMap<>();
    
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = formatter.format(date);
    
    params.put("reservationInfoId", reservationInfoId);
    params.put("cancelFlag", cancelFlag);
    params.put("modifyDate", formattedDate);
    
    return jdbc.update(UPDATE_CANCEL_FLAG_BY_ID, params);
  }

}