package net.hkpark.cockstalgiacore.chatbot.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.service.KakaoMemberService;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.kakao.openbuilder.dto.SkillRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/admin")
public class KakaoAdminController {
    private final KakaoMemberService kakaoMemberService;

    @RequestMapping(value = "/register/v1", method = RequestMethod.POST)
    public ResponseEntity<?> registerAdmin(@RequestBody SkillRequestDto skillResultDto) {
        ResultDto resultDto = kakaoMemberService.registerAdmin(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }
}
