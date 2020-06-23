package com.naver.reservation.controller;

import com.naver.reservation.dto.api.returnObject.CategoryList;
import com.naver.reservation.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/categories")
public class CategoriesApiController {
  
  private final CategoryService categoryService;

  @Autowired
  public CategoriesApiController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }
  
/*[질문]
 * 정말 사소한 질문일 수 있지만 궁금해서 질문 드립니다. 가독성을 위한 개행삽입에 관련된 질문 입니다.
 * 현재 아래와 같은 규칙으로 코드를 작성했습니다.
 * 
 * 클래스 : 클래스 내부 맨윗줄과 맨 아랫줄에 공백을 한칸씩준다.
 * 함수 : 함수 내부 맨윗줄과 맨 아랫줄에 공백을 넣지 않는다.
 * 
 * 개발자의 스타일 문제일수도 있는 것 같은데 현업에서는 어떤식으로 하는지 궁금합니다!
 * */
  
  @GetMapping(path = "")
  public CategoryList getCategories() {
    return new CategoryList(categoryService.getCategories());
  }
  
}
