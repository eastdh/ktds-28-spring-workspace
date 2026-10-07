package com.ktdsuniversity.edu.members.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ktdsuniversity.edu.commons.crypto.AES;
import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;
import com.ktdsuniversity.edu.commons.exceptions.ArticleException;
import com.ktdsuniversity.edu.commons.exceptions.enums.ArticleCodes;
import com.ktdsuniversity.edu.commons.exceptions.enums.ExceptionType;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MembersServiceImpl implements MembersService {

  @Value("${app.encrypt.aes.key}")
  private String aesKey;
  private final MembersDao membersDao;
  private static final Logger logger = LoggerFactory.getLogger(MembersServiceImpl.class);

  @Transactional
  @Override
  public MembersVO createNewMember(RegistMembersVO registMembersVO) {
    // 1. 이메일 중복 검사
    if (this.membersDao.selectEmailCount(registMembersVO.getEmail()) > 0) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.USED);
    }
    // 2. SHA에서 salt 발급 받아 password 암호화
    String salt = SHA.generateSalt();
    String encryptedPassword = SHA.getEncrypt(registMembersVO.getPassword(), salt);
    registMembersVO.setPassword(encryptedPassword);
    registMembersVO.setSalt(salt);

    // 3. 닉네임 중복 검사
    if (this.membersDao
        .selectNicknameCount(AES.encode(this.aesKey, registMembersVO.getNickname())) > 0) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.USED);
    }

    // 3.1. 이름, 닉네임 AES 암호화
    String encryptedName = AES.encode(this.aesKey, registMembersVO.getName());
    String encryptedNickname = AES.encode(this.aesKey, registMembersVO.getNickname());

    registMembersVO.setName(encryptedName);
    registMembersVO.setNickname(encryptedNickname);

    // 4. INSERT 요청
    int insertedRows = this.membersDao.insertNewMember(registMembersVO);
    if (insertedRows == 0) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.SYSTEM_ERROR);
    }

    // 5. 회원 가입 결과 반환
    MembersVO newMember = this.membersDao.selectMemberByEmail(registMembersVO.getEmail());
    if (newMember == null) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.SYSTEM_ERROR);
    }
    newMember.setName(AES.decode(this.aesKey, newMember.getName()));
    newMember.setNickname(AES.decode(this.aesKey, newMember.getNickname()));
    return newMember;
  }

  @Override
  public MembersVO readMember(LoginMemberVO loginMemberVO) {
    // 이메일로 회원 정보 조회
    MembersVO membersVO = this.membersDao.selectMemberByEmail(loginMemberVO.getEmail());

    // 회원 정보가 존재하는가?
    if (membersVO == null || membersVO.getDelYn().equals("Y")) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.NOT_MATCHED_IDENTIFY);
    }

    // 차단된 회원인가?
    if (membersVO.getLoginBlockYn().equals("Y")) {
      // 로그인 차단 계정

      // 차단된 후 1시간이 지났는가?
      LocalDateTime now = LocalDateTime.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      LocalDateTime loginBlockDate = LocalDateTime.parse(membersVO.getLoginBlockDate(), formatter);
      loginBlockDate = loginBlockDate.plusHours(1); // 차단된 후 1시간이 지난 시각

      if (now.isEqual(loginBlockDate) || now.isAfter(loginBlockDate)) {
        // 차단 후 1시간 경과
        // 로그인 실패 횟수 0으로 초기화 & 차단 여부 N으로 수정
        int updatedRows = this.membersDao.updateResetBlock(loginMemberVO.getEmail());
        logger.info("{}건이 Block 해제되었음", updatedRows);
      } else {
        throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.NOT_MATCHED_IDENTIFY);
      }
    }
    // 로그인 가능 계정
    // 사용자 salt 필요
    // 로그인 요청 비밀번호 필요
    // 암호화
    String rawPassword = loginMemberVO.getPassword();
    String storedSalt = membersVO.getSalt();
    String encryptedPassword = SHA.getEncrypt(rawPassword, storedSalt);

    // salt 로 암호화된 입력 비밀번호와 DB에 저장된 비밀번호가 일치하는가?
    if (encryptedPassword.equals(membersVO.getPassword())) {
      // 비밀번호 일치함
      int updatedRows = this.membersDao.updateLoginStatus(membersVO.getEmail());
      if (updatedRows == 0) {
        // 이 부분은 운영이 종료될 때까지도 실행되지 않을 수도 있다!
        throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.FAILURE_LOGIN);
      }
      MembersVO loggedMember = this.membersDao.selectMemberByEmail(membersVO.getEmail());
      loggedMember.setName(AES.decode(this.aesKey, loggedMember.getName()));
      loggedMember.setNickname(AES.decode(this.aesKey, loggedMember.getNickname()));
      return loggedMember;
    }

    // 비밀번호 불일치
    int updatedRows = this.membersDao.updateLoginFailed(membersVO.getEmail());
    logger.info("{} 로그인 실패!", membersVO.getEmail());
    logger.info("{}건이 로그인 실패 처리 됨", updatedRows);

    // 로그인 실패 횟수 확인하면서 계정 차단 시도
    int blockUpdatedRows = this.membersDao.updateBlock(membersVO.getEmail());
    if (blockUpdatedRows > 0) {
      // 계정이 차단 됨
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.BLOCKED_LOGIN);
    } else {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.NOT_MATCHED_IDENTIFY);
    }

  }

  @Transactional
  @Override
  public String updateLogoutStatus(String email) {
    int updatedRows = this.membersDao.updateLogoutStatus(email);
    if (updatedRows > 0) {
      return email;
    }
    return null;
  }

  @Transactional
  @Override
  public String deleteMember(String email, String password) {

    MembersVO membersVO = this.membersDao.selectMemberByEmail(email);

    String encryptedPassword = SHA.getEncrypt(password, membersVO.getSalt());

    if (!encryptedPassword.equals(membersVO.getPassword())) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.NOT_MATCHED_IDENTIFY);
    }

    int deletedRows = this.membersDao.deleteMember(email);
    if (deletedRows == 0) {
      throw new ArticleException(ExceptionType.MEMBERS, ArticleCodes.FAILURE_EXIT);
    }

    return updateLogoutStatus(email);
  }

}
