package com.ktdsuniversity.edu.replies.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RepliesServiceImpl implements RepliesService {

  private RepliesDao repliesDao;
  private MultipartHandler multipartHandler;
  private static final Logger logger = LoggerFactory.getLogger(RepliesServiceImpl.class);

  @Override
  public ReplyListVO readAllReplies(String articleId) {
    ReplyListVO list = new ReplyListVO();

    list.setReplyCount(this.repliesDao.selectRepliesCountByArticleId(articleId));
    list.setReplyList(this.repliesDao.selectAllRepliesByArticleId(articleId));

    return list;
  }

  @Override
  public RepliesVO createNewReply(String articleId, RegistReplyVO registReplyVO) {

    registReplyVO.setArticleId(articleId);

    String fileSetId =
        this.multipartHandler.storeFiles(registReplyVO.getFile(), registReplyVO.getEmail());
    registReplyVO.setFileSetId(fileSetId);

    int insertedRows = this.repliesDao.insertNewReply(registReplyVO);
    logger.info("{}개의 댓글 Row가 생성되었습니다.", insertedRows);

    if (insertedRows > 0) {
      return this.repliesDao.selectReplyByReplyId(registReplyVO.getId());
    }
    throw new IllegalArgumentException("입력 값이 유효하지 않습니다.");
  }

  @Override
  public RepliesVO updateReply(String articleId, String replyId, ModifyReplyVO modifyReplyVO) {

    RepliesVO originalReply = this.repliesDao.selectReplyByReplyId(replyId);

    if (originalReply == null) {
      throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }

    String fileSetId = this.multipartHandler.storeFiles(modifyReplyVO.getFile(),
        modifyReplyVO.getEmail(), originalReply.getFileSetId());
    modifyReplyVO.setFileSetId(fileSetId);

    int updatedRows =
        this.repliesDao.updateReplyByArticleIdAndReplyId(articleId, replyId, modifyReplyVO);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }

    return this.repliesDao.selectReplyByReplyId(replyId);
  }

  @Override
  public String deleteReply(String articleId, String replyId) {
    RepliesVO originalReply = this.repliesDao.selectReplyByReplyId(replyId);

    if (originalReply == null) {
      throw new IllegalArgumentException("존재하지 않는 댓글입니다.");
    }

    int deletedRows = this.repliesDao.deleteReply(articleId, replyId);

    if (deletedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글 또는 댓글입니다.");
    }

    int deleltedFilesCount = this.multipartHandler.deleteFiles(originalReply.getFileSetId());
    logger.info("{}개 파일이 삭제되었습니다.", deleltedFilesCount);

    return replyId;
  }

  @Override
  public long recommendOneReply(String articleId, String replyId) {

    int updatedRows = this.repliesDao.updateIncreaseRecommendCount(articleId, replyId);

    if (updatedRows == 0) {
      throw new IllegalArgumentException("존재하지 않는 게시글 또는 댓글입니다.");
    }

    return this.repliesDao.getRecommendCount(articleId, replyId);
  }

}
