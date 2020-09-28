package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service.MemberService;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import net.hkpark.kakao.openbuilder.dto.response.SkillResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/admin")
public class AdminController {
    private final MemberService memberService;

    @RequestMapping(value = "/register/v1", method = RequestMethod.POST)
    public ResponseEntity<?> registerAdmin(@RequestBody SkillRequestDto skillResultDto) {
        SkillResponseDto resultDto = memberService.registerAdmin(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }
}
