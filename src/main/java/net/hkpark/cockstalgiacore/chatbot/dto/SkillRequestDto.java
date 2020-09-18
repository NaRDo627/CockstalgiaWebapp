package net.hkpark.cockstalgiacore.chatbot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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
