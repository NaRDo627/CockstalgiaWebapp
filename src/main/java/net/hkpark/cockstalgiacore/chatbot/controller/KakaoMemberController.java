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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chatbot/member")
public class KakaoMemberController {
    private final KakaoMemberService kakaoMemberService;

    @PrintArguments
    @RequestMapping(value = "/confirm/v1", method = RequestMethod.POST)
    public ResponseEntity<?> confirmMember(@RequestBody SkillRequestDto skillResultDto) {
        ResultDto resultDto = kakaoMemberService.confirmPlusFriend(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }

    @RequestMapping(value = "/register/v1", method = RequestMethod.POST)
    public ResponseEntity<?> registerMember(@RequestBody SkillRequestDto skillResultDto) {
        ResultDto resultDto = kakaoMemberService.registerKakaoMember(skillResultDto);
        return ResponseEntity.ok(resultDto);
    }
}