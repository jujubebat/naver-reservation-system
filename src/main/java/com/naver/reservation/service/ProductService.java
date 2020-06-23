package com.naver.reservation.service;

import com.naver.reservation.dao.ProductDao;
import com.naver.reservation.dto.api.Product;
import com.naver.reservation.service.ProductService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  public static final Integer LIMIT = 4;
  private final ProductDao productsdao;

  @Autowired
  public ProductService(ProductDao productsdao) {
    this.productsdao = productsdao;
  }

  public List<Product> getProducts(Integer start) {
    return productsdao.selectAll(start, ProductService.LIMIT);
  }

  public List<Product> getProductsByCategoryId(Integer categoryId, Integer start) {
    if (categoryId == 0) {
      return productsdao.selectAll(start, ProductService.LIMIT);
    } else {
      return productsdao.selectByCategoryId(categoryId, start, ProductService.LIMIT);
    }
  }

  public int getCount() {
    return productsdao.selectCount();
  }

  public int getCountByCategory(Integer categoryId) {
    if (categoryId == 0) {
      return productsdao.selectCount();
    } else {
      return productsdao.selectCountByCategory(categoryId);
    }
  }

}
