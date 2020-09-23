package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * 스킬 payload는 스킬 실행시 봇 시스템이 스킬 서버에게 전달하는 정보입니다.
 * payload는 사용자의 정보, 발화, 실행 블록, 파라미터 등의 정보를 포함합니다.
 */
@Getter
@NoArgsConstructor
@ToString
public class SkillRequestDto {
    @JsonProperty("intent")
    private IntentDto intent;

    @JsonProperty("userRequest")
    private UserRequestDto userRequest;

    @JsonProperty("contexts")
    private List<Object> contexts;

    @JsonProperty("bot")
    private BotDto bot;

    @JsonProperty("action")
    private ActionDto action;
}
