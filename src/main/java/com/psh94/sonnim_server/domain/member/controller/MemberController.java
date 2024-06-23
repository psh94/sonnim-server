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
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<?> createMember(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("signUpRequest: {}", signUpRequest);
        MemberDTO savedMember = memberService.createMember(signUpRequest);
        return new ResponseEntity<>(savedMember, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> findMemberById() {
        MemberDTO memberDTO = memberService.findMemberByAuth();
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@Valid @RequestBody DeleteMemberRequest deleteMemberRequest) {
        log.info("deleteMemberRequest: {}", deleteMemberRequest);
        memberService.deleteMember(deleteMemberRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
