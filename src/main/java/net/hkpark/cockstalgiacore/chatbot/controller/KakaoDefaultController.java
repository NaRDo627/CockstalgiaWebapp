package net.hkpark.cockstalgiacore.chatbot.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.service.KakaoMemberService;
import net.hkpark.cockstalgiacore.core.annotation.PrintArguments;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.kakao.openbuilder.dto.SkillRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot")
public class KakaoDefaultController {
    private final KakaoMemberService kakaoMemberService;

    @PrintArguments
    @RequestMapping(value = "/datetime/v1", method = RequestMethod.POST)
    public ResponseEntity<?> datetime(@RequestBody SkillRequestDto skillResultDto) {
        return ResponseEntity.ok(ResultDto.builder().data(new Date().getTime()).build());
    }
}
