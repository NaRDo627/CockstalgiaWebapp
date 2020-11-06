package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.service.ChatbotMemberService;
import net.hkpark.cockstalgia.core.annotation.PrintArguments;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import net.hkpark.kakao.openbuilder.dto.request.SkillRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class BasicController {
    private final ChatbotMemberService chatbotMemberService;

    @PrintArguments
    @PostMapping(value = "/datetime/v1")
    public ResponseEntity<?> datetime(@RequestBody SkillRequestDto skillResultDto) {
        return ResponseEntity.ok(ResultDto.builder().data(new Date().getTime()).build());
    }
}
