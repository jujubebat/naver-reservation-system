package com.naver.reservation.dao;

import static com.naver.reservation.dao.PromotionDaoSqls.SELECT_ALL;

import com.naver.reservation.dto.api.Promotion;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PromotionDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Promotion> rowMapper = BeanPropertyRowMapper.newInstance(Promotion.class);

  public PromotionDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Promotion> selectAll() {
    return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
  }
}
