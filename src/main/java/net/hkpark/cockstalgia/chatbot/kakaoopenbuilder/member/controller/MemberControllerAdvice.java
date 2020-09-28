package net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.member.controller;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.exception.MemberNotFoundException;
import net.hkpark.cockstalgia.chatbot.kakaoopenbuilder.core.util.SkillResponseUtil;
import net.hkpark.cockstalgia.core.dto.ResultDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class,
        basePackages = "net.hkpark.cockstalgiacore.chatbot.kakaoopenbuilder.member")
public class MemberControllerAdvice {
    // 에러 코드를 리턴하면 챗봇쪽에서 에러로 처리해버리기 때문에 200응답을 리턴.
    @ExceptionHandler(value = MemberAlreadyExistsException.class)
    public ResponseEntity<?> memberAlreadyExistsException(MemberAlreadyExistsException e) {
        return ResponseEntity.ok(SkillResponseUtil.simpleTextResponseDto("이미 존재하는 회원입니다."));
    }

    @ExceptionHandler(value = MemberNotFoundException.class)
    public ResponseEntity<?> memberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.ok(ResultDto.builder().data(
                SkillResponseUtil.simpleTextResponseDto("회원 등록을 먼저 해주세요.")
        ).build());
    }
}
