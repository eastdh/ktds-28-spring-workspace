package com.ktdsuniversity.edu.files.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.ktdsuniversity.edu.files.vo.request.RequestFileSetVO;
import com.ktdsuniversity.edu.files.vo.request.RequestFileVO;
import com.ktdsuniversity.edu.files.vo.response.FilesVO;

@Mapper
public interface FilesDao {

  int insertNewFileSet(RequestFileSetVO requestFileSetVO);

  int insertNewFile(RequestFileVO requestFileVO);

  int updateFileSet(String fileSetId);

  int deleteFilesByFileSetId(String fileSetId);

  List<FilesVO> selectFilesByFileSetId(String fileSetId);

  FilesVO selectAttachFile(@Param("fileSetId") String fileSetId, @Param("fileId") String fileId);

  int updateIncreaseDownloadCount(@Param("fileSetId") String fileSetId,
      @Param("fileId") String fileId);

}
