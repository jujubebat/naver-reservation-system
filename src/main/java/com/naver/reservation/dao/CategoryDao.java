package com.naver.reservation.dao;

import static com.naver.reservation.dao.CategoryDaoSqls.SELECT_ALL; // static import는 최상위에 한 칸 띄어서 배치한다.
                                                                   
import com.naver.reservation.dto.api.Category;
import java.util.Collections;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);

  public CategoryDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Category> selectAll() {
    return jdbc.query(SELECT_ALL, Collections.emptyMap(), rowMapper);
  }
  
}
