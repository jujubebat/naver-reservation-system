package com.naver.reservation.controller;

import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.CommentInfo;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;
import com.naver.reservation.dto.api.returnObject.UserReservationList;
import com.naver.reservation.service.ReservationInfoService;
import com.naver.reservation.service.ReservationUserCommentService;
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
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/reservations")
public class ReservationApiController {

  private final ReservationInfoService reservationInfoService;
  private final ReservationUserCommentService reservationUserCommentService;
  
  @Autowired
  public ReservationApiController(ReservationInfoService reservationInfoService, 
      ReservationUserCommentService reservationUserCommentService) {
    this.reservationInfoService = reservationInfoService;
    this.reservationUserCommentService = reservationUserCommentService; 
  }
  
  @PostMapping
  public ReservationInfo postReservations(@RequestBody ReservationInfoParam reservationParam) {
    return reservationInfoService.addReservationInfo(reservationParam);
  }

  @GetMapping
  public UserReservationList getReservations(
      @RequestParam(name = "reservationEmail") String reservationEmail, HttpSession session) {
    UserReservationList userReservationList = new UserReservationList(
        reservationInfoService.getReservationInfoDetailListByEmail(reservationEmail));

    if (userReservationList.getSize() > 0) { // 검색된 결과가 하나 이상이면,
      session.setAttribute("reservationEmail", reservationEmail); // 세션에 이메일을 저장한다.
    }

    return userReservationList;
  }


  @PutMapping(path = "/{reservationId}")
  public ReservationInfo cancelReservation(
      @PathVariable(name = "reservationId") int reservationId) {
    return reservationInfoService.cancelReservation(reservationId);
  }

  /* [질문]
   * 아래 함수는 구글 자바 컨밴션에 따라 코드를 정렬했는데 제가 볼때는 코드가 뭉쳐보이고 잘 안 읽히는 느낌이 듭니다.
   * 더 잘 보이도록 수정하는 것이 나을까요? 앞서 질문에서 나름의 규칙을 적용하여(공백관련) 가독성을 유지하려고 하는데
   * 아래 같은 경우를 보면 고민이 됩니다. 실무에서는 어떤 규칙을 가지고 가독성을 유지하는지 또 어느정도 융통성을 가지고 
   * 개발자 나름대로 작성하기도 하는지 궁금합니다.
   * */
  @PostMapping(path = "/{reservationInfoId}/comments")
  public CommentInfo postReservationComment(
      @RequestParam(name = "file", required = false) MultipartFile file,
      @PathVariable(name = "reservationInfoId") int reservationInfoId, @RequestParam String comment,
      @RequestParam int productId, @RequestParam int score) {
    return reservationUserCommentService.addCommentAndImage(file, reservationInfoId, comment,
        productId, score);
  }
  
}
