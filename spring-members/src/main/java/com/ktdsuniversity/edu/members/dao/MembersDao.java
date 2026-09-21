package com.ktdsuniversity.edu.members.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.members.vo.response.MemberVO;

@Mapper
public interface MembersDao {

  long selectMemberCount();

  List<MemberVO> selectAllMembers();


}
