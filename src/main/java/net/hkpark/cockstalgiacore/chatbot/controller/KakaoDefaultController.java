package net.hkpark.cockstalgiacore.chatbot.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.service.KakaoSkillService;
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
@RequestMapping("/api/chatbot/v1")
public class KakaoDefaultController {
    private final KakaoSkillService kakaoSkillService;

    @PrintArguments
    @RequestMapping(value = "/datetime", method = RequestMethod.POST)
    public ResponseEntity<?> datetime(@RequestBody SkillRequestDto skillResultDto) {
        return ResponseEntity.ok(ResultDto.builder().data(new Date().getTime()).build());
    }

    @PrintArguments
    @RequestMapping(value = "/confirm_member", method = RequestMethod.POST)
    public ResponseEntity<?> confirmMember(@RequestBody SkillRequestDto skillResultDto) {
        ResultDto resultDto = kakaoSkillService.confirmPlusFriend(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }
}
