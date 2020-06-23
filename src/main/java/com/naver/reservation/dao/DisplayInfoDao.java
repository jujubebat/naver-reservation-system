package com.naver.reservation.dao;

import static com.naver.reservation.dao.DisplayInfoDaoSqls.SELECT_BY_DISPLAY_INFO_ID;

import com.naver.reservation.dto.api.DisplayInfo;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DisplayInfoDao {
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<DisplayInfo> rowMapper = BeanPropertyRowMapper.newInstance(DisplayInfo.class);

  public DisplayInfoDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public DisplayInfo selectByDisplayInfoId(Integer displayInfoId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("displayInfoId", displayInfoId);
    return jdbc.queryForObject(SELECT_BY_DISPLAY_INFO_ID, params, rowMapper);
  }

}
