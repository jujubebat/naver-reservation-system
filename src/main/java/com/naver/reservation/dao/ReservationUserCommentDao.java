package com.naver.reservation.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationUserCommentDao {

  private SimpleJdbcInsert insertAction;

  public ReservationUserCommentDao(DataSource dataSource) {
    insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment")
        .usingGeneratedKeyColumns("id");
  }

  public int insertReservationUserComment(int productId, int reservationInfoId, int score,
      String comment) {
    Map<String, Object> params = new HashMap<>();

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = formatter.format(date);

    params.put("product_id", productId);
    params.put("reservation_info_id", reservationInfoId);
    params.put("score", score);
    params.put("comment", comment);
    params.put("create_date", formattedDate);
    params.put("modify_date", formattedDate);


    return insertAction.executeAndReturnKey(params).intValue(); // reservation user comment id 반환.
  }

}
