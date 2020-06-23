package com.naver.reservation.service;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.naver.reservation.dao.FileDao;
import com.naver.reservation.dao.ReservationInfoPriceDao;
import com.naver.reservation.dao.ReservationUserCommentDao;
import com.naver.reservation.dao.ReservationUserCommentImageDao;
import com.naver.reservation.dto.api.CommentImage;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.CommentInfo;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;

@Service
public class ReservationUserCommentService {

  private final ReservationUserCommentDao reservationUserCommentDao;
  private final FileDao fileInfoDao;
  private final ReservationUserCommentImageDao reservationUserCommentImageDao;

  @Autowired
  public ReservationUserCommentService(ReservationUserCommentDao reservationUserCommentDao,
      FileDao fileInfoDao, ReservationUserCommentImageDao reservationUserCommentImageDao) {
    this.reservationUserCommentDao = reservationUserCommentDao;
    this.fileInfoDao = fileInfoDao;
    this.reservationUserCommentImageDao = reservationUserCommentImageDao;
  }

  @Transactional
  public CommentInfo addCommentAndImage(MultipartFile file, int reservationInfoId, String comment,
      int productId, int score) {
    
    String saveRootPath = "c:/";
    String savePath = "img_comment/";

    int reservationUserCommentId = reservationUserCommentDao.insertReservationUserComment(productId,
        reservationInfoId, score, comment);
    int reservationUserCommentImageId = 0;

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss_");
    String saveDate = formatter.format(date);

    Optional<Integer> optReservationUserCommentId = Optional.ofNullable(reservationUserCommentId);

    if (!optReservationUserCommentId.isPresent()) {
      return null;
    }

    if (file != null) { // 업로드된 파일이 존재하면, 파일 업로드 로직 수행
      String fileName =
          saveDate + "commentId_" + reservationUserCommentId + "_" + file.getOriginalFilename();
      
      try (FileOutputStream fos = new FileOutputStream(saveRootPath + savePath + fileName);
          InputStream is = file.getInputStream();) {
        int readCount = 0;
        byte[] buffer = new byte[1024];
        while ((readCount = is.read(buffer)) != -1) {
          fos.write(buffer, 0, readCount);
        }
        
        int fileId = fileInfoDao.insertFileInfo(fileName, file.getContentType(), savePath);
        reservationUserCommentImageId = reservationUserCommentImageDao
            .insertReservationUserCommentImage(reservationInfoId, reservationUserCommentId, fileId);
      } catch (Exception ex) {
        throw new RuntimeException("file Save Error");
      }

    }
    
    CommentImage commentImage = null;
    
    if(reservationUserCommentImageId != 0) {
      commentImage = reservationUserCommentImageDao
          .selectReservationUserCommentImageById(reservationUserCommentImageId);
    }
  
    CommentInfo commentInfo = new CommentInfo();
    commentInfo.setCommentId(reservationUserCommentId);
    commentInfo.setProductId(productId);
    commentInfo.setReservationInfoId(reservationInfoId);
    commentInfo.setScore(score);
    commentInfo.setComment(comment);
    commentInfo.setCreateDate(saveDate);
    commentInfo.setModifyDate(saveDate);
    if(commentInfo != null)
      commentInfo.setCommentImage(commentImage);

    return commentInfo;
  }

}
