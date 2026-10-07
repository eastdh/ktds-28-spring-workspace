package com.ktdsuniversity.edu.commons.exceptions;

import java.util.HashMap;
import java.util.Map;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;

public class Messages {

  private static Map<ExceptionType, Map<ArticleCodes, String>> messages;

  static {
    messages = new HashMap<>();
    messages.put(ExceptionType.ARTICLES, new HashMap<>());
    messages.get(ExceptionType.ARTICLES).put(ArticleCodes.NOT_EXISTS, "존재하지 않는 게시글입니다.");
    messages.get(ExceptionType.ARTICLES).put(ArticleCodes.NOT_AUTHORIZED, "삭제할 수 없는 게시글입니다.");

    messages.put(ExceptionType.FILES, new HashMap<>());
    messages.get(ExceptionType.FILES).put(ArticleCodes.SYSTEM_ERROR, "파일 세트를 만들 수 없습니다.");
    messages.get(ExceptionType.FILES).put(ArticleCodes.BAD_REQUEST, "잘못된 요청입니다.");

    messages.put(ExceptionType.MEMBERS, new HashMap<>());
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.USED, "이미 사용중입니다.");
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.SYSTEM_ERROR,
        "회원가입을 할 수 없습니다. 잠시 후 다시 시도해주세요.");
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.NOT_MATCHED_IDENTIFY,
        "이메일 또는 비밀번호가 일치하지 않습니다.");
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.FAILURE_LOGIN,
        "로그인을 실패했습니다. 잠시 후 다시 시도해주세요.");
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.FAILURE_EXIT,
        "회원 탈퇴를 실패했습니다. 잠시 후 다시 시도해주세요.");
    messages.get(ExceptionType.MEMBERS).put(ArticleCodes.BLOCKED_LOGIN,
        "로그인 실패 횟수가 누적되어 계정이 차단되었습니다. 잠시 후 다시 시도해주세요.");

    messages.put(ExceptionType.REPLIES, new HashMap<>());
    messages.get(ExceptionType.REPLIES).put(ArticleCodes.SYSTEM_ERROR, "댓글 생성을 실패했습니다.");
    messages.get(ExceptionType.REPLIES).put(ArticleCodes.NOT_EXISTS, "존재하지 않는 댓글입니다.");
    messages.get(ExceptionType.REPLIES).put(ArticleCodes.NOT_AUTHORIZED, "삭제할 수 없는 댓글입니다.");
  }

  public static String getMessage(ExceptionType exceptionType, ArticleCodes code) {
    return messages.get(exceptionType).getOrDefault(code, "요청을 처리할 수 없습니다. 잠시 후 다시 시도해주세요.");
  }

}
