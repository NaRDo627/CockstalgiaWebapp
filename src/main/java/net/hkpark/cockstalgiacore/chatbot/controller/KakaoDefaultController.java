package net.hkpark.cockstalgiacore.chatbot.controller;


import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.dto.SkillRequestDto;
import net.hkpark.cockstalgiacore.chatbot.dto.SkillResultDto;
import net.hkpark.cockstalgiacore.core.annotation.PrintArguments;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/api/chatbot/v1")
public class KakaoDefaultController {
    @PrintArguments
    @RequestMapping(value = "/datetime", method = RequestMethod.POST)
    public ResponseEntity<SkillResultDto> datetime(@RequestBody SkillRequestDto skillResultDto) {
        log.info(skillResultDto.toString());
        return ResponseEntity.ok(SkillResultDto.builder().data(new Date().getTime()).build());
    }
}
