package com.naver.reservation.service;

import com.naver.reservation.dao.CommentImageDao;
import com.naver.reservation.dto.api.CommentImage;
import com.naver.reservation.service.CommentImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentImageService {

  private final CommentImageDao commentImageDao;

  @Autowired
  public CommentImageService(CommentImageDao commentImageDao) {
    this.commentImageDao = commentImageDao;
  }

  public List<CommentImage> getCommentImageByCommentId(Integer commentId) {
    return commentImageDao.selectByCommentId(commentId);
  }

}
