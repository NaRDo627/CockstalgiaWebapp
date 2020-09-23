package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 사용자의 정보를 담고 있는 필드입니다. 사용자의 발화와 시간대/언어, 반응한 블록의 정보를 추가적으로 포함합니다.
 */
@Getter
@NoArgsConstructor
public class UserRequestDto {

    /**
     * 사용자의 시간대를 반환합니다.한국에서 보낸 요청이라면 “Asia/Seoul”를 갖습니다.
     */
    @JsonProperty("timezone")
    private String timezone;

    /**
     * 사용자의 발화에 반응한 블록의 정보입니다.
     * 블록의 id와 name을 포함합니다.
     */
    @JsonProperty("block")
    private BlockDto block;

    /**
     * 	봇 시스템에 전달된 사용자의 발화입니다.
     */
    @JsonProperty("utterance")
    private String utterance;

    /**
     * 사용자의 언어를 반환합니다.
     * 한국에서 보낸 요청이라면 “kr”를 갖습니다.
     */
    @JsonProperty("lang")
    private String lang;

    /**
     * 사용자의 정보입니다.
     */
    @JsonProperty("user")
    private UserDto user;

    @JsonProperty("params")
    private Map<String, Object> params;
}
