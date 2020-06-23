package com.naver.reservation.dao;

import static com.naver.reservation.dao.ProductDaoSqls.SELECT_ALL;
import static com.naver.reservation.dao.ProductDaoSqls.SELECT_BY_CATEGORY_ID;
import static com.naver.reservation.dao.ProductDaoSqls.SELECT_COUNT;
import static com.naver.reservation.dao.ProductDaoSqls.SELECT_COUNT_BY_CATEGORY_ID;

import com.naver.reservation.dto.api.Product;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);

  public ProductDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<Product> selectAll(Integer start, Integer limit) {
    Map<String, Integer> params = new HashMap<>();
    params.put("start", start);
    params.put("limit", limit);
    return jdbc.query(SELECT_ALL, params, rowMapper);
  }

  public List<Product> selectByCategoryId(Integer categoryId, Integer start, Integer limit) {
    Map<String, Integer> params = new HashMap<>();
    params.put("categoryId", categoryId);
    params.put("start", start);
    params.put("limit", limit);
    return jdbc.query(SELECT_BY_CATEGORY_ID, params, rowMapper);
  }

  public int selectCount() { 
    return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
  }

  public int selectCountByCategory(Integer categoryId) { 
    Map<String, Integer> params = Collections.singletonMap("categoryId", categoryId);
    return jdbc.queryForObject(SELECT_COUNT_BY_CATEGORY_ID, params, Integer.class);
  }

}
