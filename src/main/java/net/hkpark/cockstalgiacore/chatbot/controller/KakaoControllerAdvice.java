package net.hkpark.cockstalgiacore.chatbot.controller;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.chatbot.exception.MemberAlreadyExistsException;
import net.hkpark.cockstalgiacore.chatbot.exception.MemberNotFoundException;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.cockstalgiacore.core.exception.BusinessException;
import net.hkpark.cockstalgiacore.core.exception.EntityAlreadyExistsException;
import net.hkpark.cockstalgiacore.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgiacore.core.exception.InvalidValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class, basePackages = "net.hkpark.cockstalgiacore.chatbot.controller")
public class KakaoControllerAdvice {
    // 에러 코드를 리턴하면 챗봇쪽에서 에러로 처리해버리기 때문에 200응답을 리턴.
    @ExceptionHandler(value = MemberAlreadyExistsException.class)
    public ResponseEntity<?> memberAlreadyExistsException(MemberAlreadyExistsException e) {
        return ResponseEntity.ok(ResultDto.builder().data("이미 존재하는 회원입니다.").build());
    }

    @ExceptionHandler(value = MemberNotFoundException.class)
    public ResponseEntity<?> memberNotFoundException(MemberNotFoundException e) {
        return ResponseEntity.ok(ResultDto.builder().data("회원 등록을 먼저 해주세요.").build());
    }
}
