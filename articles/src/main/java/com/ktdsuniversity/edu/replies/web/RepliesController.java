package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/articles/{articleId}/replies")
@AllArgsConstructor
public class RepliesController {

  private RepliesService repliesService;

  // GET /replies/{게시글아이디}
  // 게시글에 등록된 댓글을 반환
  @GetMapping("/list")
  public ApiResponse<ReplyListVO> getReplies(@Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
      message = "잘못된 요청입니다.") @PathVariable String articleId) {
    ReplyListVO replyList = this.repliesService.readAllReplies(articleId);
    return ApiResponse.OK(replyList);
  }

  // POST /replies/{게시글아이디}
  // 게시글에 댓글 작성 (파일 첨부 가능)
  @PostMapping
  public ApiResponse<RepliesVO> makeNewReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Valid @ModelAttribute RegistReplyVO registReplyVO, BindingResult validationResult,
      @SessionAttribute("__LOGIN_USER__") MembersVO membersVO) {
    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }


    registReplyVO.setEmail(membersVO.getEmail());

    try {
      RepliesVO result = this.repliesService.createNewReply(articleId, registReplyVO);
      return ApiResponse.CREATED(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  // PUT /replies/{게시글아이디}/{댓글아이디}
  // 게시글에 등록된 댓글을 수정 (파일 첨부 가능)
  @PutMapping("/{replyId}")
  public ApiResponse<RepliesVO> updateReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId,
      @Valid @ModelAttribute ModifyReplyVO modifyReplyVO, BindingResult validationResult,
      @SessionAttribute("__LOGIN_USER__") MembersVO membersVO) {

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }


    modifyReplyVO.setEmail(membersVO.getEmail());


    try {
      RepliesVO result = this.repliesService.updateReply(articleId, replyId, modifyReplyVO);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  // DELETE /replies/{게시글아이디}/{댓글아이디}
  // 게시글에 등록된 댓글 하나를 삭제
  // 첨부된 파일 제거
  @DeleteMapping("/{replyId}")
  public ApiResponse<String> deleteReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId) {
    try {
      String result = this.repliesService.deleteReply(articleId, replyId);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  // PUT /replies/{게시글아이디}/recommend/{댓글아이디}
  // 게시글에 등록된 댓글 하나를 추천
  @PutMapping("/recommend/{replyId}")
  public ApiResponse<Long> recommendOneReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId) {
    try {
      long result = this.repliesService.recommendOneReply(articleId, replyId);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.ERROR(iae.getMessage());
    }
  }
}
