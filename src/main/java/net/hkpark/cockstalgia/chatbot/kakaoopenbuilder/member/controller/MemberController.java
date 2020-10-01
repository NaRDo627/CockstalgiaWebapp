package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service.MemberService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/member")
public class MemberController {
    private final MemberService memberService;

    @PrintArguments
    @PostMapping(value = "/confirm/v1")
    public ResponseEntity<?> confirmMember(@RequestBody SkillRequestDto skillResultDto) {
        SkillResponseDto resultDto = memberService.confirmPlusFriend(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }

    @PostMapping(value = "/register/v1")
    public ResponseEntity<?> registerMember(@RequestBody SkillRequestDto skillResultDto) {
        SkillResponseDto resultDto = memberService.registerKakaoMember(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }
}
