package com.naver.reservation.service.impl;

import com.naver.reservation.dao.ProductDao;
import com.naver.reservation.dto.api.Product;
import com.naver.reservation.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductDao productsdao;

  @Autowired
  public ProductServiceImpl(ProductDao productsdao) {
    this.productsdao = productsdao;
  }

  @Override
  public List<Product> getProducts(Integer start) {
    return productsdao.selectAll(start, ProductService.LIMIT);
  }

  @Override
  public List<Product> getProductsByCategoryId(Integer categoryId, Integer start) {

    if (categoryId == 0) {
      return productsdao.selectAll(start, ProductService.LIMIT);
    } else {
      return productsdao.selectByCategoryId(categoryId, start, ProductService.LIMIT);
    }

  }

  @Override
  public int getCount() {
    return productsdao.selectCount();
  }

  @Override
  public int getCountByCategory(Integer categoryId) {
    if (categoryId == 0) {
      return productsdao.selectCount();
    } else {
      return productsdao.selectCountByCategory(categoryId);
    }
  }

}
