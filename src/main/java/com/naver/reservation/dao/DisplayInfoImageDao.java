package com.naver.reservation.dao;

import static com.naver.reservation.dao.DisplayInfoImageDaoSqls.SELECT_BY_ID;

import com.naver.reservation.dto.api.DisplayInfoImage;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DisplayInfoImageDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<DisplayInfoImage> rowMapper = 
      BeanPropertyRowMapper.newInstance(DisplayInfoImage.class);

  public DisplayInfoImageDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }
  
  public DisplayInfoImage selectByDisplayInfoId(Integer displayInfoId) {   
    Map<String, Integer> params = new HashMap<>();
    params.put("displayInfoId", displayInfoId);
    return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
  }
  
}
