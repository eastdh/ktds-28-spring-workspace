package com.ktdsuniversity.edu.members.service;

import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.commons.crypto.AES;
import com.ktdsuniversity.edu.commons.crypto.encrypt.hash.SHA;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class MembersServiceImpl implements MembersService {

  private static final String AES_SECRET_KEY = "abcde12345abcde12345abcde12345zz"; // 32 글자

  private MembersDao membersDao;

  @Override
  public MembersVO createNewMember(RegistMembersVO registMembersVO) {
    // 1. 이메일 중복 검사
    if (this.membersDao.selectEmailCount(registMembersVO.getEmail()) > 0) {
      throw new IllegalArgumentException("이미 가입된 이메일입니다.");
    }
    // 2. SHA에서 salt 발급 받아 password 암호화
    String salt = SHA.generateSalt();
    String encryptedPassword = SHA.getEncrypt(registMembersVO.getPassword(), salt);
    registMembersVO.setPassword(encryptedPassword);
    registMembersVO.setSalt(salt);

    // 3. 닉네임 중복 검사
    if (this.membersDao.selectNicknameCount(registMembersVO.getNickname()) > 0) {
      throw new IllegalArgumentException("이미 존재하는 닉네임입니다.");
    }

    // 3.1. 이름, 닉네임 AES 암호화
    String encryptedName = AES.encode(AES_SECRET_KEY, registMembersVO.getName());
    String encryptedNickname = AES.encode(AES_SECRET_KEY, registMembersVO.getNickname());

    registMembersVO.setName(encryptedName);
    registMembersVO.setNickname(encryptedNickname);

    // 4. INSERT 요청
    int insertedRows = this.membersDao.insertNewMember(registMembersVO);
    if (insertedRows == 0) {
      throw new IllegalArgumentException("회원 가입에 실패했습니다.");
    }

    // 5. 회원 가입 결과 반환
    MembersVO newMember = this.membersDao.selectMemberByEmail(registMembersVO.getEmail());
    if (newMember == null) {
      throw new IllegalArgumentException("회원 가입에 실패했습니다.");
    }
    return newMember;
  }

}
