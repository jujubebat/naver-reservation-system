package com.naver.reservation.service;

import com.naver.reservation.dao.CategoryDao;
import com.naver.reservation.dto.api.Category;
import com.naver.reservation.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryDao categoriesDao;

  @Autowired
  public CategoryService(CategoryDao categoriesDao) {
    this.categoriesDao = categoriesDao;
  }
  
  public List<Category> getCategories() {
    return categoriesDao.selectAll();
  }

}
