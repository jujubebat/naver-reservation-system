package com.naver.reservation.dao;

import static com.naver.reservation.dao.ReservationUserCommentImageDaoSqls.SELECT_BY_ID;

import com.naver.reservation.dto.api.CommentImage;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationUserCommentImageDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;
  private RowMapper<CommentImage> rowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);

  public ReservationUserCommentImageDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    insertAction = new SimpleJdbcInsert(dataSource).withTableName("reservation_user_comment_image")
        .usingGeneratedKeyColumns("id");
  }
  
  public int insertReservationUserCommentImage(int reservationInfoId, int reservationUserCommentId, int fileId) {
    Map<String, Object> params = new HashMap<>();
    
    params.put("reservation_info_id", reservationInfoId);
    params.put("reservation_user_comment_id", reservationUserCommentId);
    params.put("file_id", fileId);

    return insertAction.executeAndReturnKey(params).intValue(); //reservation user comment id 반환.
  }
  
  
  public CommentImage selectReservationUserCommentImageById(int id) {
    Map<String, Integer> params = new HashMap<>();
    params.put("id", id);
    return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
  }
  
}