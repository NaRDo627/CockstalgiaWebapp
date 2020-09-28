package net.hkpark.cockstalgiacore.core.controller;

import lombok.extern.slf4j.Slf4j;
import net.hkpark.cockstalgiacore.core.dto.ResultDto;
import net.hkpark.cockstalgiacore.core.exception.BusinessException;
import net.hkpark.cockstalgiacore.core.exception.EntityAlreadyExistsException;
import net.hkpark.cockstalgiacore.core.exception.EntityNotFoundException;
import net.hkpark.cockstalgiacore.core.exception.InvalidValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalControllerAdvice {
    @ExceptionHandler(value = InvalidValueException.class)
    public ResponseEntity<?> handleInvalidValueException(InvalidValueException e) {
        return handleException(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundExceptionException(EntityNotFoundException e) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EntityAlreadyExistsException.class)
    public ResponseEntity<?> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException e) {
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception e) {
        log.error("Unexpected Exception : ", e);
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> handleException(Throwable e, HttpStatus status) {
        return handleException(e, status, status.value());
    }

    private ResponseEntity<?> handleException(Throwable e, HttpStatus status, int errorCode) {
        String message = StringUtils.isEmpty(e.getMessage()) ? status.getReasonPhrase() : e.getMessage();
        return ResponseEntity.status(status)
                .body(ResultDto.builder().message(message).build());
    }
}
