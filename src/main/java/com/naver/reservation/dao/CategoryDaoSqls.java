package com.naver.reservation.dao;

public class CategoryDaoSqls {
  
  /*
   * [질문] : orm 방식으로 db 접근을 더 쉽게 할 수 있는 기술이 있다고 들었는데, 실무에서도 이렇게 SQL문을 직접 사용해서 
   *         개발하는지 궁금합니다. 
   * 
   * */
  
  public static final String SELECT_ALL = 
        "SELECT category.id,\r\n"
      + "       category.NAME,\r\n" 
      + "       COUNT(*) AS count\r\n" 
      + "FROM   product\r\n" 
      + "       INNER JOIN category\r\n" 
      + "               ON product.category_id = category.id\r\n" 
      + "GROUP  BY category.id";
}
