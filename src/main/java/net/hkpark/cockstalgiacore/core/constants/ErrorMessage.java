package net.hkpark.cockstalgiacore.core.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorMessage {
    REQUEST_BAD_REQUEST("리소스 요청이 잘못되었습니다."),
    REQUEST_NOT_FOUND("리소스 요청을 찾을 수 없습니다."),
    DB_INSERT_FAILURE("{} 테이블 삽입이 실패하였습니다."),
    DB_INSERT_FAILURE_ALREADY_EXISTS("{} 테이블 삽입이 실패하였습니다.\n레코드가 이미 존재합니다.");
    @Getter
    private final String message;
}
