package com.naver.reservation.service;

import com.naver.reservation.dao.CategoryDao;
import com.naver.reservation.dto.api.Category;
import com.naver.reservation.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private final CategoryDao categoriesDao;

  @Autowired
  public CategoryService(CategoryDao categoriesDao) {
    this.categoriesDao = categoriesDao;
  }

  /*
   * [메모] : 데이터 조회시에는 @Transactional을 사용한 격리 수준을 따로 하지 않아도 된다고 조언받았습니다.
   *         mysql에 경우 격리수준(isolation) 기본 설정은 "REPEATABLE READ"로 되어 있으며 동일 
   *         트랜잭션 내의 데이터의 일괄성을 보장한다고 알려주셔서 데이터 조회 서비스 로직에는 트랜잭션 격리설정을 
   *         따로하지 않았습니다. (이전에 두 명의 리뷰어께서 서로 다른 의견을 주셔서 메모 남깁니다.)
   * */
  
  public List<Category> getCategories() {
    return categoriesDao.selectAll();
  }

}
