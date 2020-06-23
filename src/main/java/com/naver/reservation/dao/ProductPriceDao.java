package com.naver.reservation.dao;

import static com.naver.reservation.dao.ProductPriceDaoSqls.SELECT_BY_PRODUCT_ID;
import static com.naver.reservation.dao.ProductPriceDaoSqls.SELECT_PRICE_BY_PRODUCT_ID;

import com.naver.reservation.dto.api.ProductPrice;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductPriceDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<ProductPrice> rowMapper = BeanPropertyRowMapper.newInstance(ProductPrice.class);

  public ProductPriceDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<ProductPrice> selectByProductId(Integer productId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("productId", productId);
    return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
  }

  public int selectPriceByProductId(Integer productId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("productId", productId);
    return jdbc.queryForObject(SELECT_PRICE_BY_PRODUCT_ID, params, Integer.class);
  }

}
