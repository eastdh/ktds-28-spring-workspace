package com.ktdsuniversity.edu.files.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.files.vo.request.RequestFileSetVO;
import com.ktdsuniversity.edu.files.vo.request.RequestFileVO;

@Mapper
public interface FilesDao {

  int insertNewFileSet(RequestFileSetVO requestFileSetVO);

  int insertNewFile(RequestFileVO requestFileVO);

  int updateFileSet(String fileSetId);

  int deleteFilesByArticleId(String articleId);

  long selectFilesCountByArticleId(String articleId);

}
