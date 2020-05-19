package com.naver.reservation.service;

import com.naver.reservation.dto.api.Product;
import java.util.List;

public interface ProductService {
  public static final Integer LIMIT = 4;

  public List<Product> getProducts(Integer start);

  public List<Product> getProductsByCategoryId(Integer categoryId, Integer start);

  public int getCount();

  public int getCountByCategory(Integer categoryId);
}
