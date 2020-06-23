package com.naver.reservation.dao;

import static com.naver.reservation.dao.ProductImageDaoSqls.SELECT_BY_PRODUCT_ID;

import com.naver.reservation.dto.api.ProductImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductImageDao {
  
  private NamedParameterJdbcTemplate jdbc;
  private RowMapper<ProductImage> rowMapper = BeanPropertyRowMapper.newInstance(ProductImage.class);

  public ProductImageDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
  }

  public List<ProductImage> selectByProductId(Integer productID) {
    Map<String, Integer> params = new HashMap<>();
    params.put("productID", productID);
    return jdbc.query(SELECT_BY_PRODUCT_ID, params, rowMapper);
  }

}

