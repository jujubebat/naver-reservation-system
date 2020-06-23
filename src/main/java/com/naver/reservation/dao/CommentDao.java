package com.naver.reservation.dao;

import static com.naver.reservation.dao.CommentDaoSqls.SELECT_BY_PRODUCT_ID;
import static com.naver.reservation.dao.CommentDaoSqls.SELECT_SCORE_AVG;

import com.naver.reservation.dto.api.CommentDetail;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<CommentDetail> rowMapper = BeanPropertyRowMapper.newInstance(CommentDetail.class);

  public CommentDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<CommentDetail> selectByProductId(Integer productId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("productId", productId);
    return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
  }

  public double selectAverageScoreByProductId(Integer productId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("productId", productId);

    if (jdbc.queryForObject(SELECT_SCORE_AVG, params, double.class) == null) {
      return 0;
    } else {
      return jdbc.queryForObject(SELECT_SCORE_AVG, params, double.class);
    }
  }

}
