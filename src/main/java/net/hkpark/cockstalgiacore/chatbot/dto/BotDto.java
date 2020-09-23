package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 사용자의 발화를 받은 봇의 정보를 담고 있는 필드입니다.
 */
@Getter
@NoArgsConstructor
public class BotDto {
    /**
     * 봇의 고유한 식별자입니다.
     */
    @JsonProperty("id")
    private String id;

    /**
     * 설정된 봇의 이름입니다.
     */
    @JsonProperty("name")
    private String name;
}
