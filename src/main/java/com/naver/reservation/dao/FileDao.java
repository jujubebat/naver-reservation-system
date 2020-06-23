package com.naver.reservation.dao;

import static com.naver.reservation.dao.FileDaoSqls.SELECT_BY_ID;

import com.naver.reservation.dto.api.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository 
public class FileDao {

  private NamedParameterJdbcTemplate jdbc;
  private SimpleJdbcInsert insertAction;
  private RowMapper<File> rowMapper = BeanPropertyRowMapper.newInstance(File.class);
  
  public FileDao(DataSource dataSource) {
    this.jdbc = new NamedParameterJdbcTemplate(dataSource);
    insertAction = new SimpleJdbcInsert(dataSource).withTableName("file_info")
        .usingGeneratedKeyColumns("id");
  }
  
  public int insertFileInfo(String fileName, String contentType, String savePath) {
    Map<String, Object> params = new HashMap<>();
 
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String formattedDate = formatter.format(date);
    
    params.put("file_name", fileName);
    params.put("save_file_name", savePath + fileName);
    params.put("content_type", contentType);
    params.put("delete_flag", false);
    params.put("create_date", formattedDate);
    params.put("modify_date", formattedDate);
    
    System.out.println(fileName);
    System.out.println(contentType);
    System.out.println(savePath);

    return insertAction.executeAndReturnKey(params).intValue(); //reservation user comment id 반환.
  }
  
  
  public File selectByFileId(Integer fileId) {
    Map<String, Integer> params = new HashMap<>();
    params.put("fileId", fileId);
    return jdbc.queryForObject(SELECT_BY_ID, params, rowMapper);
  }
  
}
