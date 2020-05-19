package com.naver.reservation.dao;

import static com.naver.reservation.dao.CommentImageDaoSqls.SELECT_BY_COMMENT_ID;

import com.naver.reservation.dto.api.CommentImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentImageDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<CommentImage> rowMapper = BeanPropertyRowMapper.newInstance(CommentImage.class);

  public CommentImageDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  /**
   * comment의 CommentImage 반환.
   */
  public List<CommentImage> selectByCommentId(Integer commentId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("commentId", commentId);
    return jdbc.query(SELECT_BY_COMMENT_ID, params, rowMapper);
  }
}
