package com.psh94.sonnim_server.domain.member.controller;

import com.psh94.sonnim_server.domain.member.dto.DeleteMemberRequest;
import com.psh94.sonnim_server.domain.member.dto.MemberDTO;
import com.psh94.sonnim_server.domain.member.dto.SignUpRequest;
import com.psh94.sonnim_server.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<MemberDTO> createMember(@RequestBody @Valid SignUpRequest signUpRequest) {
        MemberDTO savedMember = memberService.createMember(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberDTO> findMemberById() {
        MemberDTO memberDTO = memberService.findMemberByAuth();
        return ResponseEntity.ok(memberDTO);
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> deleteMember(@Valid @RequestBody DeleteMemberRequest deleteMemberRequest) {
        memberService.deleteMember(deleteMemberRequest);
        return ResponseEntity.noContent().build();
    }
}
