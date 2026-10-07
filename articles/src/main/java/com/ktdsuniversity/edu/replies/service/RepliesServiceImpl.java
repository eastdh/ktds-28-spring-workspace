package com.ktdsuniversity.edu.replies.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.files.components.MultipartHandler;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.dao.RepliesDao;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

  @Transactional
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
    throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.SYSTEM_ERROR);
  }

  @Transactional
  @Override
  public RepliesVO updateReply(String articleId, String replyId, ModifyReplyVO modifyReplyVO) {

    RepliesVO originalReply = this.repliesDao.selectReplyByReplyId(replyId);

    if (originalReply == null) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
    }

    String fileSetId = this.multipartHandler.storeFiles(modifyReplyVO.getFile(),
        modifyReplyVO.getEmail(), originalReply.getFileSetId());
    modifyReplyVO.setFileSetId(fileSetId);

    int updatedRows =
        this.repliesDao.updateReplyByArticleIdAndReplyId(articleId, replyId, modifyReplyVO);

    if (updatedRows == 0) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
    }

    return this.repliesDao.selectReplyByReplyId(replyId);
  }

  @Transactional
  @Override
  public String deleteReply(String articleId, String replyId) {
    RepliesVO originalReply = this.repliesDao.selectReplyByReplyId(replyId);

    if (originalReply == null) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
    }

    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = requestAttributes.getRequest();
    HttpSession session = request.getSession();

    MembersVO loggedMember = (MembersVO) session.getAttribute("__LOGIN_USER__");

    if (!loggedMember.getEmail().equals(originalReply.getEmail())) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_AUTHORIZED);
    }

    int deletedRows = this.repliesDao.deleteReply(articleId, replyId);

    if (deletedRows == 0) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
    }

    int deleltedFilesCount = this.multipartHandler.deleteFiles(originalReply.getFileSetId());
    logger.info("{}개 파일이 삭제되었습니다.", deleltedFilesCount);

    return replyId;
  }

  @Transactional
  @Override
  public long recommendOneReply(String articleId, String replyId) {

    int updatedRows = this.repliesDao.updateIncreaseRecommendCount(articleId, replyId);

    if (updatedRows == 0) {
      throw new ArticleException(ExceptionType.REPLIES, ArticleCodes.NOT_EXISTS);
    }

    return this.repliesDao.getRecommendCount(articleId, replyId);
  }

}
