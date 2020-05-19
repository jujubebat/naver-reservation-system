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

  /**
   * start(순번), limit(데이터 개수)를 인자로 받아 Products를 select 한다.
   */
  public List<Product> selectAll(Integer start, Integer limit) {
    Map<String, Integer> params = new HashMap<>();
    params.put("start", start);
    params.put("limit", limit);
    return jdbc.query(SELECT_ALL, params, rowMapper);
  }

  /**
   * categoryId(카테고리 아이디), start(순번), limit(데이터 개수)를 인자로 받아 카테고리별로 Products를 select 한다.
   */
  public List<Product> selectByCategoryId(Integer categoryId, Integer start, Integer limit) {
    Map<String, Integer> params = new HashMap<>();
    params.put("categoryId", categoryId);
    params.put("start", start);
    params.put("limit", limit);
    return jdbc.query(SELECT_BY_CATEGORY_ID, params, rowMapper);
  }

  public int selectCount() { // 모든 product 개수
    return jdbc.queryForObject(SELECT_COUNT, Collections.emptyMap(), Integer.class);
  }

  public int selectCountByCategory(Integer categoryId) { // 카테고리별 product 개수
    Map<String, Integer> params = Collections.singletonMap("categoryId", categoryId);
    return jdbc.queryForObject(SELECT_COUNT_BY_CATEGORY_ID, params, Integer.class);
  }

}
