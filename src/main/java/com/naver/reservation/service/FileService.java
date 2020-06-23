package com.naver.reservation.service;

import com.naver.reservation.dao.FileDao;
import com.naver.reservation.dto.api.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

  private final FileDao fileInfoDao;
  private static final String rootPath = "c:/";

  @Autowired
  public FileService(FileDao fileInfoDao) {
    this.fileInfoDao = fileInfoDao;
  }

  public void downloadFile(HttpServletResponse response, Integer FileId) {   
    File file = fileInfoDao.selectByFileId(FileId);

    response.setHeader("Content-Disposition",
        "attachment; filename=\"" + file.getFileName() + "\";");
    response.setHeader("Content-Transfer-Encoding", "binary");
    response.setHeader("Content-Type", file.getContentType());
    response.setHeader("Pragma", "no-cache;");
    response.setHeader("Expires", "-1;");

    int fileSize = 0;
    
    try (FileInputStream fis = new FileInputStream(rootPath + file.getSaveFileName());
        
        OutputStream out = response.getOutputStream();) {
      int readCount = 0;
      byte[] buffer = new byte[10240];
      while ((readCount = fis.read(buffer)) != -1) {
        out.write(buffer, 0, readCount);
        fileSize++;
      }
    } catch (Exception ex) {
      throw new RuntimeException("file Read Error");
    }

    response.setHeader("Content-Length", Integer.toString(fileSize));
    /* [질문]
     * 위와 같이 파일 사이즈를 헤더에 넣어줬습니다. 그런데 헤더값에서 확인이 되지 않습니다.. 혼자 해결하다가 안돼서 질문 드립니다.
     * */
  }

}


