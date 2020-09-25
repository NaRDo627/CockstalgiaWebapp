package net.hkpark.cockstalgiacore.core.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ErrorMessage {
    DB_INSERT_FAILURE("%s 테이블 삽입이 실패하였습니다.");

    @Getter
    private final String message;
}
