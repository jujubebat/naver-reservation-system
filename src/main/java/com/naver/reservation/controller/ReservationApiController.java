package com.naver.reservation.controller;

import com.naver.reservation.dto.api.Comment;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.CategoryList;
import com.naver.reservation.dto.api.returnObject.DetailDisplayInfo;
import com.naver.reservation.dto.api.returnObject.ProductList;
import com.naver.reservation.dto.api.returnObject.PromotionList;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;
import com.naver.reservation.dto.api.returnObject.UserReservationList;
import com.naver.reservation.service.CategoryService;
import com.naver.reservation.service.CommentService;
import com.naver.reservation.service.DisplayInfoImageService;
import com.naver.reservation.service.DisplayInfoService;
import com.naver.reservation.service.ProductImageService;
import com.naver.reservation.service.ProductPriceService;
import com.naver.reservation.service.ProductService;
import com.naver.reservation.service.PromotionService;
import com.naver.reservation.service.ReservationInfoService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api")
public class ReservationApiController {

  private final ProductService productsService;
  private final CategoryService categoriesService;
  private final PromotionService promotionsService;
  private final DisplayInfoService displayInfoService;
  private final ProductImageService productImageService;
  private final DisplayInfoImageService displayInfoImageService;
  private final ProductPriceService productPriceService;
  private final CommentService commentService;
  private final ReservationInfoService reservationInfoService;
  
  /*
  [질문] : 현재 프로젝트에 구글 스타일 가이드를 적용해서 가독성을 유지하고 있습니다. (https://github.com/google/styleguide)
  ReservationApiController 클래스의 생성자와 모든 함수위에 구글 스타일 가이드가 주석을 달라고 표시하는데, 주석을 다는게 맞나요? 
     실무에서 주석을 다는 기준이 궁금합니다.
  */
  
  /*
   * [질문] : 코드 가독성을 위해 컨트롤러, 서비스, 레포지터리의 함수들의 이름을 나름대로 규칙을 정해서 붙였는데, 좀 고민을 많이 했습니다.
   *         (제가 작성한 나름대로의 함수명은 괜찮은거 같기도 하면서 간혹 좀 길다 싶은 이름이 존재해서 마음에 조금 걸립니다.)
   *         함수명 이름 짓기 관련해서 조언해주시면 감사하겠습니다. 실무에서는 어떤식으로 이름을 짓는지 궁금합니다.
   * */
  
  @Autowired
  public ReservationApiController(ProductService productsService, CategoryService categoriesService,
      PromotionService promotionsService, DisplayInfoService displayInfoService,
      ProductImageService productImageService, DisplayInfoImageService displayInfoImageService,
      ProductPriceService productPriceService, CommentService commentService,
      ReservationInfoService reservationInfoService) {

    this.productsService = productsService;
    this.categoriesService = categoriesService;
    this.promotionsService = promotionsService;
    this.displayInfoService = displayInfoService;
    this.productImageService = productImageService;
    this.displayInfoImageService = displayInfoImageService;
    this.productPriceService = productPriceService;
    this.commentService = commentService;
    this.reservationInfoService = reservationInfoService;
  }

  
  @GetMapping(path = "/products")
  public ProductList getProducts(@RequestParam(defaultValue = "0") int categoryId,
      @RequestParam(defaultValue = "0") int start) {

    return new ProductList(productsService.getCountByCategory(categoryId),
        productsService.getProductsByCategoryId(categoryId, start));
    // [질문] : 위와 같이 바로 return 해도 되는지 궁금합니다. 가독성 측면에서 어떤 방법이 좋을지 궁금해요.

  }

 
  @GetMapping(path = "/products/{displayInfoId}")
  public DetailDisplayInfo getDisplayInfo(@PathVariable(name = "displayInfoId") int displayInfoId) {

    DisplayInfo displayInfo = displayInfoService.getDisplayInfoById(displayInfoId);
    Optional<DisplayInfo> optDisplayInfo = Optional.ofNullable(displayInfo);

    if (!optDisplayInfo.isPresent()) {
      return null;
    }

    int productId = displayInfo.getProductId();

    List<ProductImage> productImages = productImageService.getProductImagesByProductId(productId);
    DisplayInfoImage displayInfoImage =
        displayInfoImageService.getDisplayInfoImageByDisplayInfoId(displayInfoId);
    List<ProductPrice> productPrices = productPriceService.getProductPricesByProductId(productId);
    double averageScore = commentService.getProductAverageScoreByProductId(productId);
    List<Comment> comments = commentService.getCommentsWithImagesByProductId(productId);

    return new DetailDisplayInfo(displayInfo, productImages, displayInfoImage, productPrices,
        averageScore, comments);

  }

  
  @GetMapping(path = "/categories")
  public CategoryList getCategories() {

    return new CategoryList(categoriesService.getCategories()); 
    

  }

 
  @GetMapping(path = "/promotions")
  public PromotionList getPromotions() {

    return new PromotionList(promotionsService.getPromotions());

  }

  
  @PostMapping(path = "/reservations")
  public ReservationInfo postReservations(@RequestBody ReservationInfoParam reservationParam) {

    return reservationInfoService.addReservationInfo(reservationParam);

  }

  
  /*
   * [질문] : 이전 리뷰어님께서 컨트롤러단은 로직을 최소화 하는 것이 좋다고 조언해주셔서 컨트롤러단 로직을 최소화 했습니다.
   *         아래 getReservations 컨트롤러는 세션관련 로직이 들어있는데, 세션이나 쿠키 관련 로직이 서비스단에 
   *         있어도 괜찮은건지 궁금합니다.
   * */
 
  @GetMapping(path = "/reservations")
  public UserReservationList getReservations(
      @RequestParam(name = "reservationEmail") String reservationEmail, HttpSession session) {

    UserReservationList userReservationList = new UserReservationList(
        reservationInfoService.getReservationInfoDetailListByEmail(reservationEmail));

    if (userReservationList.getSize() > 0) { // 검색된 결과가 하나 이상이면,
      session.setAttribute("reservationEmail", reservationEmail); // 세션에 이메일을 저장한다.         
    }

    return userReservationList;

  }

  
  @PutMapping("/reservations/{reservationId}")
  public ReservationInfo cancelReservation(
      @PathVariable(name = "reservationId") int reservationId) {

    return reservationInfoService.cancelReservation(reservationId);

  }

  
  @GetMapping("/randomDate")
  public Map<String, String> getRandomDate() {

    Date date = new Date();
    int random = (int) (Math.random() * 5);
    date.setDate(date.getDate() + random);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = formatter.format(date);

    Map<String, String> randomdate = new HashMap<>();
    randomdate.put("reservationDate", formattedDate);

    return randomdate;

  }


}