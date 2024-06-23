package com.psh94.sonnim_server.domain.member.service;

import com.psh94.sonnim_server.domain.member.dto.DeleteMemberRequest;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.dto.SignUpRequest;

public interface MemberService {

    MemberDTO createMember(SignUpRequest signUpRequest);
    MemberDTO findMemberByAuth();
    void deleteMember(DeleteMemberRequest deleteMemberRequest);
}
